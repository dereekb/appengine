import { ModuleWithProviders, NgModule } from '@angular/core';
import { UserLoginTokenService, LegacyAppTokenUserService } from './token.service';
import { AppTokenStorageService, StoredTokenStorageAccessor } from './storage.service';

export function appTokenStorageServiceFactory() {
  const accessor = StoredTokenStorageAccessor.getLocalStorageOrBackupAccessor();
  return new AppTokenStorageService(accessor);
}

/**
 * Module that provides services for managing JWTs for HttpClient.
 *
 * However, this module does not configure those relationships.
 */
@NgModule({
  imports: []
})
export class GaeTokenModule {

  public static forRoot(): ModuleWithProviders {
    return {
      ngModule: GaeTokenModule,
      providers: [
        {
          provide: UserLoginTokenService,
          useClass: LegacyAppTokenUserService
        },
        {
          provide: AppTokenStorageService,
          useFactory: appTokenStorageServiceFactory
        }
      ]
    };
  }

}
