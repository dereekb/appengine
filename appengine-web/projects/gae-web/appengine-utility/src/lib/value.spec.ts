import { ValueUtility } from './value';

describe('ValueUtility', () => {

  describe('readValue()', () => {

    it('should return a value from the executed function', () => {
      const value = 123;
      const result = ValueUtility.readValue(() => value);
      expect(result).toEqual(value);
    });

  });

});
