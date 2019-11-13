import { Injectable, Inject } from '@angular/core';

import { TokenType, EncodedRefreshToken, DecodedLoginIncludedToken, LoginId, LoginPointerId, EncodedToken, LoginTokenPair } from './token';
import { ExpiredTokenAuthorizationError, TokenAuthorizationError, UnavailableLoginTokenError, TokenExpiredError, TokenLoginError, TokenLoginCommunicationError } from './error';

import { FullStorageObject, MemoryStorageObject, StorageObject } from '@gae-web/appengine-utility';
import { AppTokenStorageService, StoredTokenUnavailableError, AsyncAppTokenStorageService } from './storage.service';

import { Observable, BehaviorSubject, of, throwError, empty, forkJoin, from, concat, EMPTY, merge, interval } from 'rxjs';
import { map, catchError, filter, flatMap, first, toArray, throwIfEmpty, share, tap, finalize, shareReplay, distinctUntilChanged, timeoutWith, throttleTime } from 'rxjs/operators';
import { InvalidLoginTokenError } from './error';
import { BaseError } from 'make-error';
import { DateTime } from 'luxon';

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
  abstract getLoginToken(): Observable<LoginTokenPair>;

  abstract login(fullToken: LoginTokenPair, selector?: AppTokenKeySelector): Observable<LoginTokenPair>;
  abstract logout(): Observable<boolean>;

  // TODO: Add DecodedLoginToken type and allow returning it.

}

export type AppTokenKeySelector = string | undefined;

/**
 * Internal cache used by an AppTokenUserService instance.
 */
export class AppTokenUserServicePair {
  public selector: AppTokenKeySelector;
  public token?: LoginTokenPair;
  public refreshToken?: LoginTokenPair;
}

/**
 * Shiny new UserLoginTokenService implementation.
 */
@Injectable()
export class AsyncAppTokenUserService implements UserLoginTokenService {

  private _selector = new BehaviorSubject<AppTokenKeySelector>(undefined);
  private readonly _pair: Observable<AppTokenUserServicePair>;

  constructor(private _storage: AsyncAppTokenStorageService, private _tokenService: UserLoginTokenAuthenticator) {
    this._pair = this._makePairPipe();
  }

  // MARK: UserLoginTokenService
  public isAuthenticated(): Observable<boolean> {
    return this.getLoginToken().pipe(
      map((x) => Boolean(x)),
      catchError((e) => {
        if ((e instanceof NoLoginSetError) === false) {
          console.error('Error while checking authentication: ' + e);
        }

        return of(false);
      })
    );
  }

  public getEncodedLoginToken(): Observable<EncodedToken | undefined> {
    return this.getLoginToken().pipe(
      map(x => (x) ? x.token : undefined)
    );
  }

  public getLoginToken(): Observable<LoginTokenPair | undefined> {
    return this.servicePairObs.pipe(
      map(x => (x) ? x.token : undefined)
    );
  }

  // MARK: Login / Logout
  /**
   * Equivalent to setLoginToken.
   */
  public login(fullToken: LoginTokenPair, selector?: AppTokenKeySelector): Observable<LoginTokenPair> {
    return this.setLoginToken(fullToken, selector);
  }

  public logout(): Observable<boolean> {
    return this._clearAllTokens().pipe(
      flatMap((_) => {
        // Watch for the token to no longer be set.
        return this.servicePairObs.pipe(
          filter(x => !x.token)
        );
      }),
      map(_ => true)
    );
  }

  // MARK: Accessors
  public getLoginId(): Observable<string | undefined> {
    return this.getLoginToken().pipe(
      map((token) => token.decode().login),
      map((login) => (login) ? String(login) : undefined)
    );
  }

  public getLoginTokenStream(): Observable<LoginTokenPair> {
    return this.getRawLoginTokenStream().pipe(
      filter((x) => Boolean(x))
    );
  }

  public getRawLoginTokenStream(): Observable<LoginTokenPair | undefined> {
    return this.servicePairObs.pipe(
      map((x) => ((x) ? x.token : undefined))
    );
  }

  public get servicePairObs(): Observable<AppTokenUserServicePair> {
    return this._pair;
  }

  // MARK: Key Changes
  get selector() {
    return this._selector.value;
  }

  set selector(selector) {
    this._selector.next(selector);
  }

  // MARK: Login Token
  /**
   * Sets the login token and the selector.
   */
  public setLoginToken(fullToken: LoginTokenPair, selector: AppTokenKeySelector = this.selector): Observable<LoginTokenPair> {
    return this.updateLoginToken(fullToken, selector).pipe(
      flatMap((_) => {

        // Set the new selector.
        this.selector = selector;

        // Watch for the selector to come through the pipe with the token.
        return this.servicePairObs.pipe(
          filter(x => x.selector === selector && Boolean(x.token)),
          map(x => x.token)
        );
      })
    );
  }

