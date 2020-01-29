import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { ApiModuleRouteConfiguration } from '../api.config';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { EncodedToken, LoginTokenPair, LoginTokenPairJson, NoLoginSetError } from '@gae-web/appengine-token';
import { AuthUtility } from './auth.utility';
import { map, share, shareReplay, catchError } from 'rxjs/operators';
import { LoginRegistrationError, LoginExistsRegistrationError } from './error';
import { ApiResponse, ApiResponseJson } from '../api';
import { ApiJwtConfigurationError } from '../error';


/**
 * Service for interacting with an Appengine Token Register service.
 */
export class RegisterApiService {

  public static SERVICE_PATH = '/login/auth/register';

  private _servicePath: string;

  constructor(private _httpClient: HttpClient, private _config: ApiModuleRouteConfiguration) {
    this._servicePath = this._config.root + RegisterApiService.SERVICE_PATH;
  }

  // MARK: Service
  public register(encodedToken: EncodedToken): Observable<LoginTokenPair> {
    const url: string = this._servicePath;
    const headers = AuthUtility.buildHeaderWithAuthentication(encodedToken);

    const obs = this._httpClient.post<LoginTokenPairJson>(url, undefined, {
      observe: 'response',
      headers
    });

    return this.handleResponseObs(obs);
  }

  private handleResponseObs(responseObs: Observable<HttpResponse<LoginTokenPairJson>>) {
    return responseObs.pipe(
      map(LoginTokenPair.fromResponse),
      catchError(this.handleRequestError),
      shareReplay(1)
    );
  }

  private handleRequestError(error: NoLoginSetError | HttpResponse<ApiResponseJson>, caught: Observable<LoginTokenPair>): Observable<LoginTokenPair> {

    if ((error instanceof NoLoginSetError)) {
      throw new ApiJwtConfigurationError(`The OAuthLoginApiService's routes are not blacklisted.`);
    } else if (error instanceof HttpResponse) {
      let registrationError;

      if (error.body) {
        const apiResponse = ApiResponse.fromJson(error.body);
        const apiError = apiResponse.errors.errors[0];

        switch (apiError.code) {
          case LoginExistsRegistrationError.CODE:
            registrationError = new LoginExistsRegistrationError(apiError.detail);
            break;
          default:
            break;
        }
      }

      if (!registrationError) {
        registrationError = new LoginRegistrationError('An error occured while registering.');
      }

      return throwError(registrationError);
    } else {
      return throwError(error);
    }
  }

}
