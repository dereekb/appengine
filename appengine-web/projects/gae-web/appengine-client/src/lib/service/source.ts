import { UniqueModel, ModelKey, ModelUtility, ConversionSource, AbstractConversionSource,
         SourceState, SourceFactory, IterableSource, AbstractSource, ControllableSource, SourceEvent } from '@gae-web/appengine-utility';
import { ReadService, ReadRequest, ReadResponse, SearchCursor, SearchParameters, QueryService, SearchRequest, ModelSearchResponse } from '@gae-web/appengine-api';
import { Subscription, Observable, combineLatest, Subject } from 'rxjs';
import { map, share } from 'rxjs/operators';
import { ValueUtility } from '@gae-web/appengine-utility';

// MARK: Read Source
export interface ReadSourceConfiguration {
  readonly atomic: boolean;
}

export interface ModelKeyReadSource<T extends UniqueModel> extends ConversionSource<ModelKey, T> {
  readonly type: string;
  readonly config: ReadSourceConfiguration;
}

export abstract class AbstractModelKeyConversionSource<T extends UniqueModel> extends AbstractConversionSource<ModelKey, T> {

  // MARK: AbstractConversionSource
  protected updateWithInput(keys: ModelKey[]) {
    keys = ModelUtility.filterInitializedModelKeys(keys);

    if (keys.length === 0) {
      this.updateWithNothing();
    } else {
      this.updateWithKeys(keys);
    }
  }

  protected abstract updateWithKeys(keys: ModelKey[]);

}

export const DEFAULT_READ_SOURCE_CONFIG: ReadSourceConfiguration = {
  atomic: false
};

/**
 * ReadSource that watches another observable for keys.
 */
export class ReadSource<T extends UniqueModel> extends AbstractModelKeyConversionSource<T> implements ModelKeyReadSource<T> {

  constructor(protected _service: ReadService<T>, private _config: ReadSourceConfiguration = DEFAULT_READ_SOURCE_CONFIG) {
    super();
  }

  // MARK: ModelKeyReadSource
  public get type() {
    return this._service.type;
  }

  public get config() {
    return this._config;
  }

  public set config(config) {
    this._config = config;
  }

  // MARK: Update
  protected updateWithKeys(keys: ModelKey[]) {
    this.setState(SourceState.Loading);

    const readRequest: ReadRequest = this.makeReadRequest(keys);

    const sub = this._service.read(readRequest).subscribe((response: ReadResponse<T>) => {
      this._updateWithReadResponse(response);
    }, (error: any) => {
      this._updateWithReadError(keys, error);
    });

    this.setSourceSub(sub);
  }

  protected makeReadRequest(keys: ModelKey[]) {
    const readRequest: ReadRequest = {
      atomic: this._config.atomic,
      modelKeys: keys
    };

    return readRequest;
  }

  protected _updateWithReadResponse(response: ReadResponse<T>) {
    this.setElements(response.models, SourceState.Idle, response.failed);
  }

  protected _updateWithReadError(keys: ModelKey[], error: any) {
    this.setElements([], SourceState.Error, keys);
  }

}

/**
 *  ReadSource Factory.
 */
export class ReadSourceFactory<T extends UniqueModel> implements SourceFactory<T> {

  constructor(protected readonly _readService: ReadService<T>) { }

  public makeSource(): ReadSource<T> {
    return new ReadSource<T>(this._readService);
  }

}

// MARK: Query
export interface QuerySourceConfiguration {
  limit?: number;
  cursor?: SearchCursor;
  filters?: SearchParameters;
}

export const DEFAULT_CONFIG = {
  limit: 20,
  cursor: undefined,
  filters: undefined
};

export interface QueryIterableSource<T> extends IterableSource<T> {

  /**
   * Allows query configuration modifications.
   */
  config: QuerySourceConfiguration;

}

/**
 * IterableSource that uses queries.
 */
export class KeyQuerySource<T extends UniqueModel> extends AbstractSource<ModelKey> implements QueryIterableSource<ModelKey> {

  private _config: QuerySourceConfiguration = DEFAULT_CONFIG;

  private _result: KeyResultPair<T>;

  private _nextSub?: Subscription;