  /**
   * Only updates the stored value for the selector.
   *
   * Typically won't be called directly.
   */
  public updateLoginToken(fullToken: LoginTokenPair, selector: AppTokenKeySelector = this.selector): Observable<LoginTokenPair> {
    return this._tokenService.createRefreshToken(fullToken.token).pipe(
      flatMap((refreshToken) => {
        return this._storeRefreshToken(refreshToken, selector).pipe(
          map(_ => refreshToken)
        );
      })
    );
  }

  // MARK: Internal
  private _storeRefreshToken(refreshToken: LoginTokenPair, selector?: AppTokenKeySelector): Observable<{}> {
    return this._storage.setToken(refreshToken, selector);
  }

  private _makePairPipe(): Observable<AppTokenUserServicePair> {
    const obs: Observable<AppTokenUserServicePair> = this._selector.pipe(
      map(x => [x, DateTime.local()] as [string, DateTime]),
      // tap((x) => console.log('Selector updated: "' + x[0] + '" ' + x[1].toISO())),
      // Should not use distinctUntilChanged due to the lack of information here.
      /*
      distinctUntilChanged((a, b) => {
        let allow: boolean;

        if (a[0] === b[0]) {
          // Throttle for 1 second if the same.
          const SECONDS_TO_THROTTLE = 1;
          allow = Math.abs(a[1].diff(b[1], 'seconds').seconds) > SECONDS_TO_THROTTLE;
        } else {
          allow = true;
        }

        return !allow;  // distinctUntilChanged uses equality.
      }),
      */
      flatMap((x) => {
        const selector = x[0];
        return this._loadAppTokenUserServicePairForSelector(selector).pipe(
          // On errors, return a service pair that has just the selector.
          catchError(_ => of({
            selector
          }))
        );
      })
    );

    return obs.pipe(
      distinctUntilChanged((a, b) => {
        // Don't propogate until the pair changes in some way.
        if (a && b) {
          return a.selector === b.selector && a.token === b.token && a.refreshToken === b.refreshToken;
        } else {
          return !a && !b; // Not the same.
        }
      }),
      // Subsequent calls should not hit the chain again.
      shareReplay(1),

      // TODO: Fix issue where the token can expire. If the token is close to expiring, or is expired when called, then refresh it before returning.

      // tap((x) => console.log(`Pipe called > S: ${ x.selector} T: ${ x.token }`)),
    );
  }

  private _loadAppTokenUserServicePairForSelector(selector: AppTokenKeySelector): Observable<AppTokenUserServicePair | undefined> {
    // Load the refresh token
    return this._loadRefreshToken(selector).pipe(
      flatMap((refreshToken: LoginTokenPair) => {
        // Load the full token
        return this._loadFullToken(selector, refreshToken).pipe(
          // Map to a pair
          map(token => ({
            token, refreshToken
          }) as AppTokenUserServicePair)
        );
      }),
      // Catch Refresh Token Issues then throw error.
      catchError((e) => {
        if (e instanceof TokenLoginError) {
          return this._clearTokensWithSelectorFromStorage(selector).pipe(
            catchError(_ => of(undefined)),
            flatMap(_ => throwError(e))
          );
        } else {
          return throwError(e);
        }
      })
    );
  }

  private _loadRefreshToken(selector: AppTokenKeySelector): Observable<LoginTokenPair> {
    return this._loadTokenFromStorage(selector, TokenType.Refresh);
  }

  private _loadFullToken(selector: AppTokenKeySelector, refreshToken?: LoginTokenPair): Observable<LoginTokenPair> {
    // Check Storage First
    return this._loadTokenFromStorage(selector, TokenType.Full).pipe(
      catchError((e) => {
        // If it cannot be loaded due to expiration or unavailability, refresh if the refresh token is available in scope.
        if (refreshToken && (e instanceof TokenExpiredError || e instanceof UnavailableLoginTokenError)) {
          return this._refreshFullToken(selector, refreshToken);
        }

        return throwError(new UnavailableLoginTokenError());
      })
    );
  }

