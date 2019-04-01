import { Injectable } from '@angular/core';
import { FacebookLoginResponse, FacebookLoginStatus, FacebookApi, FacebookApiService } from './facebook.service';
import { OAuthLoginServiceTokenResponse, OAuthLoginService } from '../shared/oauth.service';
import { from, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { OAuthCancelledError } from '../shared/error';

@Injectable()
export class FacebookOAuthService extends OAuthLoginService {

  public static SERVICE_NAME = 'facebook';

  private _status: FacebookLoginResponse = {
    status: 'other'
  };

  constructor(private _apiService: FacebookApiService) {
    super(FacebookOAuthService.SERVICE_NAME);
    this.getLoginStatus().then((status) => {
      this._status = status;
    }, (error) => {
      console.log('Error loading facebook login status...');
    });
  }

  // MARK: OAuthSignInService
  public tokenLogin(): Observable<OAuthLoginServiceTokenResponse> {
    let promise = this.syncLogin();

    if (!promise) {
      promise = this.login();
    }

    return from(promise).pipe(map((x) => this.convertResponse(x)));
  }

  public convertResponse(response: FacebookLoginResponse): OAuthLoginServiceTokenResponse {
    const tokenResponse: OAuthLoginServiceTokenResponse = {
      service: FacebookOAuthService.SERVICE_NAME,
      token: {
        accessToken: response.authResponse.accessToken
      }
    };

    return tokenResponse;
  }

  // MARK: Service
  /**
   * Attempts to login synchronously. This is to try and avoid the facebook login popup blocked.
   */
  public syncLogin(): Promise<FacebookLoginResponse> | undefined {
    const facebookApi: FacebookApi | undefined = this._apiService.syncGetApi();

    if (!facebookApi) {
      return undefined;
    }

    if (this._status.status !== 'connected') {
      return this.doLogin(facebookApi);
    } else {
      return Promise.resolve(this._status);
    }
  }

  public login(): Promise<FacebookLoginResponse> {
    // console.log('Trying facebook login...');
    return this.getLoginStatus().then((value: FacebookLoginResponse) => {
      switch (value.status) {
        case 'connected':
          return Promise.resolve(value);
        default:
          return this.startLogin();
      }
    });
  }

  public getLoginStatusType(): Promise<FacebookLoginStatus> {
    return this.getLoginStatus().then((response) => {
      return response.status;
    });
  }

  public getLoginStatus(): Promise<FacebookLoginResponse> {
    return this._apiService.getApi().then((facebookApi: FacebookApi) => {
      return new Promise<FacebookLoginResponse>((resolve, reject) => {
        facebookApi.getLoginStatus((status) => {
          resolve(status);
        });
      });
    });
  }

  private startLogin(): Promise<FacebookLoginResponse> {
    return this._apiService.getApi().then((facebookApi: FacebookApi) => {
      return this.doLogin(facebookApi);
    });
  }

  private doLogin(facebookApi: FacebookApi): Promise<FacebookLoginResponse> {
    return new Promise<FacebookLoginResponse>((resolve, reject) => {
      facebookApi.login((response: FacebookLoginResponse) => {
        switch (response.status) {
          case 'connected':
            resolve(response);
            break;
          default:
            reject(new OAuthCancelledError());
            break;
        }
      });
    });
  }

}