  private _next?: Promise<ModelKey[]>;    // Next Promise.

  private done = false;

  constructor(private _service: QueryService<T>, config?: QuerySourceConfiguration) {
    super();
    this.resetQuerySource();

    if (config) {
      this.config = config;
    }
  }

  // MARK: Accessors
  public get config() {
    return this._config;
  }

  public set config(config) {
    if (this._config !== config) {
      this.setConfig(config);
    }
  }

  protected setConfig(config): void {
    config = config || {};

    this._config = {};
    this._config.limit = config.limit || DEFAULT_CONFIG.limit;
    this._config.cursor = config.cursor || DEFAULT_CONFIG.cursor;
    this._config.filters = { ...DEFAULT_CONFIG.filters, ...config.filters };

    this.reset();
  }

  // MARK: Iterable
  public hasNext(): boolean {
    return !this.done;
  }

  /**
   * Loads the next values, extending the observable chain.
   */
  public next(): Promise<ModelKey[]> {
    if (this._next) {
      return this._next;
    } else {
      return this.loadNext();
    }
  }

  private loadNext(): Promise<ModelKey[]> {
    if (this.canDoNext()) {
      this._next = this.doNext();
      return this._next;
    } else {
      return Promise.reject(new Error('No more elements to load.'));
    }
  }

  protected canDoNext() {
    return this.hasNext() && !this.isStopped();
  }

  private doNext(): Promise<ModelKey[]> {
    const obs = this.makeNext();

    let promiseDone = false;
    let resolve: (keys: ModelKey[]) => void;
    let reject: (reason?: any) => void;

    const promise = new Promise<ModelKey[]>((x, y) => {
      resolve = x;
      reject = y;
    });

    this._nextSub = obs.subscribe((result) => {
      resolve(result.keys);
      promiseDone = true;

      // Resolve promise first, otherwise it will be cleared and fail.
      this.updateWithResult(result);
    }, (error) => {
      reject(error);
      promiseDone = true;

      this.updateWithError(error);
    }).add(() => {
      // Make sure to reject the promise if unsubscribed early.
      if (!promiseDone) {
        reject(new Error('Canceled.'));
      }
    });

    return promise;
  }

  private makeNext(): Observable<KeyResultPair<T>> {
    const prev = this._result;

    const request: SearchRequest = {
      ...prev.request,
      isKeysOnly: true,
      cursor: prev.response.cursor
    };

    super.setState(SourceState.Loading);

    const obs = this._service.query(request).pipe(
      map((response: ModelSearchResponse<T>) => {
        return new KeyResultPair<T>(request, response);
      }),
      share()
    );

    return obs;
  }

  private updateWithResult(result: KeyResultPair<T>): void {
    this._result = result;

    const newModels: ModelKey[] = result.keys;
    let state = SourceState.Idle;

    if (result.isFinal) {
      this.done = true;
      state = SourceState.Done;
    }

    this.addElements(newModels, state);
    this.clearNext();
  }

  private updateWithError(error): void {
    this.setState(SourceState.Error);
    this.clearNext();
  }

  // MARK: Reset
  public reset() {
    if (!this.isStopped()) {
      this.resetQuerySource();
      super.reset();
      // this.next(); //Don't call next, as the source could be reconfiguring.
    }
  }

  protected resetQuerySource() {
    this.done = false;
    this.clearNext();
    this._result = this.makeInitialResult();
  }

  private clearNext() {
    if (this._nextSub) {
      this._nextSub.unsubscribe();
      this._nextSub = undefined;
    }

    this._nextSub = undefined;
    this._next = undefined;
  }

  private makeInitialResult(): KeyResultPair<T> {
    const defaultRequest: SearchRequest = {
      isKeysOnly: false,
      parameters: this._config.filters,
      limit: this._config.limit,
      offset: 0
    };

    const defaultResponse = {
      isKeysOnlyResponse: false,
      keyResults: [],
      modelResults: [],
      cursor: this._config.cursor,
    };

    return new DefaultKeyResultPair<T>(defaultRequest, defaultResponse);
  }

  // MARK: Cleanup
  protected stop() {
    this.clearNext();
    super.stop();
  }

}

