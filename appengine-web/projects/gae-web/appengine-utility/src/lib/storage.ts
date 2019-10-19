import { Observable, of, range } from 'rxjs';
import { map, flatMap, filter } from 'rxjs/operators';

import { BaseError } from 'make-error';
import { DateTime } from 'luxon';
import * as localStorageMemory from 'localstorage-memory';

/**
 * Stored object accessor that can get/set objects for the input key.
 */
export abstract class StorageAccessor<T> {

  abstract get(key: string): Observable<T>;

  abstract set(key: string, value: T): Observable<{}>;

  abstract remove(key: string): Observable<{}>;

  abstract all(): Observable<T>;

  abstract allKeys(): Observable<string>;

  abstract clear(): Observable<{}>;

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

export interface StoredData {
  storedAt: string | undefined;
  data: string;
}

export interface ReadStoredData<T> extends StoredData {
  expired: boolean;
  convertedData: T;
}

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
