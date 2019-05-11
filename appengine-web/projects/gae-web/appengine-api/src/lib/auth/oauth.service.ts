
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { ApiRouteConfiguration } from '../api.config';
import { HttpClient, HttpResponse, HttpHeaders, HttpParams, HttpBackend } from '@angular/common/http';
import { share, catchError, map } from 'rxjs/operators';
import { LoginTokenPair, LoginTokenPairJson, NoLoginSetError } from '@gae-web/appengine-token';
import { ApiJwtConfigurationError } from '../error';

/**
 * Service for interacting with an Appengine OAuth Token service.
 */
@Injectable()
export class OAuthLoginApiService {

  public static SERVICE_PATH = '/login/auth/oauth';

  private _servicePath: string;
  private _httpClient: HttpClient;

  constructor(httpBackend: HttpBackend, private _config: ApiRouteConfiguration) {
    this._servicePath = this._config.root + OAuthLoginApiService.SERVICE_PATH;
    this._httpClient = new HttpClient(httpBackend);
  }

  // MARK: Service
  public loginWithAuthCode(type: string, authCode: string, codeType: string = 'default'): Observable<LoginTokenPair> {
    const url = this._servicePath + '/' + type + '/code';

    const body = new HttpParams()
      .set('code', authCode)
      .set('type', codeType);

    const response = this._httpClient.post<LoginTokenPairJson>(url, body.toString(), {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      }),
      observe: 'response'
    });

    return this.handleResponseObs(response);
  }

  public loginWithAccessToken(type: string, accessToken: string): Observable<LoginTokenPair> {
    if (accessToken.length === 0) {
      throw new Error('Access token was empty.');
    }

    const url = this._servicePath + '/' + type + '/token';

    const body = new HttpParams()
      .set('token', accessToken);

    const response = this._httpClient.post<LoginTokenPairJson>(url, body.toString(), {
      headers: new HttpHeaders({
        'Content-Type': 'application/x-www-form-urlencoded'
      }),
      observe: 'response'
    });

    return this.handleResponseObs(response);
  }

  private handleResponseObs(responseObs: Observable<HttpResponse<LoginTokenPairJson>>) {
    return responseObs.pipe(
      map(LoginTokenPair.fromResponse),
      catchError(this.handleRequestError),
      share()
    );
  }

  private handleRequestError(err: any, caught: Observable<LoginTokenPair>): Observable<LoginTokenPair> {

    if ((err instanceof NoLoginSetError)) {
      throw new ApiJwtConfigurationError(`The OAuthLoginApiService's routes are not blacklisted.`);
    }

    console.error(err);

    return throwError(new Error('An error occured while accessing the Login Server.'));
  }

}