export class KeyResultPair<T> {

  constructor(private _request: SearchRequest, private _response: ModelSearchResponse<T>) { }

  get request() {
    return this._request;
  }

  get response() {
    return this._response;
  }

  get isFinal() {
    return this._response.keyResults.length < this._request.limit; // this.response.modelResults.length === 0;
  }

  get keys() {
    return this.response.keyResults;
  }

}

export class DefaultKeyResultPair<T> extends KeyResultPair<T> {

  get isFinal() {
    return false;
  }

}

// MARK: Merged
/**
 * MergedReadQuerySource that forwards all functions to the query source,
 * but uses the read source as the element stream.
 */
export class MergedReadQuerySource<T extends UniqueModel> implements ControllableSource<T> {

  private _stream: Observable<SourceEvent<T>>;

  /*
  private _nextSubject = new Subject<{}>;
  private _nextObs: Observable<{}>;
  */
  constructor(private _readSource?: ReadSource<T>, private _querySource?: KeyQuerySource<T>) {
    let readUpdated = false;

    this._stream = combineLatest(this._readSource.stream, this._querySource.stream).pipe(
      map(([read, query]) => {

        // Whenever the query state becomes 2, and read isn't 2 yet, the read still has to catch up.
        // Without this, the result will sometimes return an "Idle" state before the read updates itself.
        // This issue would allow another next() to be called immediately after the query completes, but not the read source.
        if (query.state === SourceState.Loading && read.state !== SourceState.Loading) {
          readUpdated = false;
        } else if (read.state === SourceState.Loading) {
          readUpdated = true;
        }

        let state = read.state;

        // Check if the read state is either idle or done, then defer to query state.
        if (state === SourceState.Idle || state === SourceState.Done) {
          if (!readUpdated) {
            state = SourceState.Loading;
          } else {
            state = query.state;
          }
        }

        // console.log('rs: ' + read.state + ' qs: ' + query.state + 'State: ' + state);

        return {
          elements: read.elements,
          failed: read.failed,
          state
        };
      })
    );
  }

  get state(): SourceState {
    return this._querySource.state;
  }

  get stream(): Observable<SourceEvent<T>> {
    return this._stream;
  }

  hasNext() {
    return this._querySource.hasNext();
  }

  next() {
    return this._querySource.next();
  }

  refresh() {
    this._querySource.refresh();
  }

  destroy() {
    this._querySource.destroy();
    this._readSource.destroy();
  }

  reset() {
    this._querySource.reset();
    // this._readSource.reset();
  }

}

// MARK: Predictive Ordered Query / CachedKeySource
export interface KeyedPredictiveOrderedQueryStreamEvent {
  readonly updated?: ModelKey[];  // Updated keys go to the top when the event is called.
  readonly removed?: ModelKey[];  // Removed keys are ignored until reset.
}

export interface KeyedPredictiveOrderedQueryStream {
  readonly delegateStream: Observable<KeyedPredictiveOrderedQueryStreamEvent>;
}

export type CachedKeySourceCacheNext = ([ModelKey[], boolean]);

export interface CachedKeySourceCacheEvent {

  readonly offset: number;

}

export type CachedKeySourceCacheFactoryConfigurationHash = string;
export type CachedKeySourceCacheConfiguration<T> = [IterableSource<ModelKey>, KeyedPredictiveOrderedQueryStream];

export interface CachedKeySourceCacheFactoryDelegate<T, C> {
  hashConfig(config: C): CachedKeySourceCacheFactoryConfigurationHash;
  componentsForConfig(config: C): CachedKeySourceCacheConfiguration<T>;
}

export abstract class AbstractCachedKeySourceCacheFactoryDelegate<T> implements CachedKeySourceCacheFactoryDelegate<T, string> {

  public hashConfig(config: string): CachedKeySourceCacheFactoryConfigurationHash {
    return config.toString();
  }

  public abstract componentsForConfig(config: string): CachedKeySourceCacheConfiguration<T>;

}

export class CachedKeySourceCacheFactory<T extends UniqueModel, C> {

  private _map = new Map<CachedKeySourceCacheFactoryConfigurationHash, CachedKeySourceCache<T>>();

