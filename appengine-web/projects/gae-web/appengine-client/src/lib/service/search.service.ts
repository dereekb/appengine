import { UniqueModel, ModelUtility, ModelKey } from '@gae-web/appengine-utility';
import {
  QueryService, QueryRequest, QueryResponse, SearchCursor, ModelSearchResponse, SearchRequest,
  SearchResponse, TypedModelSearchService,
  TypedModelSearchRequest, TypedModelSearchResponse
} from '@gae-web/appengine-api';
import { ModelServiceWrapper } from './model.service';
import { ModelReadService } from './crud.service';
import { Observable, of, empty, from, EMPTY } from 'rxjs';
import { mergeMap, filter, map } from 'rxjs/operators';
import { KeyedPredictiveOrderedQueryStreamEvent, KeyedPredictiveOrderedQueryStream } from './source';
import { WrapperEventFilter, ModelWrapperEvent, WrapperEventType } from './wrapper';

/**
 * Helper wrapper that wraps models and an optional cursor and implements QueryResponse.
 */
export class WrappedModelSearchResponse<T extends UniqueModel> implements ModelSearchResponse<T> {

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

// MARK: Abstract
abstract class AbstractModelSearchService<T extends UniqueModel, I extends SearchRequest, O extends SearchResponse> {

  constructor(protected readonly _parent: ModelServiceWrapper<T>, protected readonly _readService: ModelReadService<T>) { }

  // MARK: Query Service
  protected doSearch(request: I): Observable<O> {

    if (request.isKeysOnly) {
      return this.doSearchWithService(request);   // Pass through keys only requests normally.
    } else {
      const keysOnlyRequest = { ...request, isKeysOnly: true };

      return this.doSearchWithService(keysOnlyRequest).pipe(
        mergeMap((response: O) => {
          const keys = response.keyResults;

          if (keys.length > 0) {
            return this.performRead(response);
          } else {
            return this.mapSearchResponse(of(new WrappedModelSearchResponse([], response.cursor)));
          }
        })
      );
    }
  }

  protected abstract doSearchWithService(request: I): Observable<O>;

  protected performRead(response: O): Observable<O> {
    const keys = response.keyResults;
    const cacheLoad = this._parent.cache.load(keys);

    const hits = cacheLoad.hits;
    const misses = cacheLoad.misses;

    let result: Observable<WrappedModelSearchResponse<T>>;

    if (misses.length > 0) {
      const readObs = this._readService.read({
        modelKeys: misses,
        atomic: false
      });

      result = readObs.pipe(
        map((x) => {
          const models = hits.concat(x.models);
          const ordered = ModelUtility.orderModels(keys, models);
          return new WrappedModelSearchResponse(ordered, response.cursor);
        })
      );
    } else {
      result = of(new WrappedModelSearchResponse(hits, response.cursor));
    }

    return this.mapSearchResponse(result);
  }

  protected abstract mapSearchResponse(responseObs: Observable<WrappedModelSearchResponse<T>>): Observable<O>;

}

// MARK: Search
/**
 * Wraps a TypedModelSearchService and ModelReadService to always query by keys, and use the read service to perform reads and cache reads.
 */
export class ModelSearchService<T extends UniqueModel> extends AbstractModelSearchService<T, TypedModelSearchRequest, TypedModelSearchResponse<T>> implements TypedModelSearchService<T> {

  constructor(parent: ModelServiceWrapper<T>, readService: ModelReadService<T>, private _searchService: TypedModelSearchService<T>) {
    super(parent, readService);
  }

  // MARK: Query Service
  public search(request: TypedModelSearchRequest): Observable<TypedModelSearchResponse<T>> {
    return this.doSearch(request);
  }

  protected doSearchWithService(request: TypedModelSearchRequest): Observable<TypedModelSearchResponse<T>> {
    return this._searchService.search(request);
  }

  protected mapSearchResponse(responseObs: Observable<WrappedModelSearchResponse<T>>): Observable<TypedModelSearchResponse<T>> {
    return responseObs;
  }

}


// MARK: Query
/**
 * Wraps a QueryService and ModelReadService to always query by keys, and use the read service to perform reads and cache reads.
 */
export class ModelQueryService<T extends UniqueModel> extends AbstractModelSearchService<T, QueryRequest, QueryResponse<T>> implements QueryService<T> {

  constructor(parent: ModelServiceWrapper<T>, readService: ModelReadService<T>, private _queryService: QueryService<T>) {
    super(parent, readService);
  }

  // MARK: Query Service
  public query(request: QueryRequest): Observable<QueryResponse<T>> {
    return this.doSearch(request);
  }

  protected doSearchWithService(request: QueryRequest): Observable<QueryResponse<T>> {
    return this._queryService.query(request);
  }

  protected mapSearchResponse(responseObs: Observable<WrappedModelSearchResponse<T>>): Observable<QueryResponse<T>> {
    return responseObs;
  }

}

// MARK: Searching

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
        mergeMap((event: ModelWrapperEvent) => {
          return this._strictFilterMap(event);
        })
      );
    } else {
      base = base.pipe(
        mergeMap((event: ModelWrapperEvent) => {
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

    return EMPTY;
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
    return EMPTY;
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
