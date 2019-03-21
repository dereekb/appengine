import { DateTime } from 'luxon';

/**
 * Object that acts as a cache for a value.
 */
export interface ICache<T> {

  /**
   * Value from the source.
   */
  value: T;

}

export interface IRefreshableCache<T> extends ICache<T> {

  /**
   * Whether or not the current cache value has expired or not.
   */
  readonly hasExpired: boolean;

  /**
   * Forces a refresh of the current value.
   */
  refresh(): T;

}

export interface ITimedCache<T> extends ICache<T> {

  /**
   * Time at which the item is set to expire at. If undefined, the item will not expire.
   */
  resetTime: DateTime | undefined;

}

/**
 * Basic ICache implementation.
 */
export class Cache<T> implements ICache<T> {

  constructor(protected _value?: T) { }

  get value() {
    return this._value;
  }

  set value(value: T) {
    this._value = value;
  }

}

/**
 * ICache implementation.
 */
export abstract class AbstractRefreshableCache<T> extends Cache<T> implements ICache<T> {

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

export interface ICacheValueDelegate<T> {
  refresh(): T;
}

/**
 * Lazy-loaded source that uses a delegate.
 */
export class LazyCache<T> extends AbstractRefreshableCache<T> {

  private _loaded: boolean;

  constructor(private _delegate: ICacheValueDelegate<T>) {
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
 * ITimedCache implementation that uses a delegate for refreshing, and time for checking expiration.
 */
export class TimedCache<T> extends AbstractRefreshableCache<T> implements ITimedCache<T> {

  private _loaded: boolean;
  private _lifetime?: number;
  private _resetTime?: DateTime;

  constructor(private _delegate: ICacheValueDelegate<T>, lifetime?: number) {
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
export interface IPromiseCacheValueDelegate<T> extends ICacheValueDelegate<Promise<T>> { }

export interface IPromiseCachedCache<T> extends ICache<Promise<T>> { }

export class PromiseCachedCache<T> implements IPromiseCachedCache<T> {

  constructor(private _source: IRefreshableCache<Promise<T>>) { }

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
