import 'jasmine-expect';
import { ValueUtility } from './value';

describe('ValueUtility', () => {

  describe('#readValue()', () => {

    it('should return a value from the executed function', () => {
      const value = 123;
      const result = ValueUtility.readValue(() => value);
      expect(result).toEqual(value);
    });

  });

  describe('#separateValues()', () => {

    it('should include values requested to be included', () => {
      const includedValues = [2, 3, 4];
      const values = [1].concat(includedValues);

      const result = ValueUtility.separateValues(values, (x) => x > 1);

      expect(result).toBeObject();
      expect(result.excluded).toContain(1);
      expect(result.included).not.toBeEmptyArray();
      expect(result.included).toBeArrayOfNumbers();
    });

  });

});
