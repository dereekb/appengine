import { Injectable, InjectionToken, Inject } from '@angular/core';

import { AbstractAsyncLoadedService } from '../shared/async';

export const PRELOAD_GOOGLE_TOKEN = new InjectionToken<string>('PreLoadGoogleService');

export type GooglePlatformApi = any;

/**
 * Google API Service used for waiting/retrieving the gapi from https://apis.google.com/js/platform.js.
 *
 * This requires some setup in index.html,
 * specifically the function referenced by G_CALLBACK_KEY.
 */
@Injectable()
export class GooglePlatformApiService extends AbstractAsyncLoadedService<GooglePlatformApi> {

  public static readonly G_API_WINDOW_KEY = 'gapi';
  public static readonly G_CALLBACK_KEY = 'googleOnLoadCallback';

  constructor(@Inject(PRELOAD_GOOGLE_TOKEN) preload: boolean = true) {
    super(GooglePlatformApiService.G_API_WINDOW_KEY, GooglePlatformApiService.G_CALLBACK_KEY, 'Google', preload);
  }

  public load(apiName: string): Promise<any> {
    return this.getPlatformApi().then((gapi: GooglePlatformApi) => {
      return new Promise<any>((resolve, reject) => {
        const resolveFn = () => {
          resolve(gapi[apiName]);
        };

        if (gapi[apiName]) {
          resolveFn();
        } else {
          // Load the API
          gapi.load(apiName, resolveFn, reject);
        }
      });
    });
  }

  public getPlatformApi(): Promise<GooglePlatformApi> {
    return this.getService();
  }

}
