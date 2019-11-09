import { ModuleWithProviders, NgModule, InjectionToken } from '@angular/core';
import { UserLoginTokenService, LegacyAppTokenUserService, AsyncAppTokenUserService } from './token.service';
import { AppTokenStorageService, StoredTokenStorageAccessor, AsyncAppTokenStorageService } from './storage.service';
import { StorageObjectLimitedStorageAccessor, MemoryStorageObject } from '@gae-web/appengine-utility';

export function memoryStoredTokenStorageAccessorFactory() {
  const storageObject = new MemoryStorageObject();
  const accessor = new StorageObjectLimitedStorageAccessor(storageObject);
  return new AsyncAppTokenStorageService(accessor);
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
          useClass: AsyncAppTokenUserService
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
    provide: AsyncAppTokenStorageService,
    useFactory: memoryStoredTokenStorageAccessorFactory
  }]
})
export class MemoryGaeTokenModule { }
