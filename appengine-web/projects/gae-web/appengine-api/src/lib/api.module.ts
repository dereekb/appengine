import { NgModule, ModuleWithProviders, Injector } from '@angular/core';
import { ApiRouteConfiguration, ApiModuleInfo, ApiConfiguration } from './api.config';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ClientLinkService } from './model/extension/link/link.service';
import { ClientSchedulerService } from './model/extension/scheduler/scheduler.service';
import { PublicLoginTokenApiService, PrivateLoginTokenApiService, ApiUserLoginTokenAuthenticator } from './auth/token.service';
import { GaeTokenModule, UserLoginTokenService, UserLoginTokenAuthenticator } from '@gae-web/appengine-token';
import { JwtModule, JWT_OPTIONS, JwtModuleOptions } from '@auth0/angular-jwt';
import { RegisterApiService } from './auth/register.service';
import { OAuthLoginApiService } from './auth/oauth.service';
import { catchError, defaultIfEmpty } from 'rxjs/operators';
import { Observable, of } from 'rxjs';

export function jwtOptionsFactory(userLoginTokenService: UserLoginTokenService, apiConfig: ApiConfiguration) {
  const throwNoTokenError = false;
  const skipWhenExpired = false;
  const whitelistedDomains = [];

  function makeRouteRegex(route: string, openEnd = true, addRoot = true) {
    let prefix = '.*';

    if (addRoot) {
      prefix = prefix + apiConfig.routeConfig.root;
    }

    route = prefix + route;

    // Escape all route slashes
    route = route.replace(/\//g, '\\/');

    if (openEnd) {
      route = route + '.*';
    }

    return new RegExp(route, 'i');
  }

  const blacklistedRoutes = [makeRouteRegex(OAuthLoginApiService.SERVICE_PATH)];

  // TODO: Add black/white list parameters using the module's info.

  return {
    tokenGetter: () => {
      const obs = userLoginTokenService.getEncodedLoginToken().pipe(
        catchError(() => of(null))
      );

      return obs.toPromise() as Promise<string | null>;
    },
    throwNoTokenError,
    skipWhenExpired,
    whitelistedDomains,
    blacklistedRoutes
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
    GaeTokenModule
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
