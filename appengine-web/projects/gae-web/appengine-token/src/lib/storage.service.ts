import { TokenType, DecodedLoginIncludedToken, LoginTokenPair, LoginId, LoginPointerId, EncodedToken } from './token';
import { Observable, throwError, merge } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { DateTime } from 'luxon';

import {
  StorageAccessor, AbstractStorageAccessor, StorageObject, DataDoesNotExistError, ISO8601DateString, DateTimeUtility, MemoryStorageObject, LimitedStorageAccessor,
  WrappedAsyncStorageAccessorDelegate, AsyncStorageAccessor, StoredDataString
} from '@gae-web/appengine-utility';
import { DecodedLoginToken } from '@gae-web/appengine-token/lib/token';

export class StoredTokenUnavailableError extends DataDoesNotExistError {
  constructor(message?: string) { super(message); }
}

/**
 * Interface for a StoredToken's JSON data.
 */
export interface StoredTokenJSON {
  key: string;       // Unique Key
  type: TokenType;   // Token Type
  login: LoginId;     // Login ID
  pointer: LoginPointerId;   // Pointer ID, generally used as the key.
  encoded: EncodedToken;   // Encoded Token
  expires: ISO8601DateString;   // Expiration Time ISO
}

/**
 * Used for storing tokens.
 */
export class StoredToken {

  public key: string;       // Unique Key
  public type: TokenType;   // Token Type
  public login: LoginId;     // Login ID
  public pointer: LoginPointerId;   // Pointer ID, generally used as the key.
  public encoded: EncodedToken;   // Encoded Token
  public expires: DateTime;      // Expiration Time

  public static reviver(key: string, value: any): any {
    return key === '' ? StoredToken.fromJSON(value) : value;
  }

  public static fromJSON(json: StoredTokenJSON | string): StoredToken {
    if (typeof json === 'string') {
      return JSON.parse(json, StoredToken.reviver);
    } else {
      const token = new StoredToken();
      token.copyJsonData(json);
      return token;
    }
  }

  constructor(data?: StoredToken | StoredTokenJSON) {
    if (data) {
      if (data instanceof StoredToken) {
        this.copyData(data);
      } else {
        this.copyJsonData(data);
      }
    }
  }

  get isExpired(): boolean {
    return this.expires < DateTime.local();
  }

  // MARK: Clone
  public clone() {
    const copy: StoredToken = new StoredToken();
    copy.copyData(this);
    return copy;
  }

  public copyData(data: StoredToken) {
    Object.assign(this, data);
  }

  public copyJsonData(data: StoredTokenJSON) {
    Object.assign(this, data, {
      expires: DateTimeUtility.dateTimeFromInput(data.expires)
    });
  }

  // MARK: JSON
  public toJSON(): StoredTokenJSON {
    return Object.assign({}, this, {
      expires: this.expires.toString()
    });
  }

}

abstract class AbstractTokenStorageService {

  // MARK: Token
  protected convertToLoginTokenPair(token: StoredToken): LoginTokenPair {
    return new LoginTokenPair(token.encoded, token.pointer);
  }

  protected convertToStoredToken(token: LoginTokenPair): StoredToken {
    const decoded = token.decode();
    const storedToken = new StoredToken();

    storedToken.type = decoded.type;
    storedToken.pointer = decoded.pointer;
    storedToken.encoded = decoded.encodedToken;
    storedToken.expires = decoded.expiration;
    storedToken.key = this.makeKeyForStoredToken(storedToken);

    if (decoded instanceof DecodedLoginIncludedToken) {
      storedToken.login = decoded.login;
    } else {
      storedToken.login = 0;
    }

    return storedToken;
  }

  protected makeKeyForTokenPair(tokenPair: LoginTokenPair): string {
    const decoded = tokenPair.decode();
    return this.makeKey(decoded.type, decoded.pointer);
  }

  protected makeKeyForStoredToken(token: StoredToken): string {
    return this.makeKey(token.type, token.pointer);
  }

  protected makeKey(type: TokenType, pointer: string): string {
    return type + '_' + pointer; // Key is the type and pointer, meaning naturally over time tokens will be overwritten.
  }

}

// MARK: Async
export class AsyncAppTokenStorageService extends AbstractTokenStorageService {

  private static readonly DEFAULT_PREFIX = 'STORED_TOKEN_';
  private static readonly TOKEN_KEY = 'USER_TOKEN';

  private _storage: LimitedStorageAccessor<StoredToken>;

