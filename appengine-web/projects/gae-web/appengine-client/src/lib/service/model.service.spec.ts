import 'jasmine-expect';
import { Foo, FooReadService } from '../../test/foo.model';
import { FOO_MODEL_TYPE } from '../../test/foo.service';
import { ModelServiceWrapper, ModelServiceWrapperSet, ModelWrapperInitializedError } from './model.service';
import { ReadService, ReadRequest, ReadResponse } from '@gae-web/appengine-api';
import { Observable, of } from 'rxjs';
import { ModelUtility, ValueUtility, NumberModelKey, ModelKey } from '@gae-web/appengine-utility';
import { ModelReadService } from './crud.service';


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

    function makeTestReadService(): ReadService<Foo> {
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

    describe('ModelReadService', () => {

      let testReadService;
      let modelReadService: ModelReadService<Foo>;

      beforeEach(() => {
        testReadService = makeTestReadService();
        modelReadService = modelServiceWrapper.wrapReadService(testReadService);
      });

      it('#type should return the type.', () => {
        expect(modelReadService.type).toBe(FOO_MODEL_TYPE);
      });

      describe('#read()', () => {

        it('should return the requested models.', async () => {
          const testKeys = [1, 2, 3];

          const readResult = await modelReadService.read({
            modelKeys: testKeys
          }).toPromise();

          expect(readResult.failed).toBeEmptyArray();
          expect(readResult.models).toBeNonEmptyArray();

          testKeys.forEach((testKey) => {
            expect(ModelUtility.readModelKeys(readResult.models)).toContain(testKey);
          });

        });

      });

      describe('#continuousRead()', () => {

        it('should return the requested models.', (done) => {

          const testKeys = [1, 2, 3];
          modelReadService.continuousRead({
            modelKeys: testKeys
          }).subscribe((readResult) => {
            expect(readResult.failed).toBeEmptyArray();
            expect(readResult.models).toBeNonEmptyArray();

            testKeys.forEach((testKey) => {
              expect(ModelUtility.readModelKeys(readResult.models)).toContain(testKey);
            });

            done();
          });

        });

        it('should refresh when the cache is modified.', (done) => {

          let updates = 0;
          const testKeys = [1, 2, 3];

          modelReadService.continuousRead({
            modelKeys: testKeys
          }).subscribe((readResult) => {
            updates += 1;

            switch (updates) {
              case 1:
                expect(readResult.models).toBeNonEmptyArray();
                modelServiceWrapper.cache.clear();
                break;
              case 2:
                done();
                break;
            }
          });

        });

      });

    });

  });

});
