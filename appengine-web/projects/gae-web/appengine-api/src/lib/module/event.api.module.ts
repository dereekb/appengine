import { NgModule, ModuleWithProviders, InjectionToken } from '@angular/core';
import { GaeApiModuleConfiguration, GaeApiModuleInfo, GaeApiModuleTypesConfiguration, ApiModuleConstructorConfiguration } from '../api.config';
import { HttpClient } from '@angular/common/http';
import { ApiModuleService } from '../api.service';

/**
 * ApiModuleService for the Event API Module.
 */
export class GaeEventApiModuleService extends ApiModuleService { }

/**
 * Module configuration for the Event API Module.
 */
export class GaeEventApiModuleConfiguration extends GaeApiModuleConfiguration {

  public static make({ version = 'v1', name = 'event', types = [], server = '' }: ApiModuleConstructorConfiguration): GaeEventApiModuleConfiguration {
    const info = new GaeApiModuleInfo(version, name, server);
    const typesConfig = new GaeApiModuleTypesConfiguration(types);
    return new GaeEventApiModuleConfiguration(info, typesConfig);
  }

}

export const EVENT_API_ROUTE_CONFIGURATION_TOKEN = new InjectionToken<string>('EventApiModuleRouteConfiguration');

// MARK: Module
export function eventApiModuleServiceFactory(moduleConfig: GaeEventApiModuleConfiguration, httpClient: HttpClient) {
  return new GaeEventApiModuleService(moduleConfig, httpClient);
}

/**
 * Configuration for accessing the Event module.
 */
@NgModule()
export class GaeEventApiModule {

  static forApp(): ModuleWithProviders<GaeEventApiModule> {
    return {
      ngModule: GaeEventApiModule,
      providers: [
        // Configurations
        {
          provide: EVENT_API_ROUTE_CONFIGURATION_TOKEN,
          useExisting: GaeEventApiModuleConfiguration
        },
        // Module Services
        {
          provide: GaeEventApiModuleService,
          useFactory: eventApiModuleServiceFactory,
          deps: [GaeEventApiModuleConfiguration, HttpClient]
        }
      ]
    };
  }

}
