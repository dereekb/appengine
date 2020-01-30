import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError, map, share } from 'rxjs/operators';
import { HttpClient, HttpResponse, HttpParams, HttpHeaders, HttpBackend } from '@angular/common/http';
import { ApiModuleRouteConfiguration } from '../api.config';
import {
  LoginTokenPairJson, EncodedToken, LoginTokenPair, ExpiredTokenAuthorizationError,
  InvalidTokenAuthorizationError, UserLoginTokenAuthenticator, EncodedRefreshToken
} from '@gae-web/appengine-token';
import { ApiResponseJson, ApiResponse } from '../api';
import { TokenAuthenticationError } from './error';

export interface LoginTokenLoginOptions {
  noRoles?: boolean;
  rolesMask?: number;
}

/**
 * UserLoginTokenAuthenticator implementation.
 */
export class ApiUserLoginTokenAuthenticator implements UserLoginTokenAuthenticator {

  constructor(private _publicLoginTokenApiService: PublicLoginTokenApiService) { }

  createRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair> {
    return this._publicLoginTokenApiService.createRefreshToken(encodedToken);
  }

  loginWithRefreshToken(encodedToken: EncodedRefreshToken, options?: LoginTokenLoginOptions): Observable<LoginTokenPair> {
    return this._publicLoginTokenApiService.loginWithRefreshToken(encodedToken, options);
  }

}

/**
 * Service for interacting with an Appengine LoginToken service.
 */
export class PublicLoginTokenApiService {

  public static readonly SERVICE_PATH: string = '/login/auth/token';

  private _servicePath: string;
  private _httpClient: HttpClient;

  constructor(httpBackend: HttpBackend, private _config: ApiModuleRouteConfiguration) {
    this._servicePath = this._config.root + PublicLoginTokenApiService.SERVICE_PATH;
    this._httpClient = new HttpClient(httpBackend);
  }

  // MARK: Secured Requests
  public createRefreshToken(encodedToken: EncodedToken): Observable<LoginTokenPair> {
    const url = this._servicePath + '/refresh';

    // TODO: Update error handling.
    const body = new HttpParams()
      .set('accessToken', encodedToken);

    return this._httpClient.post<LoginTokenPairJson>(url, body, {
      observe: 'response',
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      })
    }).pipe(
      catchError(this.handleErrorResponse),
      map(LoginTokenPair.fromResponse)
    );
  }

  // MARK: Authentication
  public loginWithRefreshToken(encodedToken: EncodedToken, options?: LoginTokenLoginOptions): Observable<LoginTokenPair> {
    const url = this._servicePath + '/login';

    let body = new HttpParams()
      .set('refreshToken', encodedToken);

    if (options) {
      let rolesMask = options.rolesMask;

      if (!options.rolesMask && options.noRoles) {
        rolesMask = 0;
      }

      if (rolesMask !== undefined) {
        body = body.set('rolesMask', rolesMask.toString());
      }
    }

    return this._httpClient.post<LoginTokenPairJson | ApiResponseJson>(url, body, {
      observe: 'response',
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      })
    }).pipe(
      catchError(this.handleErrorResponse),
      map(LoginTokenPair.fromResponse)
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
