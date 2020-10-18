import { NgModule, ModuleWithProviders } from '@angular/core';

import { FacebookApiServiceConfig, FacebookApiService, PRELOAD_FACEBOOK_TOKEN } from './facebook.service';
import { FacebookOAuthService } from './auth.service';
import { FacebookSignInButtonDirective } from './button.component';

@NgModule({
  declarations: [FacebookSignInButtonDirective],
  exports: [FacebookSignInButtonDirective]
})
export class GaeFacebookModule {

  static forRoot(preloadService?: boolean): ModuleWithProviders<GaeFacebookModule> {
    return {
      ngModule: GaeFacebookModule,
      providers: [
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
