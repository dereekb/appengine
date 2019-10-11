import 'jasmine-expect';
import { ModelUtility, StringModelKey, NumberModelKey } from './model';

describe('ModelUtility', () => {

  describe('isInitializedModelKey()', () => {

    it('should return false for a passed model.', () => {
      const model = {};
      expect(ModelUtility.isInitializedModelKey(model as any)).toBeFalse();
    });

  });

  describe('isEqual', () => {

    it ('should return true if a string and number are the same', () => {
      const a: NumberModelKey = 4;
      const b: StringModelKey = a.toString();

      expect(ModelUtility.isEqual(a, b)).toBe(true);
    });

  });

});
