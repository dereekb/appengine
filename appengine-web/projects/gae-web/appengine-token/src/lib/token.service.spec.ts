import { LoginTokenUtility, EncodedRefreshToken, LoginTokenPair } from './token';
import { StoredTokenStorageAccessor, AppTokenStorageService } from './storage.service';
import { UserLoginTokenAuthenticator, LegacyAppTokenUserService } from './token.service';
import { Observable, of } from 'rxjs';

describe('LegacyAppTokenUserService', () => {

  const userLoginTokenAuthenticator: UserLoginTokenAuthenticator = {
    createRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair> {
      return of(undefined);
    },
    loginWithRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair> {
      return of(undefined);
    }
  };

  const storedTokenStorageAccessor = StoredTokenStorageAccessor.getLocalStorageOrBackupAccessor();
  const appTokenStorageService = new AppTokenStorageService(storedTokenStorageAccessor);
  const legacyAppTokenUserService = new LegacyAppTokenUserService(appTokenStorageService, userLoginTokenAuthenticator);

  describe('if authenticated', () => {

  });

  describe('if not authenticated', () => {

  });

});
