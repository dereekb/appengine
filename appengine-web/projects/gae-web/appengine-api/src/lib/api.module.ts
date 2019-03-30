import { NgModule, ModuleWithProviders } from '@angular/core';
import { ApiRouteConfiguration, ApiModuleInfo, ApiConfiguration } from './api.config';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ClientLinkService } from './model/extension/link/link.service';
import { ClientSchedulerService } from './model/extension/scheduler/scheduler.service';
import { PublicLoginTokenService, PrivateLoginTokenService } from './auth/token.service';
import { TokenModule, UserLoginTokenService } from '@gae-web/appengine-token';
import { JwtModule, JWT_OPTIONS } from '@auth0/angular-jwt';
import { OAuthLoginService } from './auth/oauth.service';
import { RegisterService } from './auth/register.service';

export function jwtOptionsFactory(userLoginTokenService: UserLoginTokenService, apiConfig: ApiConfiguration) {
  const throwNoTokenError = false;
  const skipWhenExpired = false;
  const whiteListedDomains = [];
  const blackListedRoutes = [];

  // TODO: Add black/white list parameters using the module's info.

  return {
    tokenGetter: () => {
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
    TokenModule,
    HttpClientModule,
    JwtModule.forRoot({
      jwtOptionsProvider: {
        provide: JWT_OPTIONS,
        useFactory: jwtOptionsFactory,
        deps: [UserLoginTokenService, ApiConfiguration]
      }
    })
  ]
})
export class ApiModule {

  static forTest(): ModuleWithProviders {
    const testModule = this.forApp({
      version: 'test',
      name: 'test'
    });

    return testModule;
  }

  static forApp(config: ApiModuleInfo): ModuleWithProviders {
    return {
      ngModule: ApiModule,
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
        PublicLoginTokenService,
        PrivateLoginTokenService,
        OAuthLoginService,
        RegisterService
      ]
    };
  }

}