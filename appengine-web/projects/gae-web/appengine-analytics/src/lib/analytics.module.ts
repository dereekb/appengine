import { NgModule, ModuleWithProviders, Provider } from '@angular/core';
import { AnalyticsService } from './analytics.service';


export interface GaeAnalyticsModuleOptions {

  /**
   * Provides a AnalyticsServiceConfiguration value.
   */
  analyticsConfigurationProvider?: Provider;

}

@NgModule()
export class GaeAnalyticsModule {

  static forRoot(options: GaeAnalyticsModuleOptions): ModuleWithProviders<GaeAnalyticsModule> {
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
