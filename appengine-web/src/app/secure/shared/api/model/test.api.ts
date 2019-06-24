import { NgModule, ModuleWithProviders, InjectionToken } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { GaeApiModuleConfiguration, ApiModuleConstructorConfiguration, GaeApiModuleInfo, GaeApiModuleTypesConfiguration, ApiModuleService } from '@gae-web/appengine-api';

/**
 * ApiModuleService for the Test API Module.
 */
export class TestApiModuleService extends ApiModuleService { }

/**
 * Module configuration for the Test API Module.
 */
export class TestApiModuleConfiguration extends GaeApiModuleConfiguration {

  public static make({ version = 'v1', name = 'test', types = [] }: ApiModuleConstructorConfiguration): TestApiModuleConfiguration {
    const info = new GaeApiModuleInfo(version, name);
    const typesConfig = new GaeApiModuleTypesConfiguration(types);
    return new TestApiModuleConfiguration(info, typesConfig);
  }

}

export const TEST_API_ROUTE_CONFIGURATION_TOKEN = new InjectionToken<string>('TestApiModuleRouteConfiguration');

// MARK: Module
export function testApiModuleServiceFactory(moduleConfig: TestApiModuleConfiguration, httpClient: HttpClient) {
  return new TestApiModuleService(moduleConfig, httpClient);
}

/**
 * Configuration for accessing the Test module.
 */
@NgModule()
export class TestApiModule {

  static forApp(config: TestApiModuleConfiguration = TestApiModuleConfiguration.make({})): ModuleWithProviders {
    return {
      ngModule: TestApiModule,
      providers: [
        // Configurations
        {
          provide: TestApiModuleConfiguration,
          useValue: config
        },
        {
          provide: TEST_API_ROUTE_CONFIGURATION_TOKEN,
          useExisting: TestApiModuleConfiguration
        },
        // Module Services
        {
          provide: TestApiModuleService,
          useFactory: testApiModuleServiceFactory,
          deps: [TestApiModuleConfiguration, HttpClient]
        }
      ]
    };
  }

}
