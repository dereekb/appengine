import { Injectable, Optional, InjectionToken, Inject } from '@angular/core';

import { AbstractAsyncLoadedService } from '../shared/async';

export const PRELOAD_FACEBOOK_TOKEN = new InjectionToken<string>('PreLoadFacebookService');

export type FacebookLoginStatus = 'connected' | 'not_authorized' | 'other';

export interface FaebookAuthResponseData {
  accessToken: string;
  expiresIn: string;
  signedRequest: string;
  userID: string;
}

export interface FacebookLoginResponse {
  status: FacebookLoginStatus;
  authResponse?: FaebookAuthResponseData;
}

// MARK: Analytics
export interface FacebookAnalyticsServiceEventNames {
  readonly ADDED_PAYMENT_INFO: string;
  readonly COMPLETED_REGISTRATION: string;
  readonly RATED: string;
  readonly SEARCHED: string;
  // ...
}

export interface FacebookAnalyticsServiceParameterNames {
  readonly CONTENT_ID: string;
  readonly CONTENT_TYPE: string;
  readonly DESCRIPTION: string;
  readonly SEARCH_STRING: string;
  readonly SUCCESS: string;
  // ...
}

export class FacebookAnalyticsUserPropertyNames {
  static readonly ACCOUNT_CREATE_TIME = '$account_created_time';
  static readonly INSTALL_SOURCE = '$install_source';
  static readonly USER_TYPE = '$user_type';
  // ...
}

export interface FacebookAnalyticsService {
  readonly EventNames: FacebookAnalyticsServiceEventNames;
  readonly ParameterNames: FacebookAnalyticsServiceParameterNames;

  // Log
  logPageView();
  logEvent(name: string, value?: number, parameters?: {});
  logPurchase(amount: number, currency: string, parameters?: {});

  // User
  getUserID(): string | undefined;
  setUserID(userID: string);
  clearUserID();
  updateUserProperties(parameters: { [key: string]: string | number }, callback?: (resp: {}) => void);
}

export interface FacebookApi {
  readonly AppEvents: FacebookAnalyticsService;
  init(config: FacebookApiServiceConfig);
  login(useResponse: (response: FacebookLoginResponse) => void);
  getLoginStatus(useResponse: (response: FacebookLoginResponse) => void);
}

export class FacebookApiServiceConfig {
  version = 'v2.8';
  cookie = false;
  xfbml = false;
  status = true;
  constructor(public appId: string) { }
}

/**
 * Facebook API Service used for waiting/retrieving the facebook API from window when initialized.
 *
 * This requires some setup in index.html.
 */
@Injectable()
export class FacebookApiService extends AbstractAsyncLoadedService<FacebookApi> {

  public static readonly FACEBOOK_API_WINDOW_KEY = 'FB';
  public static readonly FACEBOOK_CALLBACK_KEY = 'fbAsyncInit';

  constructor(private _config: FacebookApiServiceConfig, @Inject(PRELOAD_FACEBOOK_TOKEN) preload: boolean = true) {
    super(FacebookApiService.FACEBOOK_API_WINDOW_KEY, FacebookApiService.FACEBOOK_CALLBACK_KEY, 'Facebook', preload);
  }

  public getApi(): Promise<FacebookApi> {
    return this.loadService();
  }

  public syncGetApi(): FacebookApi | undefined {
    return this.syncGetService();
  }

  protected initService(service: FacebookApi): Promise<FacebookApi> {
    service.init(this._config); // Initialize FB
    return Promise.resolve(service);
  }

}
