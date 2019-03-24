import { AuthUtility } from './auth.utility';

describe('AuthUtility', () => {

  describe('readAuth()', () => {

    it('should return a value from the executed function', () => {
      const token = 'MY_TOKEN';
      const result = AuthUtility.buildHeaderWithAuthentication(token);
      expect(result).toBeDefined();
    });

  });

});