  constructor(tokenStringStorage: LimitedStorageAccessor<string>) {
    super();
    this._storage = this._makeStorage(tokenStringStorage);
  }

  // MARK: Tokens
  public getToken(key: string = AsyncAppTokenStorageService.TOKEN_KEY, type: TokenType = TokenType.Refresh): Observable<LoginTokenPair> {
    return this._storage.get(this._makeStorageKey(key, type)).pipe(
      catchError((e) => {
        const isMissing = (e instanceof DataDoesNotExistError);
        const error = (isMissing) ? new StoredTokenUnavailableError() : e;
        return throwError(error);
      }),
      map((result: StoredToken) => this.convertToLoginTokenPair(result))
    );
  }

  public setToken(token: LoginTokenPair, key: string = AsyncAppTokenStorageService.TOKEN_KEY): Observable<{}> {
    const type: TokenType = token.decode().type;
    const storedToken: StoredToken = this.convertToStoredToken(token);
    return this._storage.set(this._makeStorageKey(key, type), storedToken);
  }

  public clearAllTokensWithKey(key: string = AsyncAppTokenStorageService.TOKEN_KEY): Observable<{}> {
    return merge(this.clearToken(key, TokenType.Refresh), this.clearToken(key, TokenType.Full));
  }

  public clearToken(key: string = AsyncAppTokenStorageService.TOKEN_KEY, type: TokenType = TokenType.Refresh): Observable<{}> {
    return this._storage.remove(this._makeStorageKey(key, type));
  }

  public clearAllTokens(): Observable<{}> {
    return this._storage.clear();
  }

  // MARK: Internal
  _makeStorage(tokenStringStorage: LimitedStorageAccessor<string>): LimitedStorageAccessor<StoredToken> {
    const delegate = new WrappedAsyncStorageAccessorDelegate<StoredToken>(tokenStringStorage, {

      stringifyValue(value: StoredToken): StoredDataString {
        return JSON.stringify(value);
      },

      parseValue(data: StoredDataString): StoredToken {
        return StoredToken.fromJSON(data);
      }

    });

    return new AsyncStorageAccessor<StoredToken>(delegate, AsyncAppTokenStorageService.DEFAULT_PREFIX);
  }

  _makeStorageKey(key: string, type: TokenType) {
    return `${key}_${type}`;
  }

}

// MARK: Legacy
/**
 * @deprecated Legacy
 */
export class AppTokenStorageService extends AbstractTokenStorageService {

  constructor(private _storage: StorageTokenAccessor) { super(); }

  public addToken(token: LoginTokenPair): Observable<{}> {
    const storedToken: StoredToken = this.convertToStoredToken(token);
    return this._storage.setToken(storedToken);
  }

  public getTokenWithKey(key: string): Observable<LoginTokenPair> {
    return this._storage.get(key).pipe(
      catchError((e) => {
        const isMissing = (e instanceof DataDoesNotExistError);
        const error = (isMissing) ? new StoredTokenUnavailableError() : e;
        return throwError(error);
      }),
      map((result: StoredToken) => this.convertToLoginTokenPair(result))
    );
  }

  public getTokens(): Observable<LoginTokenPair> {
    return this._storage.all().pipe(
      map((result) => this.convertToLoginTokenPair(result))
    );
  }

  public removeToken(token: LoginTokenPair): Observable<{}> {
    const key = this.makeKeyForTokenPair(token);
    return this.removeTokenWithKey(key);
  }

  public removeTokenWithKey(key: string): Observable<{}> {
    return this._storage.remove(key);
  }

  public clear(): Observable<{}> {
    return this._storage.clear();
  }

}

/**
 * Class/Interface for storing StoredToken values.
 *
 * @deprecated Legacy
 */
export abstract class StorageTokenAccessor extends StorageAccessor<StoredToken> {
  abstract setToken(token: StoredToken, key?: string): Observable<{}>;
}

/**
 * @deprecated Legacy
 */
export class StoredTokenStorageAccessor extends AbstractStorageAccessor<StoredToken> implements StorageTokenAccessor {

  public static readonly DEFAULT_PREFIX = 'TOKEN_';

  constructor(storageObject: StorageObject = new MemoryStorageObject(), prefix: string = StoredTokenStorageAccessor.DEFAULT_PREFIX) {
    super(storageObject, prefix);
  }

  public setToken(token: StoredToken, key?: string): Observable<{}> {
    if (!key) {
      key = token.key;
    }

    return super.set(key, token);
  }

  protected buildValueFromData(data: string): StoredToken {
    return StoredToken.fromJSON(data);
  }

}
