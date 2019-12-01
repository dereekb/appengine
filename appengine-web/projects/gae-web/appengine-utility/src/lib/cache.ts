import { DateTime } from 'luxon';
import { Observable, Subject } from 'rxjs';
import { ValueUtility, OneOrMore } from './value';
import { filter, startWith, debounceTime, map } from 'rxjs/operators';
import { ModelKey, ModelOrKey, UniqueModel, ModelUtility } from './model';
import { Keyed } from './collection';

/**
 * Object that acts as a cache for a value.
 */
export interface Cache<T> {

  /**
   * Value from the source.
   */
  value: T;

}

export interface RefreshableCache<T> extends Cache<T> {

  /**
   * Whether or not the current cache value has expired or not.
   */
  readonly hasExpired: boolean;

  /**
   * Forces a refresh of the current value.
   */
  refresh(): T;

}

export interface TimedRefreshableCache<T> extends RefreshableCache<T> {

  /**
   * Time at which the item is set to expire at. If undefined, the item will not expire.
   */
  resetTime: DateTime | undefined;

}

/**
 * Basic Cache implementation.
 */
export class SimpleCache<T> implements Cache<T> {

  constructor(protected _value?: T) { }

  get value() {
    return this._value;
  }

  set value(value: T) {
    this._value = value;
  }

}

/**
 * Cache implementation.
 */
export abstract class AbstractRefreshableCache<T> extends SimpleCache<T> implements Cache<T> {

  constructor() {
    super();
  }

  expire() {
    if (!this.hasExpired) {
      this._expire();
    }
  }

  get hasExpired(): boolean {
    return this._checkExpiration();
  }

  get value() {
    return this.getValue();
  }

  set value(value: T) {
    this.setValue(value);
  }

  getValue(): T {
    if (this.hasExpired) {
      return this.refresh();
    }

    return this._value;
  }

  setValue(value: T) {
    // NOTE: Does not effect expiration.
    this._value = value;
  }

  refresh(): T {
    this.setValue(this._refresh());
    return this._value;
  }

  // MARK: Internal
  abstract _expire();

  abstract _checkExpiration(): boolean;

  abstract _refresh(): T;

}

export interface CacheValueDelegate<T> {
  refresh(): T;
}

/**
 * Lazy-loaded source that uses a delegate.
 */
export class LazyCache<T> extends AbstractRefreshableCache<T> {

  private _loaded: boolean;

  constructor(private _delegate: CacheValueDelegate<T>) {
    super();
  }

  setValue(value: T) {
    // NOTE: Does not effect expiration.
    this._value = value;
    this._loaded = true;
  }

  // MARK: Internal
  _expire() {
    this._loaded = false;
  }

  _checkExpiration(): boolean {
    if (!this._loaded) {
      return true;
    }

    return false;
  }

  _refresh(): T {
    return this._delegate.refresh();
  }

}

/**
 * TimedRefreshableCache implementation that uses a delegate for refreshing, and time for checking expiration.
 */
export class TimedCache<T> extends AbstractRefreshableCache<T> implements TimedRefreshableCache<T> {

  private _loaded: boolean;
  private _lifetime?: number;
  private _resetTime?: DateTime;

  constructor(private _delegate: CacheValueDelegate<T>, lifetime?: number) {
    super();
    this.lifetime = lifetime;
  }

  get resetTime() {
    return this._resetTime;
  }

  set resetTime(resetTime: DateTime | undefined) {
    this._resetTime = resetTime;
  }

  get lifetime() {
    return this._lifetime;
  }

  set lifetime(lifetime: number | undefined) {
    this._lifetime = lifetime;
    this._refreshResetTime();
  }

  setValue(value: T) {
    this._refreshResetTime();
    this._loaded = true;
    super.setValue(value);
  }

  refresh(): T {
    this._loaded = false;
    return super.refresh();
  }

  // MARK: Internal
  _expire() {
    this._loaded = false;
  }

  _refreshResetTime() {
    if (this._lifetime) {
      this._resetTime = DateTime.local().plus({ milliseconds: this._lifetime });
    } else {
      this._resetTime = undefined;
    }
  }

  _checkExpiration(): boolean {
    if (!this._loaded) {
      return true;
    } else if (this._resetTime) {
      return DateTime.local() >= this._resetTime;
    }

    return false;
  }

