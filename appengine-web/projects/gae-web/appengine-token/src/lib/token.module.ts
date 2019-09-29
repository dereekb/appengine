import { ModuleWithProviders, NgModule } from '@angular/core';
import { UserLoginTokenService, LegacyAppTokenUserService } from './token.service';
import { AppTokenStorageService, StoredTokenStorageAccessor } from './storage.service';

export function appTokenStorageServiceFactory(accessor: StoredTokenStorageAccessor) {
  return new AppTokenStorageService(accessor);
}

export function memoryStoredTokenStorageAccessorFactory() {
  return new StoredTokenStorageAccessor();
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
          useFactory: appTokenStorageServiceFactory,
          deps: [StoredTokenStorageAccessor]
        }
      ]
    };
  }

}

/**
 * Pre-configured storage accessor that only uses memory.
 */
@NgModule({
  imports: [GaeTokenModule.forRoot()],
  providers: [{
    provide: StoredTokenStorageAccessor,
    useFactory: memoryStoredTokenStorageAccessorFactory
  }]
})
export class MemoryGaeTokenModule {}
