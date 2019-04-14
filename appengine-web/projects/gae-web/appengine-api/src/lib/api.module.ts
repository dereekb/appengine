import { NgModule, ModuleWithProviders, Injector } from '@angular/core';
import { ApiRouteConfiguration, ApiModuleInfo, ApiConfiguration } from './api.config';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ClientLinkService } from './model/extension/link/link.service';
import { ClientSchedulerService } from './model/extension/scheduler/scheduler.service';
import { PublicLoginTokenApiService, PrivateLoginTokenApiService, ApiUserLoginTokenAuthenticator } from './auth/token.service';
import { GaeTokenModule, UserLoginTokenService, UserLoginTokenAuthenticator } from '@gae-web/appengine-token';
import { JwtModule, JWT_OPTIONS } from '@auth0/angular-jwt';
import { RegisterApiService } from './auth/register.service';
import { OAuthLoginApiService } from './auth/oauth.service';
import { LazyCache } from '@gae-web/appengine-utility';

export function jwtOptionsFactory(injector: Injector, apiConfig: ApiConfiguration) {
  const throwNoTokenError = false;
  const skipWhenExpired = false;
  const whiteListedDomains = [];
  const blackListedRoutes = [];

  // TODO: Add black/white list parameters using the module's info.

  const cache = new LazyCache<UserLoginTokenService>({
    refresh() {
      return injector.get(UserLoginTokenService);
    }
  });

  return {
    tokenGetter: () => {
      const userLoginTokenService = cache.getValue();
      const obs = userLoginTokenService.getEncodedLoginToken();
      return obs.toPromise();
    },
    throwNoTokenError,
    skipWhenExpired,
    whiteListedDomains,
    blackListedRoutes
  };
}

export function apiRouteConfigurationFactory(moduleInfo: ApiModuleInfo) {
  return ApiRouteConfiguration.makeWithInfo(moduleInfo);
}

export function clientLinkServiceFactory(routeConfig: ApiRouteConfiguration, httpClient: HttpClient) {
  return new ClientLinkService({
    httpClient,
    routeConfig
  });
}

export function clientSchedulerServiceFactory(routeConfig: ApiRouteConfiguration, httpClient: HttpClient) {
  return new ClientSchedulerService({
    httpClient,
    routeConfig
  });
}

@NgModule({
  imports: [
    GaeTokenModule,
    HttpClientModule,
    JwtModule
  ]
})
export class GaeApiModule {

  static makeJwtModuleForRoot(): ModuleWithProviders {
    return JwtModule.forRoot({
      jwtOptionsProvider: {
        provide: JWT_OPTIONS,
        useFactory: jwtOptionsFactory,
        deps: [UserLoginTokenService, ApiConfiguration]
      }
    });
  }

  static forTest(): ModuleWithProviders {
    const testModule = this.forApp({
      version: 'test',
      name: 'test'
    });

    return testModule;
  }

  static forApp(config: ApiModuleInfo): ModuleWithProviders {
    return {
      ngModule: GaeApiModule,
      providers: [
        // Configurations
        ApiConfiguration,
        {
          provide: ApiModuleInfo,
          useValue: config
        },
        {
          provide: ApiRouteConfiguration,
          useFactory: apiRouteConfigurationFactory,
          deps: [ApiModuleInfo]
        },
        // Link Service
        {
          provide: ClientLinkService,
          useFactory: clientLinkServiceFactory,
          deps: [ApiRouteConfiguration, HttpClient]
        },
        // Scheduler
        {
          provide: ClientSchedulerService,
          useFactory: clientSchedulerServiceFactory,
          deps: [ApiRouteConfiguration, HttpClient]
        },
        // Tokens and Auth
        PublicLoginTokenApiService,
        PrivateLoginTokenApiService,
        OAuthLoginApiService,
        RegisterApiService,
        // ApiUserLoginTokenAuthenticator
        {
          provide: UserLoginTokenAuthenticator,
          useClass: ApiUserLoginTokenAuthenticator,
          deps: [PublicLoginTokenApiService]
        }
      ]
    };
  }

}
