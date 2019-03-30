import { NgModule, ModuleWithProviders, Provider } from '@angular/core';
import { AnalyticsService } from './analytics.service';


export interface AnalyticsModuleOptions {
  analyticsConfigurationProvider?: Provider;
}

@NgModule()
export class AnalyticsModule {

  static forRoot(options: AnalyticsModuleOptions): ModuleWithProviders {
    return {
      ngModule: AnalyticsModule,
      providers: [
        // Configuration
        options.analyticsConfigurationProvider,
        // Service
        AnalyticsService
      ]
    };
  }

}
