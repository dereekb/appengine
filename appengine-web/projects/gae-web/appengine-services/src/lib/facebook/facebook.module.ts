import { NgModule, ModuleWithProviders } from '@angular/core';

import { FacebookApiServiceConfig, FacebookApiService, PRELOAD_FACEBOOK_TOKEN } from './facebook.service';
import { FacebookOAuthService } from './auth.service';
import { FacebookSignInButtonDirective } from './button.component';

import { FacebookAnalyticsListenerService } from './listener.service';

export { FacebookAnalyticsListenerService };

@NgModule({
  declarations: [FacebookSignInButtonDirective],
  exports: [FacebookSignInButtonDirective]
})
export class FacebookModule {

  static forRoot(config: FacebookApiServiceConfig, preloadService?: boolean): ModuleWithProviders {
    return {
      ngModule: FacebookModule,
      providers: [
        {
          provide: FacebookApiServiceConfig,
          useValue: config
        },
        {
          provide: PRELOAD_FACEBOOK_TOKEN,
          useValue: preloadService
        },
        FacebookApiService,
        FacebookOAuthService
      ]
    };
  }

}
