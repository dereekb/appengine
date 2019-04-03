import { ModuleWithProviders, NgModule } from '@angular/core';

import { GooglePlatformApiService, PRELOAD_GOOGLE_TOKEN } from './google.service';
import { GoogleOAuthService, GoogleOAuthServiceConfig } from './auth.service';
import { GoogleSignInButtonDirective } from './button.component';

@NgModule({
  declarations: [GoogleSignInButtonDirective],
  exports: [GoogleSignInButtonDirective]
})
export class GoogleModule {

  static forRoot(config: GoogleOAuthServiceConfig, preloadService?: boolean): ModuleWithProviders {
    return {
      ngModule: GoogleModule,
      providers: [
        {
          provide: GoogleOAuthServiceConfig,
          useValue: config
        },
        {
          provide: PRELOAD_GOOGLE_TOKEN,
          useValue: preloadService
        },
        GooglePlatformApiService,
        GoogleOAuthService
      ]
    };
  }

}
