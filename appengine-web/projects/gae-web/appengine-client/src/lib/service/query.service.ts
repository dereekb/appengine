import { UniqueModel, ModelUtility, ModelKey } from '@gae-web/appengine-utility';
import { QueryService, QueryRequest, QueryResponse, SearchCursor } from '@gae-web/appengine-api';
import { ModelServiceWrapper } from './model.service';
import { ModelReadService } from './crud.service';
import { Observable, of, empty, from } from 'rxjs';
import { flatMap, filter, map } from 'rxjs/operators';
import { KeyedPredictiveOrderedQueryStreamEvent, KeyedPredictiveOrderedQueryStream } from './source';
import { WrapperEventFilter, ModelWrapperEvent, WrapperEventType } from './wrapper';

/**
 * Helper wrapper that wraps models and an optional cursor and implements QueryResponse.
 */
export class WrappedModelsQueryResponse<T extends UniqueModel> implements QueryResponse<T> {

  private _keyResults: ModelKey[];

  constructor(private _models: T[], private _cursor?: SearchCursor) { }

  get cursor() {
    return this._cursor;
  }

  get keyResults() {
    if (!this._keyResults) {
      this._keyResults = ModelUtility.readModelKeys(this._models);
    }

    return this._keyResults;
  }

  get modelResults() {
    return this._models;
  }

  get isKeysOnlyResponse() {
    return false;
  }

}

// MARK: Query
/**
 * Wraps a QueryService and ModelReadService to always query by keys, and use the read service to perform reads and cache reads.
 */
export class ModelQueryService<T extends UniqueModel> implements QueryService<T> {

  constructor(private _parent: ModelServiceWrapper<T>, private _readService: ModelReadService<T>, private _queryService: QueryService<T>) { }

  // MARK: Query Service
  public query(request: QueryRequest): Observable<QueryResponse<T>> {

    if (request.isKeysOnly) {
      return this._queryService.query(request);   // Pass through keys only requests normally.
    } else {
      const keysOnlyRequest = { ...request, isKeysOnly: true };

      return this._queryService.query(keysOnlyRequest).pipe(
        flatMap((response: QueryResponse<T>) => {
          const keys = response.keyResults;

          if (keys.length > 0) {
            return this.performRead(response);
          } else {
            return of(new WrappedModelsQueryResponse([], response.cursor) as QueryResponse<T>);
          }
        })
      );
    }
  }

  protected performRead(response: QueryResponse<T>): Observable<QueryResponse<T>> {
    const keys = response.keyResults;
    const cacheLoad = this._parent.cache.load(keys);

    const hits = cacheLoad.hits;
    const misses = cacheLoad.misses;

    if (misses.length > 0) {
      const readObs = this._readService.read({
        modelKeys: misses,
        atomic: false
      });

      return readObs.pipe(
        map((result) => {
          const models = hits.concat(result.models);
          const ordered = ModelUtility.orderModels(keys, models);
          return new WrappedModelsQueryResponse(ordered, response.cursor);
        })
      );
    } else {
      return of(new WrappedModelsQueryResponse(hits, response.cursor));
    }
  }

}

export class KeyedPredictiveOrderedQueryDelegate<T extends UniqueModel> implements KeyedPredictiveOrderedQueryStream {

  public readonly delegateStream: Observable<KeyedPredictiveOrderedQueryStreamEvent>;

  constructor(protected readonly _parent: ModelServiceWrapper<T>, protected readonly _eventFilter?: WrapperEventFilter) {
    this.delegateStream = this.makeDelegateStream();
  }

  protected makeDelegateStream(): Observable<KeyedPredictiveOrderedQueryStreamEvent> {
    const obs = this.getBaseEventObservable();
    return this.makeMapObservable(obs);
  }

  protected getBaseEventObservable(): Observable<ModelWrapperEvent> {
    return (this._eventFilter) ? this._parent.events.pipe(filter(this._eventFilter)) : this._parent.events;
  }

  protected makeMapObservable(eventObs: Observable<ModelWrapperEvent>): Observable<KeyedPredictiveOrderedQueryStreamEvent> {
    return eventObs.pipe(
      map((wrapperEvent: ModelWrapperEvent) => {
        const keys = wrapperEvent.keys;

        let updated: ModelKey[] | undefined;
        let removed: ModelKey[] | undefined;

        switch (wrapperEvent.eventType) {
          case WrapperEventType.ModelCreate:
          case WrapperEventType.ModelUpdate:
          case WrapperEventType.ModelLink:
            updated = keys;
            break;
          case WrapperEventType.ModelDelete:
            removed = keys;
            break;
        }

        return {
          updated,
          removed
        };
      })
    );
  }

}

export class ModelFilteredKeyedPredictiveOrderedQueryDelegate<T extends UniqueModel> extends KeyedPredictiveOrderedQueryDelegate<T> {

  public readonly delegateStream: Observable<KeyedPredictiveOrderedQueryStreamEvent>;

  constructor(protected readonly _parent: ModelServiceWrapper<T>,
    protected readonly _modelsFilter: (model: T) => boolean,
    protected readonly _strictFilter = true, eventFilter?: WrapperEventFilter) {
    super(_parent, eventFilter);
  }

  protected getBaseEventObservable(): Observable<ModelWrapperEvent> {
    let base = super.getBaseEventObservable().pipe(
      filter((x: ModelWrapperEvent) => Boolean(x.models))
    );

    if (this._strictFilter) {
      base = base.pipe(
        flatMap((event: ModelWrapperEvent) => {
          return this._strictFilterMap(event);
        })
      );
    } else {
      base = base.pipe(
        flatMap((event: ModelWrapperEvent) => {
          return this._addRemoveFilterMap(event);
        })
      );
    }

    return base.pipe(
      filter((x: ModelWrapperEvent) => x.keys.length > 0)
    );
  }

  private _strictFilterMap(event: ModelWrapperEvent): Observable<ModelWrapperEvent> {
    if (event.models) {
      const filteredModels = event.models.filter(this._modelsFilter);

      if (filteredModels.length > 0) {
        const subEvent: ModelWrapperEvent = { ...event, models: filteredModels };
        return of(subEvent);
      }
    }

    return empty() as Observable<ModelWrapperEvent>;
  }

  private _addRemoveFilterMap(event: ModelWrapperEvent): Observable<ModelWrapperEvent> {

    if (event.models && event.models.length) {
      const addedModels = [];
      const removedModels = [];

      event.models.forEach((x: T) => {
        if (this._modelsFilter(x)) {
          addedModels.push(x);
        } else {
          removedModels.push(x);
        }
      });

      const added: ModelWrapperEvent = this._tryMakeFakeWrapperEventWithModels(addedModels, event, true);
      const removed: ModelWrapperEvent = this._tryMakeFakeWrapperEventWithModels(removedModels, event, false);

      const fakeEvents: ModelWrapperEvent[] = [added, removed].filter((x) => Boolean(x));
      return from(fakeEvents);
    }

    // Will get filtered out and ultimately ignored.
    return empty() as Observable<ModelWrapperEvent>;
  }

  private _tryMakeFakeWrapperEventWithModels(models: T[], event: ModelWrapperEvent, added: boolean): ModelWrapperEvent | undefined {
    if (models.length) {
      const subEvent = {
        ...event,
        models,
        eventType: (added) ? WrapperEventType.ModelUpdate : WrapperEventType.ModelDelete
      };

      return subEvent;
    }

    return undefined;
  }

}
