import { NgModule, ModuleWithProviders, Injector, InjectionToken } from '@angular/core';
import { GaeApiModuleRouteConfiguration, GaeApiModuleInfo, GaeApiModuleConfiguration, GaeApiModuleTypesConfiguration, ApiModuleConstructorConfiguration } from '../api.config';
import { HttpClient, HttpClientModule, HttpBackend } from '@angular/common/http';
import { PublicLoginTokenApiService, ApiUserLoginTokenAuthenticator } from '../auth/token.service';
import { GaeTokenModule, UserLoginTokenService, UserLoginTokenAuthenticator } from '@gae-web/appengine-token';
import { JwtModule, JWT_OPTIONS } from '@auth0/angular-jwt';
import { RegisterApiService } from '../auth/register.service';
import { OAuthLoginApiService } from '../auth/oauth.service';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { ApiModuleService } from '../api.service';
import { ClientUserNotificationService } from '../model/extension/notification/notification.service';
import { ClientServiceConfig } from '../model/client.service';
import { UserNotificationService } from '@gae-web/appengine-api/public-api';

/**
 * ApiModuleService for the Login API Module.
 */
export class GaeLoginApiModuleService extends ApiModuleService { }

/**
 * Module configuration for the Login API Module.
 */
export class GaeLoginApiModuleConfiguration extends GaeApiModuleConfiguration {

  public static make({ version = 'v1', name = 'login', types = ['app', 'login', 'loginpointer', 'loginkey'], server = '' }: ApiModuleConstructorConfiguration): GaeLoginApiModuleConfiguration {
    const info = new GaeApiModuleInfo(version, name, server);
    const typesConfig = new GaeApiModuleTypesConfiguration(types);
    return new GaeLoginApiModuleConfiguration(info, typesConfig);
  }

}

export const LOGIN_API_ROUTE_CONFIGURATION_TOKEN = new InjectionToken<string>('LoginApiModuleRouteConfiguration');

// MARK: Module
export function loginApiModuleServiceFactory(moduleConfig: GaeLoginApiModuleConfiguration, httpClient: HttpClient) {
  return new GaeLoginApiModuleService(moduleConfig, httpClient);
}

export function loginApiModuleRouteConfigurationFactory(moduleConfig: GaeLoginApiModuleConfiguration) {
  return moduleConfig.routeConfig;
}

export function clientUserNotificationServiceFactory(moduleConfig: GaeLoginApiModuleConfiguration, httpClient: HttpClient) {
  const clientConfig: ClientServiceConfig = {
    httpClient,
    routeConfig: moduleConfig.routeConfig
  };

  return new ClientUserNotificationService(clientConfig);
}

/**
 * Configuration for accessing the Login module.
 */
@NgModule({
  imports: [
    GaeTokenModule
  ]
})
export class GaeLoginApiModule {

  static forApp(): ModuleWithProviders {
    return {
      ngModule: GaeLoginApiModule,
      providers: [
        // Configurations
        {
          provide: LOGIN_API_ROUTE_CONFIGURATION_TOKEN,
          useExisting: GaeLoginApiModuleConfiguration
        },
        // Module Services
        {
          provide: GaeLoginApiModuleService,
          useFactory: loginApiModuleServiceFactory,
          deps: [GaeLoginApiModuleConfiguration, HttpClient]
        },
        /**
         * LOGIN API ONLY
         * - Avoid copying these.
         */
        // Tokens and Auth
        {
          provide: PublicLoginTokenApiService,
          useClass: PublicLoginTokenApiService,
          deps: [HttpBackend, LOGIN_API_ROUTE_CONFIGURATION_TOKEN]
        },
        {
          provide: OAuthLoginApiService,
          useClass: OAuthLoginApiService,
          deps: [HttpBackend, LOGIN_API_ROUTE_CONFIGURATION_TOKEN]
        },
        {
          provide: RegisterApiService,
          useClass: RegisterApiService,
          deps: [HttpClient, LOGIN_API_ROUTE_CONFIGURATION_TOKEN]
        },
        // ApiUserLoginTokenAuthenticator
        {
          provide: UserLoginTokenAuthenticator,
          useClass: ApiUserLoginTokenAuthenticator,
          deps: [PublicLoginTokenApiService]
        },
        // ClientUserNotificationService
        {
          provide: ClientUserNotificationService,
          useFactory: clientUserNotificationServiceFactory,
          deps: [GaeLoginApiModuleConfiguration, HttpClient]
        },
        {
          provide: UserNotificationService,
          useExisting: ClientUserNotificationService
        }
      ]
    };
  }

}

// MARK: Configured JWT
export class GaeJwtConfiguration {
  whitelistedDomains?: string[];
  blacklistedRoutes?: RegExp[];
  constructor() { }
}

export function jwtOptionsFactory(userLoginTokenService: UserLoginTokenService, apiConfig: GaeLoginApiModuleConfiguration, gaeJwtConfiguration: GaeJwtConfiguration) {
  const throwNoTokenError = false;
  const skipWhenExpired = false;
  const whitelistedDomains = gaeJwtConfiguration.whitelistedDomains || [];

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

  const blacklistedRoutes = [
    makeRouteRegex(OAuthLoginApiService.SERVICE_PATH),
    makeRouteRegex(PublicLoginTokenApiService.SERVICE_PATH),
  ].concat(gaeJwtConfiguration.blacklistedRoutes || []);

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

/**
 * Convenience module with default configuration for JwtModule for GaeLoginApi.
 */
@NgModule({
  imports: [
    GaeTokenModule,
    JwtModule.forRoot({
      jwtOptionsProvider: {
        provide: JWT_OPTIONS,
        useFactory: jwtOptionsFactory,
        deps: [UserLoginTokenService, GaeLoginApiModuleConfiguration, GaeJwtConfiguration]
      }
    }),
  ]
})
export class GaeLoginApiConfiguredJwtModule { }
