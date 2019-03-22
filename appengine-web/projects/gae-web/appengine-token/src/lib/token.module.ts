import { ModuleWithProviders, NgModule } from '@angular/core';
import { UIRouterModule } from '@uirouter/angular';

import { AppTokenLoginAccessor, UserLoginTokenService, LegacyAppTokenUserService } from './token.service';

import { TokenStateService, TokenStateConfig } from './state.service';
import { AppTokenStorageService, StoredTokenStorageAccessor } from './storage.service';

export function appTokenStorageServiceFactory() {
  const accessor = StoredTokenStorageAccessor.getLocalStorageOrBackupAccessor();
  return new AppTokenStorageService(accessor);
}

/**
 * Module that provides authentication tokens for AuthHttp and route guarding.
 */
@NgModule({
  imports: [UIRouterModule]
})
export class TokenModule {

  public static forRoot(config: TokenStateConfig): ModuleWithProviders {
    return {
      ngModule: TokenModule,
      providers: [TokenStateService,
        {
          provide: UserLoginTokenService,
          useClass: LegacyAppTokenUserService
        },
        {
          provide: AppTokenStorageService,
          useFactory: appTokenStorageServiceFactory
        },
        {
          provide: TokenStateConfig,
          useValue: config
        }
      ]
    };
  }

}
