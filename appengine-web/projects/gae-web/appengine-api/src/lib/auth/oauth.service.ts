
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AppengineApiRouteConfiguration } from '../api.config';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { share, catchError, map } from 'rxjs/operators';
import { LoginTokenPair, LoginTokenPairJson } from '@gae-web/appengine-token';

/**
 * Service for interacting with an Appengine OAuth Token service.
 */
@Injectable()
export class OAuthLoginService {

  public static SERVICE_PATH = '/login/auth/oauth';

  private _servicePath: string;

  constructor(private _httpClient: HttpClient, private _config: AppengineApiRouteConfiguration) {
    this._servicePath = this._config.root + OAuthLoginService.SERVICE_PATH;
  }

  // MARK: Service
  public loginWithAuthCode(type: string, authCode: string, codeType: string = 'default'): Observable<LoginTokenPair> {
    const url = this._servicePath + '/' + type + '/code';

    const params = {
      code: authCode,
      type: codeType
    };

    const response = this._httpClient.post<LoginTokenPairJson>(url, undefined, {
      observe: 'response',
      params
    });

    return this.handleResponseObs(response);
  }

  public loginWithAccessToken(type: string, accessToken: string): Observable<LoginTokenPair> {
    if (accessToken.length === 0) {
      throw new Error('Access token was empty.');
    }

    const url = this._servicePath + '/' + type + '/token';

    const params = {
      token: accessToken
    };

    const response = this._httpClient.post<LoginTokenPairJson>(url, undefined, {
      observe: 'response',
      params
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

    // TODO: Update error to be more specific?

    return Observable.throw(new Error('An error occured while accessing the Login Server.'));
  }

}
