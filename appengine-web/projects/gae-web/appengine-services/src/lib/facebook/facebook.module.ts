import { NgModule, ModuleWithProviders } from '@angular/core';

import { FacebookApiServiceConfig, FacebookApiService } from './facebook.service';
import { FacebookOAuthService } from './auth.service';
import { FacebookSignInButtonDirective } from './button.component';

import { FacebookAnalyticsListenerService } from './listener.service';

export { FacebookAnalyticsListenerService };

@NgModule({
  declarations: [FacebookSignInButtonDirective],
  exports: [FacebookSignInButtonDirective]
})
export class FacebookModule {

  static forRoot(config: FacebookApiServiceConfig): ModuleWithProviders {
    return {
      ngModule: FacebookModule,
      providers: [
        FacebookApiService,
        FacebookOAuthService,
        {
          provide: FacebookApiServiceConfig,
          useValue: config
        }
      ]
    };
  }

}