  _refresh(): T {
    return this._delegate.refresh();
  }

}

// MARK: Promise
export interface PromiseCacheValueDelegate<T> extends CacheValueDelegate<Promise<T>> { }

export interface PromiseCachedCache<T> extends Cache<Promise<T>> { }

export class PromiseCachedCache<T> implements PromiseCachedCache<T> {

  constructor(private _source: RefreshableCache<Promise<T>>) { }

  get value(): Promise<T> {
    return this._source.value;
  }

  get hasExpired(): boolean {
    return this._source.hasExpired;
  }

  refresh(): Promise<T> {
    return this._source.refresh();
  }

}


// MARK: Keyed Cache
export interface KeyedCacheLoad<K, T> {
  readonly hits: T[];
  readonly misses: K[];
}

export interface KeyedCache<K, T> {

  keys: Set<K>;

  load(keys: K[]): KeyedCacheLoad<K, T>;

  put(key: K, model: T);

  get(key: K): T | undefined;

  has(key: K): boolean;

  remove(key: K): T;

  removeAll(keys: K[]): T[];

  clear(): void;

}

export interface TimedKeyedCache<K, T> extends KeyedCache<K, T> {

  /**
   * Amount of time in milliseconds in which each item will live. If undefined, the item will not expire.
   */
  timeToLive: number | undefined;

}

// MARK: Observable
export enum KeyedCacheChange {
  /**
   * When an item is put into the cache.
   */
  Put,

  /**
   * When an item is removed from the cache.
   */
  Remove,

  /**
   * When the entire cache is cleared.
   */
  Clear
}

export interface KeyedCacheEvent<K> {
  readonly change: KeyedCacheChange;
  readonly keys: Set<K>;  // Set of keys that were changed.
}

export interface ObservableKeyedCache<K, T> extends KeyedCache<K, T> {

  /**
   * Observable of cache events.
   */
  readonly events: Observable<KeyedCacheEvent<K>>;

}

// MARK: Async Cache
export interface AsyncKeyedCacheReadConfig {
  debounce?: number;
  filterPut?: boolean;
}

export interface AsyncKeyedCache<K, T> {

  asyncRead(keys: OneOrMore<K>, config: AsyncKeyedCacheReadConfig): Observable<KeyedCacheLoad<K, T>>;

}

export interface AsyncObservableCache<K, T> extends AsyncKeyedCache<K, T>, ObservableKeyedCache<K, T> { }

/**
 * KeyedCache implementation that uses a map.
 */
export class MapKeyedCache<K, T> implements KeyedCache<K, T> {

  private _keys: Set<K>;
  private _cache: Map<K, T>;

  constructor(cache = new Map<K, T>()) {
    this.cache = cache;
  }

  public set cache(cache: Map<K, T>) {
    this._cache = cache;
    this._keys = ValueUtility.mapKeysSet(this._cache);
  }

  // MARK: Cache
  get keys(): Set<K> {
    return this._keys;
  }

  load(keys: K[]): KeyedCacheLoad<K, T> {
    const result: KeyedCacheLoad<K, T> = {
      hits: [],
      misses: []
    };

    keys.forEach((key) => {
      const model = this.get(key);

      if (model) {
        result.hits.push(model);
      } else {
        result.misses.push(key);
      }
    });

    return result;
  }

  put(key: K, model: T) {
    this._cache.set(key, model);
    this._keys.add(key);
  }

  get(key: K): T | undefined {
    return this._cache.get(key);
  }

  has(key: K): boolean {
    return this._keys.has(key);
  }

  remove(key: K): T {
    const model = this._cache.get(key);

    if (model) {
      this._cache.delete(key);
      this._keys.delete(key);
    }

    return model;
  }

  removeAll(keys: K[]): T[] {
    return keys.map((key) => this.remove(key));
  }

  clear(): void {
    this._cache.clear();
    this._keys.clear();
  }

}

/**
 * Element used within the MapTimeKeyedCache.
 */
export class MapTimeKeyedCacheItem<K, T> {

  public readonly resetTime?: DateTime;

