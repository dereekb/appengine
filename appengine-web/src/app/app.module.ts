import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injector, NgModuleFactoryLoader, SystemJsNgModuleLoader } from '@angular/core';

import { UIRouter, Category } from '@uirouter/core';
import { UIRouterModule, StatesModule, UIView } from '@uirouter/angular';
import { ROOT_STATES } from './root.states';
import { AnalyticsService, AnalyticsServiceConfiguration, AnalyticsServiceListener, GaeAnalyticsModule } from '@gae-web/appengine-analytics';
import { GaeGoogleModule, FacebookAnalyticsListenerService, FacebookApiService, GaeFacebookModule, FacebookApiServiceConfig, GoogleOAuthServiceConfig } from '@gae-web/appengine-services';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatProgressButtonsModule } from 'mat-progress-buttons';

export function routerConfigFn(router: UIRouter, injector: Injector, module: StatesModule): any {
  const transitionService = router.transitionService;
  const service: AnalyticsService = injector.get(AnalyticsService);

  transitionService.onSuccess({}, () => {
    // Send a page view on each successful transition.
    service.sendPageView();
  });

  router.trace.enable(Category.TRANSITION);

  return undefined;
}


export function analyticsServiceConfigurationFactory(facebookApi: FacebookApiService) {

  const facebookListener: AnalyticsServiceListener = new FacebookAnalyticsListenerService(facebookApi);

  const config: AnalyticsServiceConfiguration = {
    // TODO: Specify production from environment.
    isProduction: false,
    listeners: [
      // TODO: Add Google listener.
      facebookListener
    ]
  };

  return config;
}

export function makeGoogleConfig(): GoogleOAuthServiceConfig {
  return new GoogleOAuthServiceConfig('');
}

export function makeFacebookConfig(): FacebookApiServiceConfig {
  const config = new FacebookApiServiceConfig('431391914300748');
  config.logging = true;
  return config;
}

@NgModule({
  imports: [
    // GAE Imports
    GaeGoogleModule.forRoot(),
    GaeFacebookModule.forRoot(),
    GaeAnalyticsModule.forRoot({
      analyticsConfigurationProvider: {
        provide: AnalyticsServiceConfiguration,
        useFactory: analyticsServiceConfigurationFactory,
        deps: [FacebookApiService]
      }
    }),
    // Imports
    BrowserModule,
    BrowserAnimationsModule,
    MatProgressButtonsModule.forRoot(),
    UIRouterModule.forRoot({
      states: ROOT_STATES,
      useHash: false,
      otherwise: { state: 'public' },
      config: routerConfigFn
    })
  ],
  providers: [
    {
      provide: GoogleOAuthServiceConfig,
      useFactory: makeGoogleConfig
    },
    {
      provide: FacebookApiServiceConfig,
      useFactory: makeFacebookConfig
    }
  ],
  bootstrap: [UIView]
})
export class AppModule { }