  private _refreshFullToken(selector: AppTokenKeySelector, refreshToken: LoginTokenPair): Observable<LoginTokenPair> {
    return this._tokenService.loginWithRefreshToken(refreshToken.token).pipe(
      // Catch Login Errors
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
      }),
      // Try to Store In Storage
      flatMap((fullToken: LoginTokenPair) => {
        return this._storeFullToken(fullToken, selector).pipe(
          map(_ => fullToken),
          catchError(_ => of(fullToken))
        );
      })
    );
  }

  private _loadTokenFromStorage(selector: AppTokenKeySelector, type: TokenType) {
    return this._storage.getToken(selector, type).pipe(
      // Catch Read Errors
      catchError((e) => {
        if (e instanceof StoredTokenUnavailableError) {
          return throwError(new UnavailableLoginTokenError());
        } else {
          return throwError(e); // Throw other exceptions.
        }
      }),
      // Throw error if expired
      map((x) => {
        if (x.isExpired) {
          throw new TokenExpiredError(x);
        } else {
          return x;
        }
      })
    );
  }

  private _storeFullToken(refreshToken: LoginTokenPair, selector: AppTokenKeySelector): Observable<any> {
    return this._storage.setToken(refreshToken, selector);
  }

  private _clearTokensWithSelectorFromStorage(selector: AppTokenKeySelector, type?: TokenType): Observable<{}> {
    if (type === undefined) {
      return this._storage.clearToken(selector, type);
    } else {
      return this._storage.clearAllTokensWithKey(selector).pipe(
        tap(_ => this._triggerSelectorRefresh(selector))  // Refresh since token was probably removed.
      );
    }
  }

  private _clearAllTokens() {
    return this._storage.clearAllTokens().pipe(
      tap(_ => this._triggerSelectorRefresh())  // Refresh since token was probably removed.
    );
  }

  private _triggerSelectorRefresh(triggerSelector?: AppTokenKeySelector) {
    if (this.selector === triggerSelector) {
      // Causes the selector value to be updated and refresh.
      this.selector = this.selector;
    }
  }

}

// MARK: Testing
/**
 * Simple UserLoginTokenService implementation that uses a behavior subject.
 */
export class BasicTokenUserService implements UserLoginTokenService {

  private readonly _token = new BehaviorSubject<LoginTokenPair>(undefined);

  constructor() { }

  isAuthenticated(): Observable<boolean> {
    return this.getLoginToken().pipe(map(x => Boolean(x)));
  }

  getEncodedLoginToken(): Observable<EncodedToken> {
    return this.getLoginToken().pipe(map(x => x.token));
  }

  getLoginToken(): Observable<LoginTokenPair> {
    return this._token.asObservable();
  }

  login(fullToken: LoginTokenPair, selector?: AppTokenKeySelector): Observable<LoginTokenPair> {
    return new Observable((x) => {
      this._token.next(fullToken);
      x.next(fullToken);
      x.complete();
    });
  }

  logout(): Observable<boolean> {
    return new Observable((x) => {
      this._token.next(undefined);
      x.next(true);
      x.complete();
    });
  }

}

// MARK: Legacy
/**
 * Way the service was declared in the past.
 *
 * @deprecated Legacy
 */
@Injectable()
export class LegacyAppTokenUserService implements UserLoginTokenService {

  private refreshTokenLoginObsMap = new Map<string, Observable<LoginTokenPair>>();
  private _pair = new BehaviorSubject<AppTokenUserServicePair | undefined>(undefined);
  private _accessor: AppTokenLoginAccessor;

  constructor(private _storage: AppTokenStorageService,
    private _tokenService: UserLoginTokenAuthenticator,
    @Inject(FullStorageObject) accessorStorageObject: FullStorageObject = new MemoryStorageObject()) {
    this._accessor = new AppTokenLoginAccessor(accessorStorageObject);
  }

  // MARK: Accessors
  public isAuthenticated(): Observable<boolean> {
    return this.getLoginToken().pipe(
      map(() => true),
      catchError((x) => {
        if ((x instanceof NoLoginSetError) === false) {
          console.error('Error while checking authentication.');
        }

        return of(false);
      })
    );
  }

  public getLoginId(): Observable<string | undefined> {
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
  public login(fullToken: LoginTokenPair, selector?: AppTokenKeySelector): Observable<LoginTokenPair> {
    const rememberObs: Observable<any> = this.getAndAddRememberMe(fullToken).pipe(
      catchError(() => of(true))
    );

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
          return EMPTY;
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
    let refreshObs = this.refreshTokenLoginObsMap.get(refreshToken.token);

    if (!refreshObs) {
      refreshObs = this._tokenService.loginWithRefreshToken(refreshToken.token).pipe(
        finalize(() => {
          this.refreshTokenLoginObsMap.delete(refreshToken.token);  // Delete once finished.
        }),
        shareReplay(1)
      );
      this.refreshTokenLoginObsMap.set(refreshToken.token, refreshObs);
    }

    return refreshObs.pipe(
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
    return forkJoin([
      this.removeTokenFromStorage(pair.token),
      this.removeTokenFromStorage(pair.refreshToken)
    ]);
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
 * Accessor used for reading/writing which login to use.
 *
 * @deprecated Legacy
 */
export class AppTokenLoginAccessor {

  private static LOGIN_KEY = 'PRIMARY_LOGIN_ID';

  constructor(private _storage: StorageObject) { }

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

