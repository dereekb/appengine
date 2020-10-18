import { ModuleWithProviders, NgModule } from '@angular/core';

import { GooglePlatformApiService, PRELOAD_GOOGLE_TOKEN } from './google.service';
import { GoogleOAuthService, GoogleOAuthServiceConfig } from './auth.service';
import { GoogleSignInButtonDirective } from './button.component';

@NgModule({
  declarations: [GoogleSignInButtonDirective],
  exports: [GoogleSignInButtonDirective]
})
export class GaeGoogleModule {

  static forRoot(preloadService?: boolean): ModuleWithProviders<GaeGoogleModule> {
    return {
      ngModule: GaeGoogleModule,
      providers: [
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
