import { NgModule, ModuleWithProviders } from '@angular/core';
import { AppengineApiRouteConfiguration, AppengineApiModuleInfo, AppengineApiConfiguration } from './api.config';
import { HttpClient } from '@angular/common/http';
import { ClientLinkService } from './model/extension/link/link.service';
import { ClientSchedulerService } from './model/extension/scheduler/scheduler.service';
import { PublicLoginTokenService, PrivateLoginTokenService } from './auth/token.service';

export function appengineApiRouteConfigurationFactory(moduleInfo: AppengineApiModuleInfo) {
  return AppengineApiRouteConfiguration.makeWithInfo(moduleInfo);
}

export function appEngineClientLinkServiceFactory(routeConfig: AppengineApiRouteConfiguration, httpClient: HttpClient) {
  return new ClientLinkService({
    httpClient,
    routeConfig
  });
}

export function appEngineClientSchedulerServiceFactory(routeConfig: AppengineApiRouteConfiguration, httpClient: HttpClient) {
  return new ClientSchedulerService({
    httpClient,
    routeConfig
  });
}

@NgModule({
  declarations: [],
  exports: []
})
export class AppengineApiModule {

  static forRoot(config: AppengineApiModuleInfo): ModuleWithProviders {
    return {
      ngModule: AppengineApiModule,
      providers: [
        AppengineApiConfiguration,
        { provide: AppengineApiModuleInfo, useValue: config },
        {
          provide: AppengineApiRouteConfiguration,
          useFactory: appengineApiRouteConfigurationFactory,
          deps: [AppengineApiModuleInfo]
        },
        // Link Service
        {
          provide: ClientLinkService,
          useFactory: appEngineClientLinkServiceFactory,
          deps: [AppengineApiRouteConfiguration, HttpClient]
        },
        // Scheduler
        {
          provide: ClientSchedulerService,
          useFactory: appEngineClientSchedulerServiceFactory,
          deps: [AppengineApiRouteConfiguration, HttpClient]
        },
        // Tokens
        PublicLoginTokenService,
        PrivateLoginTokenService
      ]
    };
  }

}
