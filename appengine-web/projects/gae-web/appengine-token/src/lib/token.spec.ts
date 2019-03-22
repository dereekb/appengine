import { LoginTokenUtility } from './token';

const TEST_INVALID_ENCODED_TOKEN = 'INVALID';

describe('LoginTokenUtility', () => {

  describe('fromEncodedToken()', () => {

    it('should fail if the token is invalid.', () => {
      expect(() => {
        LoginTokenUtility.fromEncodedToken(TEST_INVALID_ENCODED_TOKEN);
      }).toThrowError();
    });

  });

});
