import 'jasmine-expect';
import { ModelServiceWrapper, ModelServiceWrapperSet, ModelWrapperInitializedError } from './model.service';
import { Foo, FOO_MODEL_TYPE, FooReadService } from '@gae-web/appengine-api';

describe('Model Services', () => {

  describe('ModelServiceWrapperSet', () => {

    let modelServiceWrapperSet: ModelServiceWrapperSet;

    beforeEach(() => {
      modelServiceWrapperSet = new ModelServiceWrapperSet();
    });

    it(`#initWrapper() should create a new ModelServiceWrapper if it doesn't exist.`, () => {
      expect(modelServiceWrapperSet.initWrapper<Foo>({
        type: FOO_MODEL_TYPE
      })).toBeDefined();
    });

    it(`#initWrapper() should throw an error if a model for that type already exists.`, () => {

      // Init one time.
      modelServiceWrapperSet.initWrapper<Foo>({
        type: FOO_MODEL_TYPE
      });

      expect(() => modelServiceWrapperSet.initWrapper<Foo>({
        type: FOO_MODEL_TYPE
      })).toThrowError(ModelWrapperInitializedError);

    });

  });

  describe('ModelServiceWrapper', () => {

    let modelServiceWrapper: ModelServiceWrapper<Foo>;

    function makeTestReadService(): FooReadService {
      return new FooReadService();
    }

    beforeEach(() => {
      const modelServiceWrapperSet = new ModelServiceWrapperSet();
      modelServiceWrapper = modelServiceWrapperSet.initWrapper<Foo>({
        type: FOO_MODEL_TYPE
      });
    });

    describe('#wrapReadService()', () => {

      it('should create a new ModelReadService', () => {
        const testReadService = makeTestReadService();
        expect(modelServiceWrapper.wrapReadService(testReadService))
          .toBeDefined();
      });

    });

  });

});