  constructor(private _delegate: CachedKeySourceCacheFactoryDelegate<T, C>) { }

  // MARK: Factory
  public getCache(config: C): CachedKeySourceCache<T> {
    const hash = this._delegate.hashConfig(config);
    let cache = this._map.get(hash);

    if (!cache) {
      cache = this.makeNewCache(config);
      this._map.set(hash, cache);
    }

    return cache;
  }

  public makeNewCache(config: C): CachedKeySourceCache<T> {
    const components: CachedKeySourceCacheConfiguration<T> = this._delegate.componentsForConfig(config);

    const source: IterableSource<ModelKey> = components[0];
    const delegate: KeyedPredictiveOrderedQueryStream = components[1];

    return new CachedKeySourceCache(source, delegate);
  }

}

export class CachedKeySourceCache<T extends UniqueModel> {

  private _cache: string[];   // model keys as strings

  private _next?: Promise<ModelKey[]>;

  private _keySet: Set<string>;
  private _removed: Set<string>;

  // TODO: Shouldn't this be used somewhere?
  private _streamSub: Subscription;

  private _cacheEvents = new Subject<CachedKeySourceCacheEvent>();
  private _cacheEventsObs = this._cacheEvents.asObservable();

  constructor(private _source: IterableSource<ModelKey>, _stream: KeyedPredictiveOrderedQueryStream) {
    this.resetCache();
    this._streamSub = _stream.delegateStream.subscribe((x) => this.updateCacheWithDelegateEvent(x));
  }

  // MARK: Events
  public get events() {
    return this._cacheEventsObs;
  }

  public makeNewSource(): CachedKeySource<T> {
    return new CachedKeySource<T>(this);
  }

  // MARK: Next
  public hasNext(index: number): boolean {
    // Check the cache, then check the source.
    return this.cacheHasNext(index) || this._source.hasNext();
  }

  public next(index: number, limit: number, onLoading: () => void): Promise<CachedKeySourceCacheNext> {
    let promise: Promise<CachedKeySourceCacheNext>;

    const nextFromCache = this.buildNext(index, limit);

    if (nextFromCache.length === limit) {

      // No need to update using the source.
      promise = Promise.resolve([nextFromCache, true] as CachedKeySourceCacheNext);
    } else {
      onLoading();
      promise = this.sourceNext().then((newNext) => {
        const requestedNext = ValueUtility.takeFromArrays([nextFromCache, newNext], limit);

        // console.log('Taking from arrays: ' + newNext);
        return [requestedNext, this._source.hasNext()] as CachedKeySourceCacheNext;
      }, () => {
        return [nextFromCache, false] as CachedKeySourceCacheNext;
      });
    }

    return promise;
  }

  private sourceNext(): Promise<ModelKey[]> {
    if (!this._next) {
      this._next = this._source.next().then((x) => {
        // console.log('Next success.' + x);
        this.updateCacheWithSourceNext(x);
        this._next = undefined;
        return x;
      }, (error) => {
        // console.log('Next failed.');
        this._next = undefined;
        return Promise.reject(error);
      });
    }

    return this._next;
  }

  public rebuildElements(index: number) {
    return this.buildNext(0, index);
  }

  // MARK: Internal
  private resetCache(): void {
    this._cache = [];
    this._removed = new Set<string>();
    this._keySet = new Set<string>();
  }

  private updateCacheWithSourceNext(keys: ModelKey[]): void {
    this.addToCache(keys);
  }

  private updateCacheWithDelegateEvent(event: KeyedPredictiveOrderedQueryStreamEvent): void {
    this.updateCacheWithAddedAndRemoved(event.removed, event.updated);
  }

