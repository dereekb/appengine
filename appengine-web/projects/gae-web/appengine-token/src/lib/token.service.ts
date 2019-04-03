import { Injectable } from '@angular/core';

import { TokenType, EncodedRefreshToken, DecodedLoginIncludedToken, LoginId, LoginPointerId, EncodedToken, LoginTokenPair } from './token';
import { ExpiredTokenAuthorizationError, TokenAuthorizationError, UnavailableLoginTokenError, TokenExpiredError, TokenLoginError, TokenLoginCommunicationError } from './error';

import { FullStorageObject } from '@gae-web/appengine-utility';
import { AppTokenStorageService, StoredTokenUnavailableError } from './storage.service';

import { Observable, BehaviorSubject, of, throwError, empty, forkJoin } from 'rxjs';
import { map, catchError, filter, flatMap, first, toArray, concat, throwIfEmpty } from 'rxjs/operators';
import { InvalidLoginTokenError } from './error';
import { StorageUtility } from '@gae-web/appengine-utility';
import { BaseError } from 'make-error';

// Service

/**
 * Class Interface used by the UserLoginTokenService to authenticate and rerieve refresh tokens.
 */
export abstract class UserLoginTokenAuthenticator {
  /**
   * Requests a long-term refresh token using the input encoded refresh token.
   * @param encodedToken EncodedRefreshToken
   */
  abstract createRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair>;

  /**
   * Requests an authentication token using the input encoded refresh token.
   * @param encodedToken EncodedRefreshToken
   */
  abstract loginWithRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair>;
}

/**
 * Class Interface used for managing the user token(s) within the application.
 */
export abstract class UserLoginTokenService {

  abstract isAuthenticated(): Observable<boolean>;
  abstract getEncodedLoginToken(): Observable<EncodedToken>;

  abstract login(fullToken: LoginTokenPair, rememberMe: boolean): Observable<LoginTokenPair>;
  abstract logout(): Observable<boolean>;

  // TODO: Add DecodedLoginToken type and allow returning it.

}

/**
 * Way the service was declared in the past.
 * @deprecated
 */
@Injectable()
export class LegacyAppTokenUserService extends UserLoginTokenService {

  private _pair = new BehaviorSubject<AppTokenUserServicePair | undefined>(undefined);
  private _accessor = new AppTokenLoginAccessor();

  constructor(private _storage: AppTokenStorageService, private _tokenService: UserLoginTokenAuthenticator) {
    super();
  }

  // MARK: Accessors
  public isAuthenticated(): Observable<boolean> {
    return this.getLoginToken().pipe(
      catchError((x) => {
        if ((x instanceof NoLoginSetError) === false) {
          console.error('Error while checking authentication.');
        }

        return of(false);
      }),
      map(() => true)
    );
  }

  public getUserId(): Observable<string | undefined> {
    return this.getLoginToken().pipe(
      map((token) => token.decode().login),
      map((login) => (login) ? String(login) : undefined)
    );
  }

  public getEncodedLoginToken(): Observable<EncodedToken> {
    return this.getLoginToken().pipe(
      map((x) => x.token)
    );
  }

  public getLoginToken(): Observable<LoginTokenPair> {
    return this.getOrLoadAppTokenUserServicePair().pipe(
      map((x) => x.token)
    );
  }

  public getLoginTokenStream(): Observable<LoginTokenPair> {
    return this.getRawLoginTokenStream().pipe(
      filter((x) => Boolean(x))
    );
  }

  public getRawLoginTokenStream(): Observable<LoginTokenPair | undefined> {
    return this._pair.pipe(
      map((x) => ((x) ? x.token : undefined))
    );
  }

  // MARK: Login / Logout
  public login(fullToken: LoginTokenPair, rememberMe: boolean): Observable<LoginTokenPair> {
    let rememberObs: Observable<any>;

    if (rememberMe) {
      rememberObs = this.getAndAddRememberMe(fullToken).pipe(
        catchError(() => of(true))
      );
    } else {
      of({});
    }

    return rememberObs.pipe(
      flatMap(() => this.setLoginToken(fullToken))
    );
  }

  public setLoginToken(loginTokenPair: LoginTokenPair): Observable<LoginTokenPair> {
    return this.addLoginToken(loginTokenPair).pipe(
      flatMap(() => this.loadAppTokenUserServicePairForLoginPointer(loginTokenPair.pointer)),
      flatMap((pair: AppTokenUserServicePair) => this.switchToAppTokenUserServicePair(pair)),
      map((x) => x.token)
    );
  }

  public addLoginToken(token: LoginTokenPair): Observable<{}> {
    const decoded = token.decode();

    if (decoded instanceof DecodedLoginIncludedToken) {
      if (decoded.isExpired) {
        return throwError(new InvalidLoginTokenError(token, 'Token is expired.'));
      } else {
        return this.addTokenToStorage(token);
      }
    } else {
      return throwError(new InvalidLoginTokenError(token, 'Token had no login.'));
    }
  }