  constructor(public readonly key: K, public readonly item: T, timeToLive?: number) {
    this.resetTime = (timeToLive) ? DateTime.local().plus({ milliseconds: timeToLive }) : undefined;
  }

  get hasExpired(): boolean {
    if (this.resetTime) {
      return DateTime.local() >= this.resetTime;
    }

    return false;
  }

}

export type MapTimeKeyedCacheOnExpiredFunction<K, T> = (item: MapTimeKeyedCacheItem<K, T>) => void;

export class MapTimeKeyedCache<K, T> implements TimedKeyedCache<K, T> {

  private _timeToLive?: number;
  private _cache: MapKeyedCache<K, MapTimeKeyedCacheItem<K, T>>;

  constructor(timeToLive: number, private readonly _onItemExpired: MapTimeKeyedCacheOnExpiredFunction<K, T> = () => 0) {
    this.timeToLive = timeToLive;
  }

  // MARK: TimedKeyedCache
  get timeToLive() {
    return this._timeToLive;
  }

  set timeToLive(timeToLive: number | undefined) {
    this._cache = new MapKeyedCache();
    this._timeToLive = timeToLive;
  }

  // MARK: Cache
  get keys(): Set<K> {
    return this._cache.keys;
  }

  load(keys: K[]): KeyedCacheLoad<K, T> {
    const cacheResult: KeyedCacheLoad<K, MapTimeKeyedCacheItem<K, T>> = this._cache.load(keys);

    const hits: T[] = [];
    const misses: K[] = cacheResult.misses;

    cacheResult.hits.forEach(x => {
      if (this._isStillAliveCheck(x)) {
        hits.push(x.item);
      } else {
        misses.push(x.key);
      }
    });

    const result: KeyedCacheLoad<K, T> = {
      hits,
      misses
    };

    return result;
  }

  put(key: K, model: T) {
    const cacheItem = new MapTimeKeyedCacheItem<K, T>(key, model, this.timeToLive);
    this._cache.put(key, cacheItem);
  }

  get(key: K): T | undefined {
    return this._isStillAliveCheckKey(key);
  }

  has(key: K): boolean {
    return this._isStillAliveCheckKey(key) !== undefined;
  }

  remove(key: K): T {
    const result = this._cache.remove(key);
    return (result) ? result.item : undefined;
  }

  removeAll(keys: K[]): T[] {
    return this._cache.removeAll(keys).map(x => x.item);
  }

  clear(): void {
    this._cache.clear();
  }

  // MARK: Internal
  private _isStillAliveCheckKey(key: K): T {
    const cacheItem = this._cache.get(key);

    if (cacheItem) {
      return this._isStillAliveCheckItem(cacheItem);
    } else {
      return undefined;
    }
  }

  private _isStillAliveCheckItem(cacheItem: MapTimeKeyedCacheItem<K, T>) {
    if (this._isStillAliveCheck(cacheItem)) {
      return cacheItem.item;
    } else {
      return undefined;
    }
  }

  /**
   * Checks the expiration of an item. If an item has expired, it is removed from the cache and false is returned.
   */
  private _isStillAliveCheck(cacheItem: MapTimeKeyedCacheItem<K, T>): boolean {
    if (cacheItem.hasExpired) {
      this._onItemExpired(cacheItem);
      this._cache.remove(cacheItem.key);
      return false;
    } else {
      return true;
    }
  }

}

export abstract class AbstractKeyedCacheWrap<K, T, C extends KeyedCache<K, T>> implements KeyedCache<K, T> {

  constructor(protected _cache: C) { }

  get keys(): Set<K> {
    return this._cache.keys;
  }

  put(key: K, model: T) {
    this._cache.put(key, model);
  }

  load(keys: K[]): KeyedCacheLoad<K, T> {
    return this._cache.load(keys);
  }

  get(key: K): T | undefined {
    return this._cache.get(key);
  }

  has(key: K): boolean {
    return this._cache.has(key);
  }

  remove(key: K) {
    return this._cache.remove(key);
  }

  removeAll(keys: K[]) {
    return keys.map((key) => this.remove(key));
  }

  clear() {
    this._cache.clear();
  }

}

// MARK: Observable
export class ObservableCacheWrap<K, T> extends AbstractKeyedCacheWrap<K, T, KeyedCache<K, T>> implements ObservableKeyedCache<K, T> {

