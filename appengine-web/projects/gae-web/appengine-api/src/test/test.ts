import { ApiModuleInfo, GaeApiModuleTypesConfiguration, GaeApiModuleRouteConfiguration, GaeApiModuleConfiguration } from '../lib/api.config';

export class TestUtility {

  static testModuleInfo(): ApiModuleInfo {
    return {
      version: 'v',
      name: 'test'
    };
  }

  static testApiRouteConfig(): GaeApiModuleRouteConfiguration {
    const result = GaeApiModuleRouteConfiguration.makeWithInfo(this.testModuleInfo());
    return result;
  }

  static testApiTypesConfig(): GaeApiModuleTypesConfiguration {
    const result = new GaeApiModuleTypesConfiguration(['foo']);
    return result;
  }

  static testApiModuleConfiguration(): GaeApiModuleConfiguration {
    const result = new GaeApiModuleConfiguration(this.testModuleInfo(), this.testApiTypesConfig());
    return result;
  }

}
