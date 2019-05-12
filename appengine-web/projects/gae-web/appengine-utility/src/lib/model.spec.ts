import 'jasmine-expect';
import { ModelUtility } from './model';

describe('ModelUtility', () => {

  // TODO
  describe('isInitializedModelKey()', () => {

    it('should return false for a passed model.', () => {
      const model = {};
      expect(ModelUtility.isInitializedModelKey(model as any)).toBeFalse();
    });

  });

});
