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

  removeAll(keys: K[]): void;

  clear(): void;

}

// MARK: Observable
export enum KeyedCacheChange {
  Put,
  Remove,
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

  removeAll(keys: K[]): void {
    keys.forEach((key) => {
      this.remove(key);
    });
  }

  clear(): void {
    this._cache.clear();
    this._keys.clear();
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
    keys.forEach((key) => {
      this.remove(key);
    });
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
    return this._subject;
  }

}

// MARK: Async Cache
export class AsyncCacheWrap<K, T> extends AbstractKeyedCacheWrap<K, T, ObservableKeyedCache<K, T>> implements AsyncObservableCache<K, T> {

  constructor(cache: ObservableKeyedCache<K, T> = new ObservableCacheWrap<K, T>()) {
    super(cache);
  }

  // MARK: AsyncCache
  public asyncRead(keys: OneOrMore<K>, { debounce = 20, filterPut = true }): Observable<KeyedCacheLoad<K, T>> {
    keys = ValueUtility.normalizeArray(keys);

    let events = this._cache.events;

    if (filterPut) {
      events = events.pipe(filter((event) => event.change !== KeyedCacheChange.Put));
    }

    return events.pipe(
      startWith({
        change: KeyedCacheChange.Clear,
        keys: new Set<K>()
      }),
      debounceTime(debounce),
      map(() => this.load(keys as K[]))
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

}

export interface AsyncStreamedModelCache<T extends UniqueModel> extends ModelCache<T>, AsyncKeyedCache<ModelKey, T> { }

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
      models.forEach((model) => {
          this.putModel(model);
      });
  }

  load(keys: ModelKey[]) {
      keys = ModelUtility.makeStringModelKeysArray(keys);
      return super.load(keys);
  }

  hasModel(modelOrKey: ModelOrKey<T>): boolean {
      const key = ModelUtility.readModelKeyString(modelOrKey);

      if (key) {
          return super.has(key);
      }

      return false;
  }

}