  public logout(): Observable<boolean> {
    return this._storage.clear().pipe(
      flatMap(() => this._accessor.clearLogin()),
      map((x) => {
        this._pair.next(undefined);
        return x;
      })
    );
  }

  // MARK: Internal
  private getOrLoadAppTokenUserServicePair(): Observable<AppTokenUserServicePair> {
    return this._pair.pipe(
      first(),
      flatMap((servicePair) => {
        let obs: Observable<[AppTokenUserServicePair, boolean]>;

        // Load a login token from the Subject or Last Used Token.
        if (servicePair) {
          obs = of([servicePair, false] as [AppTokenUserServicePair, boolean]);
        } else {
          obs = this.loadStoredTokenServicePair().pipe(
            map((x) => [x, true] as [AppTokenUserServicePair, boolean])
          );
        }

        return obs;
      }),
      flatMap(([pair, loaded]) => {
        return this.getFullTokenAndUpdateStorageRaw(pair).pipe(
          flatMap(([updatedPair, updated]) => {
            if (loaded || updated) {
              return this.setLastLogin(updatedPair).pipe(map(() => updatedPair));
            } else {
              return of(updatedPair);
            }
          })
        );
      })
    );
  }

  private switchToAppTokenUserServicePair(pair: AppTokenUserServicePair): Observable<AppTokenUserServicePair> {
    // Refesh the token if needed.
    return this.getFullTokenAndUpdateStorage(pair).pipe(
      flatMap((x) => {
        return this.setLastLogin(x).pipe(map(() => x));
      })
    );
  }

  private setLastLogin(pair: AppTokenUserServicePair): Observable<boolean> {

    // Store the "last login"
    return this._accessor.setLogin(pair.token.decode().login).pipe(
      map((updated) => {
        // Update the user internally.
        this._pair.next(pair);

        return updated;
      })
    );
  }

  // MARK: Loading
  private loadStoredTokenServicePair(): Observable<AppTokenUserServicePair> {
    return this.loadLastLoginId().pipe(
      flatMap((login) => this.loadAppTokenUserServicePairForLogin(login))
    );
  }

  private loadLastLoginId(): Observable<LoginId> {
    return this._accessor.getLogin();
  }

  private loadAppTokenUserServicePairForLogin(login: LoginId): Observable<AppTokenUserServicePair> {
    return this.loadAppTokenUserServicePairWithFilter((pair: LoginTokenPair) => pair.decode().login === login);
  }

  private loadAppTokenUserServicePairForLoginPointer(pointer: LoginPointerId): Observable<AppTokenUserServicePair> {
    return this.loadAppTokenUserServicePairWithFilter((pair: LoginTokenPair) => pair.pointer === pointer);
  }

  private loadAppTokenUserServicePairWithFilter(pairFilter: (pair: LoginTokenPair) => boolean): Observable<AppTokenUserServicePair> {
    return this.loadStoredTokens().pipe(
      filter(pairFilter),
      toArray(),
      flatMap((pairs) => this.makeTokenServicePair(pairs))
    );
  }

  private makeTokenServicePair(candidates: LoginTokenPair[]): Observable<AppTokenUserServicePair> {
    if (candidates.length === 0) {
      return throwError(new UnavailableLoginTokenError('No candidates specified.'));
    } else {
      const pairs = this.buildPairs(candidates);
      const iter = pairs.values();
      const selected = iter.next().value;

      if (selected) {
        return of(selected);
      } else {
        return throwError(new UnavailableLoginTokenError('No candidate was selectable.'));
      }
    }
  }

  private buildPairs(candidates: LoginTokenPair[]): Map<LoginId, AppTokenUserServicePair> {
    const pairMap: Map<LoginId, AppTokenUserServicePair> = new Map();

    candidates.forEach((candidate) => {
      const decoded = candidate.decode();
      const login: LoginId = decoded.login;
      let pair: AppTokenUserServicePair | undefined = pairMap.get(login);

      if (!pair) {
        pair = new AppTokenUserServicePair();
        pairMap.set(login, pair);
      }

      switch (decoded.type) {
        case TokenType.Refresh:
          pair.refreshToken = candidate;
          break;
        case TokenType.Full:
          pair.token = candidate;
          break;
        default:
          throw new Error('Unexpected token type.');
      }
    });

    return pairMap;
  }

  protected loadNonExpiredTokens(removeExpired: boolean): Observable<LoginTokenPair> {
    return this.loadStoredTokens().pipe(
      map((x) => {
        if (x.isExpired) {
          throw new TokenExpiredError(x);
        } else {
          return x;
        }
      })
    );
  }

  protected loadStoredTokens(): Observable<LoginTokenPair> {
    return this._storage.getTokens().pipe(
      catchError((e) => {
        if (e instanceof StoredTokenUnavailableError) {
          return empty();
        } else {
          return throwError(e); // Throw other exceptions.
        }
      }),
      throwIfEmpty(() => {
        return new UnavailableLoginTokenError('No stored tokens responses.');
      })
    );
  }

