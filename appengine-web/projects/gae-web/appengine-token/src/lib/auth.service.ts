import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map, share } from 'rxjs/operators';

import { EncodedToken, LoginTokenPair } from './token';

import { BaseError } from 'make-error';
import { HttpClient } from '@angular/common/http';

const LOGIN_TOKEN_SERVICE_PATH = '/login/auth/token';

export class TokenAuthenticationError extends BaseError {

  constructor(message?: string) {
    super(message);
  }

}

export class ExpiredTokenAuthorizationError extends TokenAuthenticationError {

  public static readonly CODE = 'EXPIRED_AUTHORIZATION';

}

export class InvalidTokenAuthorizationError extends TokenAuthenticationError {

  public static readonly CODE = 'INVALID_AUTHORIZATION';

}

/**
 * Service for interacting with an Appengine LoginToken service.
 */
@Injectable()
export class PublicLoginTokenService {

  private _servicePath: string;

  constructor(private _http: HttpClient, private _config: AppengineApiRouteConfiguration) {
    this._servicePath = _config.root + LOGIN_TOKEN_SERVICE_PATH;
  }

  // MARK: Secured Requests
  public createRefreshToken(encodedToken: EncodedToken): Observable<LoginTokenPair> {
    const url = this._servicePath + '/refresh';
    const authConfig = new AuthConfig({
      tokenGetter: () => encodedToken
    });

    const authHttp: AuthHttp = new AuthHttp(authConfig, this._http);
    return authHttp.get(url).map(LoginTokenPair.fromResponse);
  }

  // MARK: Authentication
  public loginWithRefreshToken(encodedToken: EncodedToken): Observable<LoginTokenPair> {
    console.log('Login');

    const url = this._servicePath + '/login';

    const body = new URLSearchParams();
    body.append('refreshToken', encodedToken);

    return this._http.post(url, body).pipe(
      catchError(this.handleErrorResponse),
      map(LoginTokenPair.fromResponse),
      share()
    );
  }

  private handleErrorResponse(x: Response): Observable<Response> {
    const json = x.json();

    if (json) {
      const apiResponse = ApiResponseImpl.fromJson(json);
      const apiError = apiResponse.errors.errors[0];

      switch (apiError.code) {
        case ExpiredTokenAuthorizationError.CODE:
          throw new ExpiredTokenAuthorizationError();
        case InvalidTokenAuthorizationError.CODE:
          throw new InvalidTokenAuthorizationError();
        default:
          throw new TokenAuthenticationError();
      }

    } else {
      throw new TokenAuthenticationError();
    }
  }

}

@Injectable()
export class PrivateLoginTokenService {

  private _servicePath: string;

  constructor(private _authHttp: AuthHttp, private _config: AppengineApiRouteConfiguration) {
    this._servicePath = _config.root + LOGIN_TOKEN_SERVICE_PATH;
  }

  // MARK: Refresh Tokens
  public createRefreshToken(): Observable<LoginTokenPair> {
    const url = this._servicePath + '/refresh';
    return this._authHttp.get(url).map(LoginTokenPair.fromResponse).share();
  }

}
