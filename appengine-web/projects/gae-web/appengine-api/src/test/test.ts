import { ApiRouteConfiguration, ApiModuleInfo, ApiConfiguration } from '../lib/api.config';

export class TestUtility {

  static testModuleInfo(): ApiModuleInfo {
    return {
      version: 'v',
      name: 'test'
    };
  }

  static testApiRouteConfig(): ApiRouteConfiguration {
    const result = ApiRouteConfiguration.makeWithInfo(this.testModuleInfo());
    return result;
  }

  static testApiConfiguration(): ApiConfiguration {
    const result = new ApiConfiguration(this.testModuleInfo(), this.testApiRouteConfig());
    return result;
  }

}
