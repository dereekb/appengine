import { UniqueModel, ModelKey, ModelOrKey, ModelsOrKeys, SourceState, KeyedCacheLoad, ModelUtility, ValueUtility, ConversionSourceInputResult, Keyed, OneOrMore } from '@gae-web/appengine-utility';
import { ReadSourceFactory, ReadSourceConfiguration, ReadSource, ReadServiceReadSourceFactory } from './source';
import { ModelServiceWrapper } from './model.service';
import { ReadService, ReadRequest, ModelServiceResponse, ReadResponse, UpdateService, UpdateResponse, UpdateRequest, DeleteService, DeleteRequest, DeleteResponse } from '@gae-web/appengine-api';
import { throwError, of, Observable } from 'rxjs';
import { flatMap, map, catchError, first, share, tap, skip, shareReplay } from 'rxjs/operators';
import { WrapperEventType } from './wrapper';

// MARK: Read
export class AppEngineReadSourceFactory<T extends UniqueModel> extends ReadServiceReadSourceFactory<T> {

  constructor(private _parent: ModelServiceWrapper<T>, readService: ReadService<T>) {
    super(readService);
  }

  public makeSource(config?: ReadSourceConfiguration): ReadSource<T> {
    return new ModelReadSource<T>(this._parent, this._readService, config);
  }

}

export class ModelReadSource<T extends UniqueModel> extends ReadSource<T>  {

  private _debounce?: number;

  constructor(private _parent: ModelServiceWrapper<T>, service: ReadService<T>, config?: ReadSourceConfiguration) {
    super(service, config);
  }

  // MARK: Update
  protected convertInputKeys(keys: ModelKey[]): Observable<ConversionSourceInputResult<ModelKey, T>> {
    this.setState(SourceState.Loading);

    // Watch for the cache to change.

    // TODO: Move this to the read service later.
    return this._parent.cache
      .asyncRead(keys, { debounce: this._debounce })
      .pipe(
        flatMap((result: KeyedCacheLoad<ModelKey, T>) => {
          this.setState(SourceState.Loading);

          const hits = result.hits;
          const misses = result.misses;

          if (misses.length > 0) {
            const readRequest: ReadRequest = this.makeReadRequest(misses);

            return this._service.read(readRequest).pipe(
              map((response: ModelServiceResponse<T>) => {
                const models = hits.concat(response.models);
                const ordered = ModelUtility.orderModels(keys, models);

                return {
                  hits: ordered,
                  misses: response.failed
                };
              }),
              catchError((x) => {
                return throwError(x);
              })
            );
          } else {
            return of(result);
          }
        }),
        map((result: KeyedCacheLoad<ModelKey, T>) => {
          return {
            models: result.hits,
            failed: result.misses
          };
        })
      );
  }

}

/**
 * ReadService extension that gives notion of a cache, and can skip the cacbe for reads.
 */
export abstract class CachedReadService<T> extends ReadService<T> {
  abstract read(request: ReadRequest, skipCache?: boolean): Observable<ReadResponse<T>>;
}

export class ModelReadService<T extends UniqueModel> implements CachedReadService<T> {

  // Used as a sort of buffer to prevent multiple of the same request from being sent.
  private _working = new Map<string, Observable<ReadResponse<T>>>();

  constructor(private _parent: ModelServiceWrapper<T>, private _readService: ReadService<T>) { }

  // MARK: Cache
  public clearFromCache(keys: ModelsOrKeys<T>): T[] {
    return this._parent.clearFromCache(keys);
  }

  // MARK: Read Service
  public get type() {
    return this._readService.type;
  }

  /**
   * Convenience function for reading a single model.
   *
   * @param key Model or key to read.
   * @param skipCache Whether or not to skip the internal read cache.
   * @param atomic Whether or not to throw an error. If false, the observable will return undefined if the model is unavailable.
   */
  public readOne(key: ModelOrKey<T>, skipCache: boolean = false, atomic = false): Observable<T | undefined> {
    const inputKey = ModelUtility.readModelKey(key);
    return this.read({
      modelKeys: [inputKey],
      atomic
    }, skipCache).pipe(
      map(x => x.models[0])
    );
  }

  /**
   * Single read that checks the cache before sending a request.
   */
  public read(request: ReadRequest, skipCache: boolean = false): Observable<ReadResponse<T>> {
    if (skipCache) {
      return this._read(request);
    } else {
      return this.continuousRead(request, 0).pipe(
        shareReplay(1)   // Share the single result with all subscribers.
      );
    }
  }