  private _subject = new Subject<KeyedCacheEvent<K>>();

  constructor(cache: KeyedCache<K, T> = new MapKeyedCache<K, T>()) {
    super(cache);
  }

  // MARK: Cache
  put(key: K, model: T) {
    this._cache.put(key, model);
    this.next(KeyedCacheChange.Put, key);
  }

  remove(key: K) {
    const removed = this._cache.remove(key);

    if (removed) {
      this.next(KeyedCacheChange.Remove, key);
    }

    return removed;
  }

  clear() {
    const keys = this.keys;
    this._cache.clear();
    this._subject.next({
      change: KeyedCacheChange.Clear,
      keys
    });
  }

  protected next(change: KeyedCacheChange, keys: OneOrMore<K>) {
    const keysArray = ValueUtility.normalizeArray(keys);
    const keysSet = ValueUtility.arrayToSet(keysArray);

    this._subject.next({
      change,
      keys: keysSet
    });
  }

  // MARK: Cache Stream
  public get events(): Observable<KeyedCacheEvent<K>> {
    return this._subject.asObservable();
  }

}

/**
 * Convenience ObservableCacheWrap extension that uses a MapTimeKeyedCache and provides accessors to the expiration time.
 */
export class TimedObservableCacheWrap<K, T> extends ObservableCacheWrap<K, T> {

  constructor(timeToLive?: number) {
    super(new MapTimeKeyedCache<K, T>(timeToLive, (x) => this._onItemExpired(x)));
  }

  // MARK: Timed Cache
  private get timedCache() {
    return this._cache as MapTimeKeyedCache<K, T>;
  }

  get timeToLive(): number | undefined {
    return this.timedCache.timeToLive;
  }

  set timeToLive(timeToLive: number | undefined) {
    this.timedCache.timeToLive = timeToLive;
  }

  private _onItemExpired(item: MapTimeKeyedCacheItem<K, T>) {
    // Notify that the item was removed from the cache.
    this.next(KeyedCacheChange.Remove, item.key);
  }

}

// MARK: Async Cache
export interface KeyedAsyncCacheLoad<K, T> extends KeyedCacheLoad<K, T> {
  readonly event?: KeyedCacheEvent<K>;
}

export interface AsyncCacheReadOptions {
  debounce?: number;
  ignoredChanges?: KeyedCacheChange[];
}

export class AsyncCacheWrap<K, T> extends AbstractKeyedCacheWrap<K, T, ObservableKeyedCache<K, T>> implements AsyncObservableCache<K, T> {

  constructor(cache: ObservableKeyedCache<K, T> = new ObservableCacheWrap<K, T>()) {
    super(cache);
  }

  // MARK: AsyncCache
  public asyncRead(keys: OneOrMore<K>, { debounce = 10, ignoredChanges }: AsyncCacheReadOptions): Observable<KeyedCacheLoad<K, T>> {
    keys = ValueUtility.normalizeArray(keys);

    let events = this._cache.events;

    if (ignoredChanges && ignoredChanges.length) {
      const ignoredTypesSet = ValueUtility.arrayToSet(ignoredChanges);
      events = events.pipe(filter((event) => !ignoredTypesSet.has(event.change)));
    }

    return events.pipe(
      startWith({
        change: KeyedCacheChange.Clear,
        keys: new Set<K>()
      }),
      debounceTime(debounce),
      map((event) => ({ ...this.load(keys as K[]), event }))
    );
  }

  // MARK: Observable Cache
  public get events(): Observable<KeyedCacheEvent<K>> {
    return this._cache.events;
  }

}

// MARK: Model Cache
export interface ModelCache<T extends Keyed<ModelKey>> extends KeyedCache<ModelKey, T> {

  putModel(model: T): boolean;

  putModels(models: T[]): void;

  hasModel(modelOrKey: ModelOrKey<T>): boolean;

  removeModel(model: T): T;

  removeModels(models: T[]): T[];

}

export interface AsyncStreamedModelCache<T extends UniqueModel> extends ModelCache<T>, AsyncKeyedCache<ModelKey, T> { }

