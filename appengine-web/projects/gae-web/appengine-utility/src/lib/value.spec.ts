import 'jasmine-expect';
import { ValueUtility, ReifyResult, ArrayDelta } from './value';

enum TestNumberEnum {
  A = -1,
  B,
  C
}

const ENUM_ARRAY = ValueUtility.mapEnumToIdsArray(TestNumberEnum);

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

  describe('#delta()', () => {

    let from: number[];
    let to: number[];

    beforeEach(() => {
      from = [1, 2, 3, 4, 5];
      to = [1, 2, 8, 9];
    });

    describe('result', () => {

      let result: ArrayDelta<number>;

      beforeEach(() => {
        result = ValueUtility.delta(from, to);
      });

      it('should have added the 2 values.', () => {
        expect(result.added).toBeArrayOfSize(2);  // 8, 9 added
      });

      it('should have kept the 2 existing values.', () => {
        expect(result.kept).toBeArrayOfSize(2); // 1, 2 kept
      });

      it('should have removed 3 values not in the target.', () => {
        expect(result.removed).toBeArrayOfSize(3); // 3, 4, 5 removed
      });

    });

  });

  describe('#reify()', () => {

    let existing: number[];
    let target: number[];

    beforeEach(() => {
      existing = [1, 2, 3, 4, 5];
      target = [1, 2, 8, 9];
    });

    describe('result', () => {

      let result: ReifyResult<number, number>;

      beforeEach(() => {
        result = ValueUtility.reify(target, existing, {
          keyForValue: (x) => x,
          make: (x) => x
        });
      });

      it('should have only the values specified in target.', () => {
        expect(result.result).toBeArrayOfSize(4);
      });

      it('should have kept the 2 existing values.', () => {
        expect(result.kept).toBeArrayOfSize(2);
      });

      it('should have removed 3 values not in the target.', () => {
        expect(result.removed).toBeArrayOfSize(3);
      });

      it('should have the delta.', () => {
        expect(result.delta).toBeDefined();
      });

    });

  });

  describe('enum', () => {

    describe('ENUM_ARRAY', () => {

      it('should have the number ids to an array', () => {
        const values = ENUM_ARRAY;

        expect(values).toBeArrayOfNumbers();
        expect(values).toBeArrayOfSize(3);
      });

    });

    describe('#mapEnumToIdsArray()', () => {

      it('should map the number ids to an array', () => {
        const values = ValueUtility.mapEnumToIdsArray(TestNumberEnum);

        expect(values).toBeArrayOfNumbers();
        expect(values).toBeArrayOfSize(3);
      });

      it('should sort values by default', () => {
        const values = ValueUtility.mapEnumToIdsArray(TestNumberEnum);

        // Check is sorted by default
        expect(values[0]).toBe(-1);
        expect(values[1]).toBe(0);
        expect(values[2]).toBe(1);
      });

    });

    describe('#mapEnumToPairs()', () => {

      it('should map the enum to a map', () => {
        const values = ValueUtility.mapEnumToPairs(TestNumberEnum);

        expect(values).toBeArrayOfObjects();
        expect(values).toBeArrayOfSize(3);
      });

    });

  });

});
