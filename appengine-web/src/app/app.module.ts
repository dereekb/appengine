import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Injector, NgModuleFactoryLoader, SystemJsNgModuleLoader } from '@angular/core';

import { PublicModule } from './public/public.module';
import { UIRouter } from '@uirouter/core';
import { UIRouterModule, StatesModule, UIView } from '@uirouter/angular';
import { ROOT_STATES } from './root.states';
import { AnalyticsService, AnalyticsServiceConfiguration, AnalyticsServiceListener, GaeAnalyticsModule } from '@gae-web/appengine-analytics';
import { SecureModule } from './secure/secure.module';
import { GaeGoogleModule, FacebookAnalyticsListenerService, FacebookApiService, GaeFacebookModule, FacebookApiServiceConfig, GoogleOAuthServiceConfig } from '@gae-web/appengine-services';

export function routerConfigFn(router: UIRouter, injector: Injector, module: StatesModule): any {
  const transitionService = router.transitionService;
  const service: AnalyticsService = injector.get(AnalyticsService);

  transitionService.onSuccess({}, () => {
    // Send a page view on each successful transition.
    service.sendPageView();
  });

  // uiRouterInstance.trace.enable();
  return undefined;
}


export function analyticsServiceConfigurationFactory(facebookApi: FacebookApiService) {

  const facebookListener: AnalyticsServiceListener = new FacebookAnalyticsListenerService(facebookApi);

  const config: AnalyticsServiceConfiguration = {
    listeners: [
      facebookListener
    ]
  };

  return config;
}

export const GOOGLE_CONFIG = new GoogleOAuthServiceConfig('');
export const FACEBOOK_CONFIG = new FacebookApiServiceConfig('');

@NgModule({
  imports: [
    // GAE Imports
    GaeGoogleModule.forRoot(GOOGLE_CONFIG),
    GaeFacebookModule.forRoot(FACEBOOK_CONFIG),
    GaeAnalyticsModule.forRoot({
      analyticsConfigurationProvider: {
        provide: AnalyticsServiceConfiguration,
        useFactory: analyticsServiceConfigurationFactory,
        deps: [FacebookApiService]
      }
    }),
    // Imports
    BrowserModule,
    UIRouterModule.forRoot({
      states: ROOT_STATES,
      useHash: false,
      otherwise: { state: 'public' },
      config: routerConfigFn
    })
  ],
  providers: [
    {
      provide: NgModuleFactoryLoader,
      useClass: SystemJsNgModuleLoader
    }
  ],
  bootstrap: [UIView]
})
export class AppModule { }
