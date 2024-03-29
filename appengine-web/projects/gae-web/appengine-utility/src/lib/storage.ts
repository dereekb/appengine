import { Observable, of, range } from 'rxjs';
import { map, flatMap, filter } from 'rxjs/operators';

import { BaseError } from 'make-error';
import { DateTime } from 'luxon';
import * as localStorageMemory from 'localstorage-memory';
import { ValueUtility } from './value';

/**
 * Stored object accessor that can get/set/remove via a key, or be cleared entirely.
 */
export abstract class LimitedStorageAccessor<T> {

  abstract get(key: string): Observable<T>;

  abstract set(key: string, value: T): Observable<{}>;

  abstract remove(key: string): Observable<{}>;

  abstract clear(): Observable<{}>;

}

/**
 * LimitedStorageAccessor extension that has knowledge of all stored keys.
 */
export abstract class StorageAccessor<T> extends LimitedStorageAccessor<T> {

  abstract all(): Observable<T>;

  abstract allKeys(): Observable<string>;

}

/**
 * StorageAccessor-like object that has immediate/synchronous functionality for get/set.
 */
export abstract class InstantStorageAccessor<T> {

  abstract getNow(key: string): T | undefined;

  abstract setNow(key: string, value: T);

  abstract removeNow(key: string);

}

/**
 * Limited Class/Interface for storing string values synchronously.
 */
export abstract class SimpleStorageObject {

  abstract getItem(key: string): string | undefined;

  abstract setItem(key: string, item: string);

  abstract removeItem(key: string);

}

/**
 * Synchronous Class/Interface for storing string values. Has the same interface as localStorage for the web.
 */
export abstract class StorageObject extends SimpleStorageObject {

  readonly length: number;

  abstract key(index: number): string | undefined;

}

export abstract class FullStorageObject extends StorageObject {

  readonly isPersistant: boolean;

  readonly isAvailable: boolean;

  abstract removeAll(): string[];

}

export class StoredDataError extends BaseError {

  constructor(message?: string) { super(message); }

}

export class DataDoesNotExistError extends StoredDataError {

  constructor(message?: string) { super(message); }

}

export class DataIsExpiredError<T> extends StoredDataError {

  constructor(public readonly data: ReadStoredData<T>, message?: string) { super(message); }

}

export type StoredDataString = string;

export interface StoredData {
  storedAt: string | undefined;
  data: StoredDataString;
}

export interface ReadStoredData<T> extends StoredData {
  expired: boolean;
  convertedData: T;
}

// MARK: AsyncStorageAccessor
export interface AsyncStorageAccessorConverter<T> {
  stringifyValue(value: T): StoredDataString;
  parseValue(data: StoredDataString): T;
}

/**
 * AsyncStorageAccessor delegate.
 */
export interface AsyncStorageAccessorDelegate<T> extends AsyncStorageAccessorConverter<T>, LimitedStorageAccessor<StoredDataString> { }

/**
 * Abstract implementation that uses JSON.stringify by default.
 */
export abstract class AbstractAsyncStorageAccessorDelegate<T> implements AsyncStorageAccessorDelegate<T>  {

  abstract get(key: string): Observable<StoredDataString>;

  abstract set(key: string, value: StoredDataString): Observable<{}>;

  abstract remove(key: string): Observable<{}>;

  abstract clear(): Observable<{}>;

  stringifyValue(value: T): StoredDataString {
    return JSON.stringify(value);
  }

  abstract parseValue(data: StoredDataString): T;

}

export class WrappedAsyncStorageAccessorDelegate<T> implements AsyncStorageAccessorDelegate<T> {

  constructor(private _delegate: LimitedStorageAccessor<StoredDataString>, private _converter: AsyncStorageAccessorConverter<T>) { }

  get(key: string): Observable<StoredDataString> {
    return this._delegate.get(key);
  }

  set(key: string, value: StoredDataString): Observable<{}> {
    return this._delegate.set(key, value);
  }

  remove(key: string): Observable<{}> {
    return this._delegate.remove(key);
  }

  clear(): Observable<{}> {
    return this._delegate.clear();
  }

  stringifyValue(value: T): StoredDataString {
    return this._converter.stringifyValue(value);
  }

  parseValue(data: StoredDataString): T {
    return this._converter.parseValue(data);
  }

}

export class AsyncStorageAccessor<T> implements LimitedStorageAccessor<T> {

  constructor(private readonly _delegate: AsyncStorageAccessorDelegate<T>, protected readonly prefix: string, protected readonly expiration?: number) { }

