import { Injectable, Inject } from '@angular/core';

import { GooglePlatformApiService, PRELOAD_GOOGLE_TOKEN } from './google.service';
import { OAuthLoginServiceTokenResponse, OAuthLoginService } from '../shared/oauth.service';
import { Observable, from } from 'rxjs';
import { map } from 'rxjs/operators';
import { OAuthFailedError, OAuthPopupClosedError, OAuthCancelledError } from '../shared/error';

export interface GoogleAuth2SignInAccessor {
  get(): boolean;
}

export interface GoogleAuth2GoogleUserAccessor {
  get(): GoogleUser;
}

export interface GoogleAuth2Service {
  isSignedIn: GoogleAuth2SignInAccessor;
  currentUser: GoogleAuth2GoogleUserAccessor;
  signIn(): Promise<any>;
}

export interface WrappedGoogleAuth2Service {
  service: GoogleAuth2Service;
}

// https://developers.google.com/identity/sign-in/web/reference
export interface GoogleUser {
  getAuthResponse(): GoogleAuthResponse;
  isSignedIn(): boolean;
}

export interface GoogleAuthResponse {
  access_token: string;
  id_token: string;
  expires_in: string;
  expires_at: string;
}

export class GoogleOAuthServiceConfig {
  scope = 'email';
  cookies = false;
  constructor(public clientId: string) { }
}

@Injectable()
export class GoogleOAuthService extends OAuthLoginService {

  public static SERVICE_NAME = 'google';

  private static AUTH_API_NAME = 'auth2';

  private _listener: GoogleOAuthLoginListener;

  constructor(private _platformService: GooglePlatformApiService, private _config: GoogleOAuthServiceConfig, @Inject(PRELOAD_GOOGLE_TOKEN) preload: boolean = true) {
    super(GoogleOAuthService.SERVICE_NAME);

    if (preload) {
      this.getListener(); // Get the listener immediately
    }
  }

  // MARK: OAuthSignInService
  public tokenLogin(): Observable<OAuthLoginServiceTokenResponse> {
    let promise = this.syncLogin();

    if (!promise) {
      promise = this.login();
    }

    return from(promise).pipe(map((x) => this.convertResponse(x)));
  }

  public convertResponse(response: GoogleAuthResponse): OAuthLoginServiceTokenResponse {
    const tokenResponse: OAuthLoginServiceTokenResponse = {
      service: GoogleOAuthService.SERVICE_NAME,
      token: {
        accessToken: response.access_token
      }
    };

    return tokenResponse;
  }

  // MARK: Service
  public syncLogin(): Promise<GoogleAuthResponse> {
    if (this._listener) {
      return this._listener.login();
    } else {
      return this.login();
    }
  }

  public login(): Promise<GoogleAuthResponse> {
    return this.getListener().then((listener) => {
      return listener.login();
    });
  }

  private getListener(): Promise<GoogleOAuthLoginListener> {
    if (this._listener) {
      return Promise.resolve(this._listener);
    } else {
      return this.getAuthService().then((wrappedAuthService) => {
        this._listener = new GoogleOAuthLoginListener(wrappedAuthService.service);
        return this._listener;
      });
    }
  }

  private getAuthService(): Promise<WrappedGoogleAuth2Service> {
    const config: GoogleOAuthServiceConfig = this._config;

    return this._platformService.load(GoogleOAuthService.AUTH_API_NAME).then((auth2: any) => {
      return new Promise<WrappedGoogleAuth2Service>((resolve, reject) => {
        auth2.init(config).then((result) => {
          // Wrap the service to prevent promise loop. (auth2 has function 'then')
          resolve({ service: auth2.getAuthInstance() });
        }, reject);
      });
    });
  }

}

export class GoogleOAuthLoginListener {

  constructor(private _service: GoogleAuth2Service) { }

  public login(): Promise<GoogleAuthResponse> {
    if (this._service.isSignedIn.get()) {
      const currentUser = this._service.currentUser.get();

      if (currentUser) {
        return this.handleLoginPromise(Promise.resolve(currentUser));
      }
    }

    return this.handleLoginPromise(this._service.signIn());
  }

  handleLoginPromise(loginPromise: Promise<GoogleUser>): Promise<GoogleAuthResponse> {
    return loginPromise.then((user: GoogleUser) => {
      return Promise.resolve(user.getAuthResponse());
    }, (rejection) => {
      const errorCode: string = rejection.error;
      let error: Error;

      switch (errorCode) {
        case 'popup_closed_by_user':
          error = new OAuthPopupClosedError();
          break;
        case 'access_denied':
          error = new OAuthFailedError('The login request was denied.');
          break;
        case 'immediate_failed':
          error = new OAuthFailedError();
          break;
        default:
          error = new OAuthCancelledError();
          break;
      }

      return Promise.reject(error);
    });
  }

}