/**
 * AsyncCacheWrap for UniqueModels of the same type.
 *
 * NOTE: Internally all ModelKey values are converted to strings, so misses are returned as string values instead of number values.
 * Use KeySafeAsyncModelCacheWrap if the "misses" value type should be the original key type input.
 */
export class AsyncModelCacheWrap<T extends UniqueModel> extends AsyncCacheWrap<ModelKey, T> implements AsyncStreamedModelCache<T> {

  // MARK: ModelCache
  put(key: ModelKey, model: T) {
    super.put(String(key), model);
  }

  putModel(model: T): boolean {
    const key = ModelUtility.readModelKeyString(model);

    if (key) {
      super.put(key, model);
      return true;
    }

    return false;
  }

  has(key: ModelKey): boolean {
    return super.has(String(key));
  }

  remove(key: ModelKey) {
    return super.remove(String(key));
  }

  putModels(models: T[]): void {
    models.forEach((model) => this.putModel(model));
  }

  load(keys: ModelKey[]) {
    const stringKeys = ModelUtility.makeStringModelKeysArray(keys, true);
    return super.load(stringKeys);
  }

  hasModel(modelOrKey: ModelOrKey<T>): boolean {
    const key = ModelUtility.readModelKeyString(modelOrKey);

    if (key) {
      return super.has(key);
    }

    return false;
  }

  removeModel(model: T): T {
    const key = ModelUtility.readModelKeyString(model);
    return this.remove(key);
  }

  removeModels(models: T[]): T[] {
    const modelKeys = ModelUtility.readModelKeysFromModels(models);
    return this.removeAll(modelKeys);
  }

}

/**
 * AsyncModelCacheWrap implementation that implements the interface but does not cache values.
 */
export class NonCacheAsyncModelCacheWrap<T extends UniqueModel> extends AsyncModelCacheWrap<T> {

  // TODO: Can consider making this implements AsyncModelCacheWrap<T>,
  // but the implications of making readAsync be an observable that ends is unknown.

  // MARK: ModelCache
  put(key: ModelKey, model: T) {
    // Do nothing.
  }

  putModel(model: T): boolean {
    const key = ModelUtility.readModelKeyString(model);

    if (key) {
      super.put(key, model);
      return true;
    }

    return false;
  }

  has(key: ModelKey): boolean {
    return false;
  }

  remove(key: ModelKey) {
    return undefined;
  }

  putModels(models: T[]): void {
    // Do nothing.
  }

  load(keys: ModelKey[]) {
    const results: KeyedCacheLoad<ModelKey, T> = {
      hits: [],
      misses: keys
    };

    return results;
  }

  hasModel(modelOrKey: ModelOrKey<T>): boolean {
    return false;
  }

  removeModel(model: T): T {
    return undefined;
  }

  removeModels(models: T[]): T[] {
    return [];
  }

  // MARK: Cache
  get keys(): Set<ModelKey> {
    return new Set();
  }

  get(key: ModelKey): T | undefined {
    return undefined;
  }

  clear() {
    // Do nothing.
  }

}

/**
 * AsyncModelCacheWrap extension that returns any "missed" keys using their original type.
 */
export class KeySafeAsyncModelCacheWrap<T extends UniqueModel> extends AsyncModelCacheWrap<T> {

  load(keys: ModelKey[]) {
    const result = super.load(keys);
    let misses = result.misses;

    if (result.misses.length > 0) {
      const keysObjectMapping = ValueUtility.convertArrayToMirrorMap(keys);
      misses = result.misses.map((x) => keysObjectMapping[x]);
    }

    return {
      ...result,
      misses
    };

  }

}

/**
 * Convenience KeySafeAsyncModelCacheWrap extension that can let items expire.
 */
export class TimedKeySafeAsyncModelCacheWrap<T extends UniqueModel> extends KeySafeAsyncModelCacheWrap<T> {

  constructor(timeToLive?: number) {
    super(new TimedObservableCacheWrap<ModelKey, T>(timeToLive));
  }

  // MARK: Timed Cache
  private get timedCache() {
    return this._cache as TimedObservableCacheWrap<ModelKey, T>;
  }

  get timeToLive(): number | undefined {
    return this.timedCache.timeToLive;
  }

  set timeToLive(timeToLive: number | undefined) {
    this.timedCache.timeToLive = timeToLive;
  }

}