  public get(inputKey: string): Observable<T> {
    const storeKey = this.makeStorageKey(inputKey);
    return this._delegate.get(storeKey).pipe(
      map((storedData: string | null) => {
        if (storedData) {
          const readStoredData = this.readStoredData(storedData);

          if (!readStoredData.expired) {
            return readStoredData.convertedData;
          } else {
            throw new DataIsExpiredError<T>(readStoredData);
          }
        } else {
          throw new DataDoesNotExistError();
        }
      })
    );
  }

  public set(inputKey: string, inputValue: T): Observable<{ key: string, data: any }> {
    const storeKey = this.makeStorageKey(inputKey);

    const storeData: StoredData = this.buildStoredData(inputValue);
    const data = JSON.stringify(storeData);

    return this._delegate.set(storeKey, data).pipe(
      map(x => ({
        key: storeKey,
        data
      }))
    );
  }

  public remove(key: string): Observable<{}> {
    const storeKey = this.makeStorageKey(key);
    return this._delegate.remove(storeKey);
  }

  public clear(): Observable<{}> {
    return this._delegate.clear();
  }

  // MARK: Internal
  protected makeStorageKey(key: string) {
    return this.prefix + String(key);
  }

  protected stringifyValue(value: T): string {
    return JSON.stringify(value);
  }

  // MARK: Stored Values
  protected readStoredData(storedDataString: string): ReadStoredData<T> {
    const storedData: StoredData = JSON.parse(storedDataString);
    const expired = this.isExpiredStoredData(storedData);
    const convertedData = this._delegate.parseValue(storedData.data);

    return {
      ...storedData,
      expired,
      convertedData
    };
  }

  protected buildStoredData(value: T): StoredData {
    return {
      storedAt: DateTime.local().toISO(),
      data: this.stringifyValue(value)
    };
  }

  protected isExpiredStoredData(storeData: StoredData) {
    if (this.expiration) {
      if (storeData.storedAt) {
        const expirationDate = DateTime.fromISO(storeData.storedAt).plus({ milliseconds: this.expiration });
        return (DateTime.local() > expirationDate);
      }

      return true;
    } else {
      return false;
    }
  }

}

// MARK: Legacy
/**
 * (Legacy) StorageAccessor implementation that wraps a StorageObject and implements the StorageAccessor interface.
 *
 * @deprecated Legacy
 */
export abstract class AbstractStorageAccessor<T> implements StorageAccessor<T>, InstantStorageAccessor<T> {

  constructor(protected readonly storageObject: StorageObject, protected readonly prefix: string, protected readonly expiration?: number) { }

  public get(inputKey: string): Observable<T> {
    return of(inputKey).pipe(map((key: string) => {
      return this.getNow(key);
    }));
  }

  public getNow(inputKey: string): T {
    const storeKey = this.makeStorageKey(inputKey);
    return this.getWithStoreKeyNow(storeKey);
  }

  public set(inputKey: string, inputValue: T): Observable<{}> {
    return of([inputKey, inputValue]).pipe(map(([key, value]: [string, T]) => {
      return this.setNow(key, value);
    }));
  }

  public setNow(inputKey: string, inputValue: T) {
    const storeKey = this.makeStorageKey(inputKey);
    return this.setWithStoreKeyNow(storeKey, inputValue);
  }

  public all(): Observable<T> {
    return this.allKeys().pipe(
      map((storeKey: string) => this.getWithStoreKeyNow(storeKey)),
      filter((value) => Boolean(value))
    );
  }

  public allKeys(): Observable<string> {
    return range(0, this.storageObject.length).pipe(
      map((index) => this.storageObject.key(index)),
      filter((value) => value !== null),
      filter((key: string) => key.startsWith(this.prefix))
    );
  }

  public remove(key: string): Observable<{}> {
    const storeKey = this.makeStorageKey(key);
    return this.removeWithStoreKey(storeKey);
  }

  public removeNow(key: string) {
    const storeKey = this.makeStorageKey(key);
    return this.removeWithStoreKeyNow(storeKey);
  }

  public clear(): Observable<{}> {
    return this.allKeys().pipe(
      flatMap((storeKey: string) => {
        return this.removeWithStoreKey(storeKey);
      })
    );
  }

  // MARK: Internal
  protected makeStorageKey(key: string) {
    return this.prefix + String(key);
  }

  protected stringifyValue(value: T): string {
    return JSON.stringify(value);
  }