  private updateCacheWithAddedAndRemoved(removed: ModelKey[] = [], updated: ModelKey[] = []) {

    // console.log('Updating cache with added/removed: ' + removed + ' | ' + updated);

    removed = ModelUtility.modelKeysAsUniqueStrings(removed);
    updated = ModelUtility.modelKeysAsUniqueStrings(updated);

    // Array of items to remove from the current cache. Updated will be appended to the top.
    const clearSet: Set<string> = new Set<string>();

    let newUniqueCount = 0;
    let removedCount = 0;

    updated.forEach((stringKey: string) => {
      clearSet.add(stringKey);

      if (!this._keySet.has(stringKey)) {
        newUniqueCount += 1;
        this._keySet.add(stringKey);
      }
    });

    removed.forEach((stringKey: string) => {
      clearSet.add(stringKey);

      if (this._keySet.has(stringKey)) {
        removedCount += 1;
        this._keySet.delete(stringKey);
      }

      this._removed.add(stringKey);
    });

    // Create a new cache array.
    const head: string[] = [].concat(updated);
    const filtered: string[] = this._cache.filter((x: string) => !clearSet.has(x));

    const cache = head.concat(filtered);
    const offset = newUniqueCount; // Consider adding/using the rmeoved count?

    this.setCache(cache, offset);
  }

  private addToCache(newElements: ModelKey[]) {
    // console.log('Adding new elements to cache: ' + newElements);

    newElements = ModelUtility.modelKeysAsStrings(newElements);

    const cache = this._cache.concat(newElements as string[]);   // Concat new elements.

    newElements.forEach((x) => this._keySet.add(x.toString())); // Add to the set.

    this.setCache(cache);
  }

  private setCache(cache: string[], offset: number = 0) {
    this._cache = cache;

    const event: CachedKeySourceCacheEvent = {
      offset
    };

    this._cacheEvents.next(event);
  }

  private buildNext(index: number, limit: number): ModelKey[] {
    if (this.cacheHasNext(index)) {
      const end = index + limit;
      return this._cache.slice(index, end);
    } else {
      return [];
    }
  }

  private cacheHasNext(index: number) {
    // Has next as long as the cache's length is greater than the requested start index.
    return index < this._cache.length;
  }

}

/**
 * KeyQuerySource wrapper for queries that are generally ordered by date.
 */
export class CachedKeySource<T extends UniqueModel> extends AbstractSource<ModelKey> implements IterableSource<ModelKey> {

  public limit = DEFAULT_CONFIG.limit;

  private _cacheSub: Subscription;

  private _next?: Promise<ModelKey[]>;

  constructor(private _cache: CachedKeySourceCache<T>) {
    super();
    this._cacheSub = _cache.events.subscribe((x) => this.updateWithCacheEvent(x));
  }

  protected get index() {
    return this.currentElements.length;
  }

  // MARK: Iterable
  hasNext(): boolean {
    const index = this.index;
    return this._cache.hasNext(index);
  }

  next(): Promise<ModelKey[]> {
    if (!this._next) {
      if (this.hasNext()) {
        this.setState(SourceState.Loading);
      } else {
        this.setState(SourceState.Done);
      }

      const index = this.index;
      const limit = this.limit;

      // console.log('New next: ' + index)

      this._next = this._cache.next(index, limit, () => {
        this.setState(SourceState.Loading);
      }).then((x) => {
        // console.log('Next is done: ' + index)
        this._next = undefined;
        this.updateWithNext(x);
        return x[0];
      }, (error) => {
        // console.log('Next failed: ' + index)
        this._next = undefined;
        return Promise.reject(error);
      });
    }

    return this._next;
  }


  private updateWithNext(next: CachedKeySourceCacheNext): void {
    const nextElements = next[0];

    if (nextElements.length > 0) {
      const hasMore = next[1];
      const state = (hasMore) ? SourceState.Idle : SourceState.Done;

      this.addElements(nextElements, state);
    } else {
      this.addElements(nextElements, SourceState.Done);
    }
  }

  reset(): void {
    super.reset();
  }

  refresh(): void {
    // Do nothing, maybe reset the index?
  }

  stop(): void {
    super.stop();
    this._cacheSub.unsubscribe();
  }

  // MARK: Cache
  private updateWithCacheEvent(event: CachedKeySourceCacheEvent) {
    let index = this.index;

    if (this.state !== SourceState.Loading) {
      index += event.offset;

      if (index < 0) {
        index = 0;
      }

      const elements = this._cache.rebuildElements(index);
      const state = (this.state) > SourceState.Reset ? this.state : SourceState.Idle;
      this.setElements(elements, state);
    }

    // Otherwise do nothing...
  }

}
