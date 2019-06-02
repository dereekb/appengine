import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiRouteConfiguration } from '../api.config';
import { HttpClient } from '@angular/common/http';
import { EncodedToken, LoginTokenPair, LoginTokenPairJson } from '@gae-web/appengine-token';
import { AuthUtility } from './auth.utility';
import { map, share, shareReplay } from 'rxjs/operators';


/**
 * Service for interacting with an Appengine Token Register service.
 */
@Injectable()
export class RegisterApiService {

  public static SERVICE_PATH = '/login/auth/register';

  private _servicePath: string;

  constructor(private _httpClient: HttpClient, private _config: ApiRouteConfiguration) {
    this._servicePath = this._config.root + RegisterApiService.SERVICE_PATH;
  }

  // MARK: Service
  public register(encodedToken: EncodedToken): Observable<LoginTokenPair> {
    const url: string = this._servicePath;
    const headers = AuthUtility.buildHeaderWithAuthentication(encodedToken);

    // TODO: Handle Errors

    return this._httpClient.post<LoginTokenPairJson>(url, undefined, {
      observe: 'response',
      headers
    }).pipe(
      map(LoginTokenPair.fromResponse),
      shareReplay()
    );

  }

}
