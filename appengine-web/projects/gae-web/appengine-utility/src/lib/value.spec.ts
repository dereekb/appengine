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

  describe('#copyObjectProperties()', () => {

    it('should copy all properties from one object to another.', () => {
      const from = {
        a: 1,
        b: 2
      };

      const to = {} as any;

      ValueUtility.copyObjectProperties(from, to);

      expect(to.a).toBe(1);
      expect(to.b).toBe(2);
    });

    it('should copy only expected properties from one object to another.', () => {
      const from = {
        a: 1,
        b: 2
      };

      const to = {} as any;

      ValueUtility.copyObjectProperties(from, to, ['a']);

      expect(to.a).toBe(1);
      expect(to.b).toBeUndefined();
    });

  });

  describe('#isEqualIgnoreCase()', () => {

    it('should return true if the two values are equal with different cases.', () => {
      const isEqual = ValueUtility.isEqualIgnoreCase('ABC', 'abc');
      expect(isEqual).toBeTrue();
    });

  });

});
