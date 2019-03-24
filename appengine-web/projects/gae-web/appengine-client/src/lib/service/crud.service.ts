import { UniqueModel, ModelKey, SourceState, KeyedCacheLoad, ModelUtility, ValueUtility } from '@gae-web/appengine-utility';
import { ReadSourceFactory, ReadSourceConfiguration, ReadSource } from './source';
import { ModelServiceWrapper } from './model.service';
import { ReadService, ReadRequest, ModelServiceResponse, ReadResponse, UpdateService, UpdateResponse, UpdateRequest, DeleteService, DeleteRequest, DeleteResponse } from '@gae-web/appengine-api';
import { throwError, of, Observable } from 'rxjs';
import { flatMap, map, catchError, first, share, tap } from 'rxjs/operators';
import { WrapperEventType } from './wrapper';

// MARK: Read
export class AppEngineReadSourceFactory<T extends UniqueModel> extends ReadSourceFactory<T> {

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
  protected updateWithKeys(keys: ModelKey[]) {
    this.setState(SourceState.Loading);

    // Watch for the cache to change.

    // TODO: Move this to the read service later.
    const sub = this._parent.cache
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
        })
      ).subscribe((result) => {
        this._updateWithReadResult(result);
      }, (error) => {
        this._updateWithReadError(keys, error);
      });

    this.setSourceSub(sub);
  }

  protected _updateWithReadResult(result: KeyedCacheLoad<ModelKey, T>) {
    this.setConvertedElements(result.hits, result.misses);
  }

}

export class ModelReadService<T extends UniqueModel> implements ReadService<T> {

  // Used as a sort of buffer to prevent multiple of the same request from being sent.
  private _working = new Map<string, Observable<ReadResponse<T>>>();

  constructor(private _parent: ModelServiceWrapper<T>, private _readService: ReadService<T>) { }

  // MARK: Read Service
  public get type() {
    return this._readService.type;
  }

  // TODO: Later move to a helper class that only uses a cache and a read service?
  public cacheReadWithKeys(keys: ModelKey | ModelKey[], atomic?: boolean): Observable<ReadResponse<T>> {
    return this.cacheRead({
      modelKeys: keys,
      atomic
    });
  }

  /**
   * Single read that checks the cache before sending a request.
   */
  public cacheRead(request: ReadRequest): Observable<ReadResponse<T>> {
    return this.asyncCacheRead(request, 0).pipe(
      first(),
      share()
    );
  }

  /**
   * Continuous read that checks the cache before reading more.
   */
  public asyncCacheRead(inputRequest: ReadRequest, debounce?: number): Observable<ReadResponse<T>> {
    const inputKeys = ValueUtility.normalizeArray(inputRequest.modelKeys);

    return this._parent.cache
      .asyncRead(inputRequest.modelKeys, { debounce })
      .pipe(
        flatMap((result: KeyedCacheLoad<ModelKey, T>) => {
          const hits = result.hits;
          const misses = result.misses;

          if (misses.length > 0) {
            const readRequest: ReadRequest = {
              modelKeys: misses,
              atomic: inputRequest.atomic
            };

            return this.read(readRequest).pipe(
              map((response: ModelServiceResponse<T>) => {
                const models = hits.concat(response.models);
                const ordered = ModelUtility.orderModels(inputKeys, models);

                return {
                  models: ordered,
                  failed: response.failed
                };
              }),
              catchError((x) => {
                return Observable.throw(x);
              })
            );
          } else {
            return of({
              models: hits,
              failed: []
            });
          }
        })
      );
  }

  public read(request: ReadRequest): Observable<ReadResponse<T>> {
    const hash = (ModelUtility.makeModelKeysParameter(request.modelKeys) + ((request.atomic) ? '_A' : ''));

    if (this._working.has(hash)) {
      return this._working.get(hash);
    } else {

      // TODO: Break up larger requests up into multiple parts and execute in serial.

      // Updates the cache with the results.
      const obs = this._readService.read(request).pipe(
        tap((response) => {
          this._parent.cache.putModels(response.models);  // Put new models into cache.

          // NOTE: This will cause a loop with the asyncRead above.
          // this._parent.cache.removeAll(response.failed);  // Remove "missing" models from cache.

          this._working.delete(hash);  // Remove from working.
        }),
        share()
      );

      this._working.set(hash, obs);

      return obs;
    }
  }

}

// MARK: Update
export class ModelUpdateService<T extends UniqueModel> implements UpdateService<T> {

  constructor(private _parent: ModelServiceWrapper<T>, private _updateService: UpdateService<T>) { }

  // MARK: Update Service
  public update(request: UpdateRequest<T>): Observable<UpdateResponse<T>> {
    return this._updateService.update(request).pipe(
      tap((response) => this.updateWithUpdateResponse(request, response))
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