  protected abstract buildValueFromData(data: string): T;

  protected isExpiredStoredData(storeData: StoredData) {
    if (this.expiration) {
      if (storeData.storedAt) {
        const expirationDate = DateTime.fromISO(storeData.storedAt).plus({ milliseconds: this.expiration });
        return (DateTime.local() > expirationDate);
      }

      return true;
    } else {
      return false;
    }
  }

  // MARK: Stored Values
  protected readStoredData(storedDataString: string): ReadStoredData<T> {
    const storedData: StoredData = JSON.parse(storedDataString);
    const expired = this.isExpiredStoredData(storedData);
    const convertedData = this.buildValueFromData(storedData.data);

    return {
      ...storedData,
      expired,
      convertedData
    };
  }

  protected buildStoredData(value: T): StoredData {
    return {
      storedAt: DateTime.local().toISO(),
      data: this.stringifyValue(value)
    };
  }

  private getWithStoreKey(inputStoreKey: string): Observable<T> {
    return of(inputStoreKey).pipe(
      map((storedKey) => {
        return this.getWithStoreKeyNow(storedKey);
      })
    );
  }

  private getWithStoreKeyNow(storeKey: string): T {
    const storedData: string | null = this.storageObject.getItem(storeKey);

    if (storedData) {
      const readStoredData = this.readStoredData(storedData);

      if (!readStoredData.expired) {
        return readStoredData.convertedData;
      } else {
        throw new DataIsExpiredError<T>(readStoredData);
      }
    } else {
      throw new DataDoesNotExistError();
    }
  }

  private setWithStoreKeyNow(storeKey: string, value: T) {
    const storeData: StoredData = this.buildStoredData(value);
    const data = JSON.stringify(storeData);

    this.storageObject.setItem(storeKey, data);

    return {
      key: storeKey,
      data
    };
  }

  private removeWithStoreKey(storeKey: string): Observable<{}> {
    return of(storeKey).pipe(
      map((key) => {
        return this.removeWithStoreKeyNow(key);
      })
    );
  }

  private removeWithStoreKeyNow(storeKey: string) {
    return this.storageObject.removeItem(storeKey);
  }

}

/**
 * StorageObject using LocalStorage.
 */
export class FullLocalStorageObject implements FullStorageObject {

  constructor(private _localStorage: StorageObject) { }

  get isPersistant() {
    return true;
  }

  get isAvailable() {
    const test = '_T_E_S_T_';

    try {
      this._localStorage.setItem(test, test);
      this._localStorage.removeItem(test);
      return true;
    } catch (e) {
      return false;
    }
  }

  get length(): number {
    return this._localStorage.length;
  }

  getItem(key: string): string | undefined {
    return this._localStorage.getItem(key);
  }

  setItem(key: string, item: string) {
    this._localStorage.setItem(key, item);
  }

  removeItem(key: string) {
    this._localStorage.removeItem(key);
  }

  key(index: number): string | undefined {
    return this._localStorage.key(index);
  }

  removeAll(): string[] {
    const length = this.length;
    let keys = [];

    if (length > 0) {
      keys = ValueUtility.range(0, length).map((x) => this.key(x));
      keys.forEach(x => this.removeItem(x));
    }

    return keys;
  }

}

export class MemoryStorageObject extends FullLocalStorageObject {

  get isLastingStorage() {
    return false;
  }

  get isAvailable() {
    return true;
  }

  constructor(memoryStorage: StorageObject = localStorageMemory) {
    super(memoryStorage);
  }

}

/**
 * LimitedStorageAccessor implementation that wraps a FullStorageObject.
 */
export class StorageObjectLimitedStorageAccessor implements LimitedStorageAccessor<string> {

  constructor(private readonly _storage: FullStorageObject) { }

  get(key: string): Observable<string> {
    return new Observable((x) => {
      const value = this._storage.getItem(key);
      x.next(value);
      x.complete();
    });
  }

  set(key: string, value: string): Observable<{}> {
    return new Observable((x) => {
      const result = this._storage.setItem(key, value);
      x.next(result);
      x.complete();
    });
  }

  remove(key: string): Observable<{}> {
    return new Observable((x) => {
      const removed = this._storage.removeItem(key);
      x.next(removed);
      x.complete();
    });
  }

  clear(): Observable<{}> {
    return new Observable((x) => {
      const removed = this._storage.removeAll();
      x.next(removed);
      x.complete();
    });
  }

}
