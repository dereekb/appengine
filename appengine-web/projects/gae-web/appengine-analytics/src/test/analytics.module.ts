import { NgModule, ModuleWithProviders, Provider } from '@angular/core';
import { AnalyticsService, AnalyticsServiceConfiguration } from '../lib/analytics.service';
import { AnalyticsModule } from '../lib/analytics.module';

@NgModule()
export class TestAnalyticsModule {

  static forRoot(): ModuleWithProviders {

    const config: AnalyticsServiceConfiguration = {
      listeners: [] // No listeners.
    };

    return AnalyticsModule.forRoot({
      analyticsConfigurationProvider: {
        provide: AnalyticsServiceConfiguration,
        useValue: config
      }
    });
  }

}