  // MARK: Updating
  private getFullTokenAndUpdateStorage(pair: AppTokenUserServicePair): Observable<AppTokenUserServicePair> {
    return this.getFullTokenAndUpdateStorageRaw(pair).pipe(
      map((updated) => {
        return updated[0];
      })
    );
  }

  private getFullTokenAndUpdateStorageRaw(pair: AppTokenUserServicePair): Observable<[AppTokenUserServicePair, boolean]> {
    return this.getFullTokenPair(pair).pipe(
      catchError((e) => {
        if (e instanceof TokenLoginError) {
          // Delete the token locally then throw the error again.
          return concat(
            this.deleteServicePair(pair),
            throwError(() => {
              return e;
            })
          ) as any;
        }

        throw e;
      }),
      flatMap((updated: [AppTokenUserServicePair, boolean]) => {
        const fullToken = updated[0].token;

        if (fullToken) {
          return this.addTokenToStorage(fullToken).pipe(
            map(() => updated)
          );
        } else {
          return of(updated);
        }
      })
    );
  }

  private getFullTokenPair(pair: AppTokenUserServicePair): Observable<[AppTokenUserServicePair, boolean]> {

    // Refresh/Update the pair if it has no main token, or the token is expired.
    if (!pair.token || pair.token.isExpired) {
      const refreshToken = pair.refreshToken;

      if (refreshToken) {
        return this.refreshFullToken(refreshToken).pipe(
          map((fullToken) => {
            const updatedPair = new AppTokenUserServicePair();

            updatedPair.token = fullToken;
            updatedPair.refreshToken = pair.refreshToken;

            const result: [AppTokenUserServicePair, boolean] = [updatedPair, true];
            return result;
          })
        );
      } else {
        throw throwError(new Error('No refresh token is available to refesh the pair.'));
      }
    } else {
      const result: [AppTokenUserServicePair, boolean] = [pair, false];
      return of(result);
    }
  }

  private refreshFullToken(refreshToken: LoginTokenPair): Observable<LoginTokenPair> {
    return this._tokenService.loginWithRefreshToken(refreshToken.token).pipe(
      catchError((e) => {
        if (e instanceof TokenAuthorizationError) {
          if (e instanceof ExpiredTokenAuthorizationError) {
            throw new TokenExpiredError(refreshToken, e.message);
          } else {
            throw new TokenLoginError(refreshToken, e.message);
          }
        } else {
          throw new TokenLoginCommunicationError(e);
        }
      })
    );
  }

  private getAndAddRememberMe(fullToken: LoginTokenPair): Observable<LoginTokenPair> {
    return this._tokenService.createRefreshToken(fullToken.token).pipe(
      flatMap((refreshToken) => {
        return this.addTokenToStorage(refreshToken).pipe(map(() => refreshToken));
      })
    );
  }

  // MARK: Remove
  private deleteServicePair(pair: AppTokenUserServicePair): Observable<{}> {
    return forkJoin(
      this.removeTokenFromStorage(pair.token),
      this.removeTokenFromStorage(pair.refreshToken)
    );
  }

  private removeTokenFromStorage(loginToken: LoginTokenPair): Observable<{}> {
    return this._storage.removeToken(loginToken);
  }

  // MARK: Other
  private addTokenToStorage(token: LoginTokenPair): Observable<{}> {
    return this._storage.addToken(token);
  }

}


/**
 * Internal cache used by an AppTokenUserService instance.
 */
export class AppTokenUserServicePair {
  public token: LoginTokenPair;
  public refreshToken: LoginTokenPair;
}

/**
 * Accessor used for reading/writing which login to use.
 */
export class AppTokenLoginAccessor {

  private static LOGIN_KEY = 'PRIMARY_LOGIN_ID';

  constructor(private _storage: FullStorageObject = StorageUtility.getAvailableLocalStorage()) { }

  public hasLogin(): Observable<boolean> {
    return this.getLogin().pipe(
      map(() => true),
      catchError(() => of(false))
    );
  }

  public tryGetLogin(): Observable<LoginId | null> {
    return this.getLogin().pipe(
      catchError(() => of(null))
    );
  }

  public getLogin(): Observable<LoginId> {
    return of(0).pipe(
      map(() => {
        const loginIdString: string | null = this._storage.getItem(AppTokenLoginAccessor.LOGIN_KEY);

        if (!loginIdString) {
          throw new NoLoginSetError();
        } else {
          return Number(loginIdString);
        }
      })
    );
  }

  public clearLogin(): Observable<boolean> {
    return this.setLogin(null).pipe(map((x) => !x));
  }

  public setLogin(inputLogin: LoginId | null): Observable<boolean> {
    return of(inputLogin).pipe(
      map((login) => {
        if (login) {
          this._storage.setItem(AppTokenLoginAccessor.LOGIN_KEY, login.toString());
          return true;
        } else {
          this._storage.removeItem(AppTokenLoginAccessor.LOGIN_KEY);
          return false;
        }
      })
    );
  }

}

export class NoLoginSetError extends BaseError {

  constructor(message: string = 'No login is set.') {
    super(message);
  }

}

