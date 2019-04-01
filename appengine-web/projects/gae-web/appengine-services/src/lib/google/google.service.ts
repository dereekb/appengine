import { Injectable } from '@angular/core';

import { AbstractAsyncLoadedService } from '../shared/async';

export type GooglePlatformApi = any;

/**
 * Google API Service used for waiting/retrieving the gapi from https://apis.google.com/js/platform.js.
 *
 * This requires some setup in index.html,
 * specifically the function referenced by G_CALLBACK_KEY.
 */
@Injectable()
export class GooglePlatformApiService extends AbstractAsyncLoadedService<GooglePlatformApi> {

  private static G_API_WINDOW_KEY = 'gapi';
  private static G_CALLBACK_KEY = 'googleOnLoadCallback';

  constructor() {
    super(GooglePlatformApiService.G_API_WINDOW_KEY, GooglePlatformApiService.G_CALLBACK_KEY, 'Google');
    this.loadService(); // Pre-load service.
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