  /**
   * Convenience function for reading a single model.
   *
   * @param key Model or key to read.
   * @param atomic Whether or not to throw an error. If false, the observable will return undefined if the model is unavailable.
   * @param debounce Optional debounce.
   */
  public continuousReadOne(key: ModelOrKey<T>, atomic = false, debounce?: number): Observable<T | undefined> {
    const inputKey = ModelUtility.readModelKey(key);
    return this.continuousRead({
      modelKeys: [inputKey],
      atomic
    }, debounce).pipe(
      map(x => x.models[0])
    );
  }

  /**
   * Continuous read that checks the cache before reading more.
   */
  public continuousRead(inputRequest: ReadRequest, debounce?: number): Observable<ReadResponse<T>> {
    const inputKeys = ValueUtility.normalizeArray(inputRequest.modelKeys);

    return this._parent.cache
      .asyncRead(inputRequest.modelKeys, { debounce })
      .pipe(
        // Read requested model keys from the cache.
        flatMap((result: KeyedCacheLoad<ModelKey, T>) => {
          const hits = result.hits;
          const misses = result.misses;

          if (misses.length > 0) {
            const readRequest: ReadRequest = {
              modelKeys: misses,
              atomic: inputRequest.atomic
            };

            return this._read(readRequest).pipe(
              map((response: ModelServiceResponse<T>) => {
                const models = hits.concat(response.models);
                const ordered = ModelUtility.orderModels(inputKeys, models);

                return {
                  models: ordered,
                  failed: response.failed,
                  result
                };
              }),
              catchError((x) => {
                return throwError(x);
              })
            );
          } else {
            return of({
              models: hits,
              failed: [],
              result
            });
          }
        })
      );
  }

  /**
   * Read using the wrapped ReadService, and update the cache.
   */
  protected _read(request: ReadRequest): Observable<ReadResponse<T>> {
    const hash = (ModelUtility.makeModelKeysParameter(request.modelKeys) + ((request.atomic) ? '_A' : ''));

    if (this._working.has(hash)) {
      return this._working.get(hash);
    } else {

      // Updates the cache with the results.
      const obs = this._readService.read(request).pipe(
        tap((response) => {
          this._parent.cache.putModels(response.models);  // Put new models into cache.
          this._parent.cache.removeAll(response.failed);  // Remove "missing" models from cache.
          this._working.delete(hash);  // Remove from working.
        }),
        // Share the latest response
        shareReplay(1)
      );

      this._working.set(hash, obs);

      return obs;
    }
  }

}

// MARK: Update
export class ModelUpdateService<T extends UniqueModel> implements UpdateService<T> {

  constructor(protected readonly _parent: ModelServiceWrapper<T>, protected readonly _updateService: UpdateService<T>) { }

  // MARK: Update Service
  public update(request: UpdateRequest<T>): Observable<UpdateResponse<T>> {
    return this._updateService.update(request).pipe(
      tap((response) => this.updateWithUpdateResponse(request, response)),
      // Share replay to tap only once.
      shareReplay(1)
    );
  }

  protected updateWithUpdateResponse(request: UpdateRequest<T>, response: UpdateResponse<T>): void {
    this._parent.next({
      eventType: WrapperEventType.ModelUpdate,
      request,
      response,
      models: response.models
    });

    this.updateCacheWithResponse(response);
  }

  protected updateCacheWithResponse(response: UpdateResponse<T>): void {
    this._parent.cache.putModels(response.models);
    this._parent.cache.removeAll(response.missing); // Models deemed missing should be removed.
  }

}

// MARK: Delete
export class ModelDeleteService<T extends UniqueModel> implements DeleteService<T> {

  constructor(private _parent: ModelServiceWrapper<T>, private _deleteService: DeleteService<T>) { }

  // MARK: Delete Service
  public delete(request: DeleteRequest): Observable<DeleteResponse<T>> {

    return this._deleteService.delete(request).pipe(
      tap((response) => {
        const keys = response.keys;
        const cachedModels = this._parent.cache.load(keys).hits;

        this._parent.next({
          eventType: WrapperEventType.ModelDelete,
          request,
          response,
          models: cachedModels
        });

        this._parent.cache.removeAll(response.keys); // Models that are deleted should be removed from the cache too.
      })
    );

  }

}
