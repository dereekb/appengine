import { ModuleWithProviders, NgModule } from '@angular/core';

import { GooglePlatformApiService } from './google.service';
import { GoogleOAuthService, GoogleOAuthServiceConfig } from './auth.service';
import { GoogleSignInButtonDirective } from './button.component';

@NgModule({
  declarations: [GoogleSignInButtonDirective],
  exports: [GoogleSignInButtonDirective]
})
export class GoogleModule {

  static forRoot(config: GoogleOAuthServiceConfig): ModuleWithProviders {
    return {
      ngModule: GoogleModule,
      providers: [
        GooglePlatformApiService,
        GoogleOAuthService,
        {
          provide: GoogleOAuthServiceConfig,
          useValue: config
        }
      ]
    };
  }

}
