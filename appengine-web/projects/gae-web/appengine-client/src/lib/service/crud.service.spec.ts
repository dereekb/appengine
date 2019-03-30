import 'jasmine-expect';
import { ModelServiceWrapper, ModelServiceWrapperSet, ModelWrapperInitializedError } from './model.service';
import { Foo, FOO_MODEL_TYPE, FooReadService, ModelServiceResponse } from '@gae-web/appengine-api';
import { ModelUtility } from '@gae-web/appengine-utility';
import { ModelReadService } from './crud.service';
import { take, takeUntil } from 'rxjs/operators';
import { timer } from 'rxjs';


describe('Crud Model Services', () => {

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

  describe('ModelReadService', () => {

    let testReadService: FooReadService;
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

      it('should return the failed models.', (done) => {
        const testKeys = [1, 2, 3];

        testReadService.filteredKeysSet.add(1);

        modelReadService.read({
          modelKeys: testKeys
        }).subscribe((readResult) => {

          expect(readResult.failed).toContain(1);
          expect(readResult.models).toBeNonEmptyArray();

          done();
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

      it('should refresh when the cache is cleared.', (done) => {

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

      it('should refresh when the models are read again.', (done) => {

        let updates = 0;
        const testKeys = [1, 2, 3];
        const readRequest = {
          modelKeys: testKeys
        };

        modelReadService.continuousRead(readRequest)
          .subscribe((readResult) => {
            updates += 1;

            switch (updates) {
              case 1:
                expect(readResult.models).toBeNonEmptyArray();
                modelReadService.read(readRequest, true).subscribe(); // Skip Cache
                break;
              case 2:
                done();
                break;
            }
          });

      });

      it('should suceed if models are re-read and not found.', (done) => {

        let updates = 0;
        let lastReadResult: ModelServiceResponse<Foo>;

        const unavailableTestKey = 3;
        const unavailableTestKeys = [unavailableTestKey];
        const testKeys = [1, 2].concat(unavailableTestKeys);
        const readRequest = {
          modelKeys: testKeys
        };

        modelReadService.continuousRead(readRequest).pipe(
          takeUntil(timer(300)) // End reading test after arbitrary amount of time.
        ).subscribe((readResult) => {
          updates += 1;
          lastReadResult = readResult;

          switch (updates) {
            case 1:
              expect(readResult.models).toBeNonEmptyArray();
              expect(readResult.failed).toBeEmptyArray();

              // Update read service to not return model.
              testReadService.filteredKeysSet.add(unavailableTestKey);

              // Clear cache to trigger a new read.
              modelServiceWrapper.cache.clear();
              break;
          }
        }).add(() => {
          expect(lastReadResult.failed).not.toBeEmptyArray();
          done();
        });

      });

      it('should suceed if cache is cleared and models are not found on reload.', (done) => {

        let updates = 0;
        let lastReadResult: ModelServiceResponse<Foo>;

        const unavailableTestKey = 3;
        const unavailableTestKeys = [unavailableTestKey];
        const testKeys = [1, 2].concat(unavailableTestKeys);
        const readRequest = {
          modelKeys: testKeys
        };

        modelReadService.continuousRead(readRequest).pipe(
          takeUntil(timer(300)) // End reading test after arbitrary amount of time.
        ).subscribe((readResult) => {
          updates += 1;
          lastReadResult = readResult;

          switch (updates) {
            case 1:
              expect(readResult.models).toBeNonEmptyArray();
              expect(readResult.failed).toBeEmptyArray();

              // Update read service to not return model.
              testReadService.filteredKeysSet.add(unavailableTestKey);

              // Read by skipping the cache.
              modelReadService.read(readRequest, true).subscribe();
              break;
          }
        }).add(() => {
          expect(lastReadResult.failed).not.toBeEmptyArray();
          done();
        });

      });

    });

  });

});
