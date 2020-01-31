import 'jasmine-expect';
import { LoginTokenUtility, EncodedRefreshToken, LoginTokenPair, DecodedLoginToken } from './token';
import { StoredTokenStorageAccessor, AppTokenStorageService, AsyncAppTokenStorageService } from './storage.service';
import { UserLoginTokenAuthenticator, LegacyAppTokenUserService, AsyncAppTokenUserService } from './token.service';
import { Observable, of } from 'rxjs';
import { MemoryStorageObject, StorageObjectLimitedStorageAccessor } from '@gae-web/appengine-utility';
import { delay } from 'rxjs/operators';

describe('UserLoginTokenService', () => {

  const LOGIN_POINTER = 'F_10157705645714532';

  /**
   * The initial login token.
   */
  // tslint:disable-next-line: max-line-length
  const TEST_INITIAL_LOGIN_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NjQ0NDA2NTYwMzkxMTY4IiwiaWF0IjoxNTczNDAwMjk5LCJleHAiOjE1NzM0MDc0OTk5LCJlIjp0cnVlLCJsZ24iOjU2NDQ0MDY1NjAzOTExNjgsInB0ciI6IkZfMTAxNTc3MDU2NDU3MTQ1MzIiLCJwdCI6NywidSI6NTY0NDQwNjU2MDM5MTE2OH0.iiP24pioRFI4dErWUNly2M_j-Zg00sDOcKethcFjlFY';

  /**
   * The refresh token.
   */
  // tslint:disable-next-line: max-line-length
  const TEST_REFRESH_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NjQ0NDA2NTYwMzkxMTY4IiwiaWF0IjoxNTczNDAwMzAwLCJleHAiOjE1ODExNzYzMDAwLCJsZ24iOjU2NDQ0MDY1NjAzOTExNjgsInB0ciI6IkZfMTAxNTc3MDU2NDU3MTQ1MzIiLCJwdCI6M30.mTc_9Lq_DbohRrvBeaoWhxuIqlO0IZxTNUaf6jf_Pts';

  /**
   * The temporary login token.
   */
  // tslint:disable-next-line: max-line-length
  const TEST_LOGIN_TOKEN = 'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1NjQ0NDA2NTYwMzkxMTY4IiwiaWF0IjoxNTczNDAwMjgyLCJleHAiOjE1NzM0MDc0ODIyLCJsZ24iOjU2NDQ0MDY1NjAzOTExNjgsInB0ciI6IkZfMTAxNTc3MDU2NDU3MTQ1MzIiLCJwdCI6NywidSI6NTY0NDQwNjU2MDM5MTE2OH0.TUTAM0MLFQpjd6idQegImme7IlwDEn3djJ6cNnMdb6w';

  function makeLoginTokenPair(token) {
    return LoginTokenPair.fromJson({
      token,
      pointer: LOGIN_POINTER
    });
  }

  const TEST_INITIAL_LOGIN_TOKEN_PAIR = makeLoginTokenPair(TEST_INITIAL_LOGIN_TOKEN);

  const tokenDelayAmount = 200;

  const userLoginTokenAuthenticator: UserLoginTokenAuthenticator = {

    createRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair> {
      return of(makeLoginTokenPair(TEST_REFRESH_TOKEN)).pipe(delay(tokenDelayAmount));
    },

    loginWithRefreshToken(encodedToken: EncodedRefreshToken): Observable<LoginTokenPair> {
      return of(makeLoginTokenPair(TEST_LOGIN_TOKEN)).pipe(delay(tokenDelayAmount));
    }

  };

  describe('AsyncAppTokenUserService', () => {

    let service: AsyncAppTokenUserService;

    function describeTests() {

      describe('Login', () => {

        it('should return a next value when subscribed to.', (done) => {
          service.login(TEST_INITIAL_LOGIN_TOKEN_PAIR).subscribe({
            next: (x) => {
              expect(x).toBeTruthy();
              done();
            }
          });
        });

        it('should show authenticate true after logging in.', (done) => {
          service.login(TEST_INITIAL_LOGIN_TOKEN_PAIR).subscribe({
            next: (x) => {
              expect(x).toBeTruthy();

              service.isAuthenticated().subscribe({
                next: (isAuthenticated) => {
                  expect(isAuthenticated).toBeTruthy();
                  done();
                }
              });
            }
          });

        });

      });

      describe('Logout', () => {

        it('should logout if there is no context.', (done) => {
          service.logout().subscribe({
            next: (x) => {
              expect(x).toBeTrue();
              done();
            }
          });
        });

        it('should not be authenticated after logging out.', (done) => {
          service.login(TEST_INITIAL_LOGIN_TOKEN_PAIR).subscribe({
            next: (x) => {
              expect(x).toBeTruthy();
              service.logout().subscribe({
                next: (y) => {
                  expect(y).toBeTrue();
                  service.isAuthenticated().subscribe({
                    next: (isAuthenticated) => {
                      expect(isAuthenticated).toBeFalse();
                      done();
                    }
                  });
                }
              });
            }
          });
        });

        it('should not have a token after logging out.', (done) => {
          service.login(TEST_INITIAL_LOGIN_TOKEN_PAIR).subscribe({
            next: (x) => {
              expect(x).toBeTruthy();
              service.logout().subscribe({
                next: (y) => {
                  expect(y).toBeTrue();
                  service.getLoginToken().subscribe({
                    next: (loginToken) => {
                      expect(loginToken).not.toBeDefined();
                      done();
                    }
                  });
                }
              });
            }
          });
        });

      });

    }

    describe('with sync storage', () => {

      beforeEach(() => {
        const storageObject = new MemoryStorageObject();
        const accessor = new StorageObjectLimitedStorageAccessor(storageObject);
        const storage = new AsyncAppTokenStorageService(accessor);
        service = new AsyncAppTokenUserService(storage, userLoginTokenAuthenticator);
      });

      describeTests();

    });

    describe('with async storage', () => {

      beforeEach(() => {
        const storageObject = new MemoryStorageObject();
        const accessor = new StorageObjectLimitedStorageAccessor(storageObject);

        // TODO: Setup async storage

        const storage = new AsyncAppTokenStorageService(accessor);
        service = new AsyncAppTokenUserService(storage, userLoginTokenAuthenticator);
      });

      // describeTests();

    });

    // TODO: ...

    // TODO: Also add tests that test async storage when using login/logout.

  });

  // TODO: Add tests for AsyncAppTokenUserService

  describe('LegacyAppTokenUserService', () => {

    // tslint:disable-next-line: deprecation
    const storedTokenStorageAccessor = new StoredTokenStorageAccessor();

    // tslint:disable-next-line: deprecation
    const appTokenStorageService = new AppTokenStorageService(storedTokenStorageAccessor);

    // tslint:disable-next-line: deprecation
    const legacyAppTokenUserService = new LegacyAppTokenUserService(appTokenStorageService, userLoginTokenAuthenticator);

    describe('if authenticated', () => {

    });

    describe('if not authenticated', () => {

    });

  });

});
