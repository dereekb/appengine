import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map, share } from 'rxjs/operators';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { ApiRouteConfiguration } from '../api.config';
import {
  LoginTokenPairJson, EncodedToken, LoginTokenPair, ExpiredTokenAuthorizationError,
  InvalidTokenAuthorizationError, UserLoginTokenAuthenticator, EncodedRefreshToken
} from '@gae-web/appengine-token';
import { ApiResponseJson, ApiResponse } from '../api';
import { TokenAuthenticationError } from './error';
import { AuthUtility } from './auth.utility';

export const LOGIN_TOKEN_SERVICE_PATH = '/login/auth/token';
export const CREATE_REFRESH_TOKEN_PATH = `${LOGIN_TOKEN_SERVICE_PATH}/refresh`;
export const LOGIN_WITH_REFRESH_TOKEN_PATH = `${LOGIN_TOKEN_SERVICE_PATH}/login`;

/**
 * UserLoginTokenAuthenticator implementation.
 */
export class ApiUserLoginTokenAuthenticator implements UserLoginTokenAuthenticator {

  constructor(private _publicLoginTokenApiService: PublicLoginTokenApiService) { }

  createRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair> {
    return this._publicLoginTokenApiService.createRefreshToken(encodedToken);
  }

  loginWithRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair> {
    return this._publicLoginTokenApiService.loginWithRefreshToken(encodedToken);
  }

}

/**
 * Service for interacting with an Appengine LoginToken service.
 */
@Injectable()
export class PublicLoginTokenApiService {

  private _servicePath: string;

  constructor(private _httpClient: HttpClient, private _config: ApiRouteConfiguration) {
    this._servicePath = this._config.root + LOGIN_TOKEN_SERVICE_PATH;
  }

  // MARK: Secured Requests
  public createRefreshToken(encodedToken: EncodedToken): Observable<LoginTokenPair> {
    const url = this._servicePath + '/refresh';
    const headers = AuthUtility.buildHeaderWithAuthentication(encodedToken);

    // TODO: Update error handling.

    return this._httpClient.get<LoginTokenPairJson>(url, {
      observe: 'response',
      headers
    }).pipe(
      map(LoginTokenPair.fromResponse)
    );
  }

  // MARK: Authentication
  public loginWithRefreshToken(encodedToken: EncodedToken): Observable<LoginTokenPair> {
    const url = this._servicePath + '/login';

    const params = {
      refreshToken: encodedToken
    };

    return this._httpClient.post<LoginTokenPairJson | ApiResponseJson>(url, undefined, {
      params
    }).pipe(
      catchError(this.handleErrorResponse),
      map((x) => LoginTokenPair.fromJson(x as LoginTokenPairJson)),
      share()
    );
  }

  private handleErrorResponse(x: HttpResponse<LoginTokenPairJson | ApiResponseJson>): Observable<HttpResponse<ApiResponseJson>> {
    const json = x.body as ApiResponseJson;

    if (json) {
      const apiResponse = ApiResponse.fromJson(json);
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
export class PrivateLoginTokenApiService {

  private _servicePath: string;

  constructor(private _httpClient: HttpClient, private _config: ApiRouteConfiguration) {
    this._servicePath = this._config.root + LOGIN_TOKEN_SERVICE_PATH;
  }

  // MARK: Refresh Tokens
  public createRefreshToken(): Observable<LoginTokenPair> {
    const url = this._servicePath + '/refresh';

    return this._httpClient.get<LoginTokenPairJson>(url, {
      observe: 'response'
    }).pipe(
      map(LoginTokenPair.fromResponse),
      share()
    );
  }

}
