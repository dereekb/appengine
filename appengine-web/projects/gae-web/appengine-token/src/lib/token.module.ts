import { ModuleWithProviders, NgModule } from '@angular/core';
import { UIRouterModule } from '@uirouter/angular';

import { Http, RequestOptions } from '@angular/http';

import { AuthHttp, AuthConfig } from 'angular2-jwt';
import { AppTokenUserService } from './token.service';

import { AppTokenRefreshService } from './refresh.service';
import { TokenStateService, TokenStateConfig } from './state.service';
import { AppTokenStorageService, StoredTokenStorageAccessor } from './storage.service';

import { TokenAnalyticsUserSource } from './analytics.service';

export { TokenAnalyticsUserSource };

export function authHttpServiceFactory(http: Http, service: AppTokenUserService, options: RequestOptions) {
  return new AuthHttp(new AuthConfig({
    tokenGetter: () => service.getEncodedLoginToken().toPromise().catch((e) => {
      console.warn('Token expired.');
      return Promise.reject(e);
    }),
    noJwtError: false // Set true to not throw JWT errors.
  }), http, options);
}

export function appTokenStorageServiceFactory() {
  const accessor = StoredTokenStorageAccessor.getLocalStorageOrBackupAccessor();
  return new AppTokenStorageService(accessor);
}

/**
 * Module that provides authentication tokens for AuthHttp and route guarding.
 */
@NgModule({
  imports: [UIRouterModule]
})
export class TokenModule {

  public static forRoot(config: TokenStateConfig): ModuleWithProviders {
    return {
      ngModule: TokenModule,
      providers: [AppTokenUserService, TokenStateService, AppTokenRefreshService,
        {
          provide: AppTokenStorageService,
          useFactory: appTokenStorageServiceFactory
        },
        {
          provide: TokenStateConfig,
          useValue: config
        },
        {
          provide: AuthHttp,
          useFactory: authHttpServiceFactory,
          deps: [Http, AppTokenUserService, RequestOptions]
        }, TokenAnalyticsUserSource
      ]
    };
  }

}
