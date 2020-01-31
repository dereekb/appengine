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

  describe('#setHasAny()', () => {

    it('should return true if the set has the value.', () => {
      const value = 1;
      const set = ValueUtility.arrayToSet([value, 2, 3, 4, 5]);
      const setWithValues = ValueUtility.arrayToSet([value]);
      const result = ValueUtility.setHasAny(set, setWithValues);
      expect(result).toBeTrue();
    });

    it('should return false if the set does not have the value.', () => {
      const value = 1;
      const set = ValueUtility.arrayToSet([2, 3, 4, 5]);
      const result = ValueUtility.setHasAny(set, [value]);
      expect(result).toBeFalse();
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

  describe('#takeRandomElements()', () => {

    let testArray: number[];

    describe('limit is bigger than the array size', () => {

      let elementsToTake;
      let shuffledArray: number[];

      beforeEach(() => {
        testArray = [1, 2, 3];
        elementsToTake = testArray.length + 100;
        shuffledArray = ValueUtility.takeRandomElements(testArray, elementsToTake);
      });

      it('should return all elements shuffled.', () => {
        expect(shuffledArray.length).toBe(testArray.length);
      });

      it('should have an item at the first index.', () => {
        expect(shuffledArray[0]).toBeDefined();
      });

    });

  });

});
