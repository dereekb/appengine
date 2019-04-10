import { NgModule, ModuleWithProviders, Provider } from '@angular/core';
import { AnalyticsService, AnalyticsServiceConfiguration } from '../lib/analytics.service';
import { GaeAnalyticsModule } from '../lib/analytics.module';

@NgModule()
export class TestAnalyticsModule {

  static forRoot(): ModuleWithProviders {

    const config: AnalyticsServiceConfiguration = {
      listeners: [] // No listeners.
    };

    return GaeAnalyticsModule.forRoot({
      analyticsConfigurationProvider: {
        provide: AnalyticsServiceConfiguration,
        useValue: config
      }
    });
  }

}
