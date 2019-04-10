import { NgModule, ModuleWithProviders, Provider } from '@angular/core';
import { AnalyticsService } from './analytics.service';


export interface GaeAnalyticsModuleOptions {
  analyticsConfigurationProvider?: Provider;
}

@NgModule()
export class GaeAnalyticsModule {

  static forRoot(options: GaeAnalyticsModuleOptions): ModuleWithProviders {
    return {
      ngModule: GaeAnalyticsModule,
      providers: [
        // Configuration
        options.analyticsConfigurationProvider,
        // Service
        AnalyticsService
      ]
    };
  }

}
