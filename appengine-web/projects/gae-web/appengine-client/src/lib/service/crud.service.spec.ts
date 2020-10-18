import 'jasmine-expect';
import { ModelServiceWrapper, ModelServiceWrapperSet, ModelWrapperInitializedError } from './model.service';
import { TestFoo, TEST_FOO_MODEL_TYPE, TestFooReadService, ModelServiceResponse, ClientAtomicOperationError } from '@gae-web/appengine-api';
import { ModelUtility } from '@gae-web/appengine-utility';
import { ModelReadService, getModelsThrowReadResponseError } from './crud.service';
import { take, takeUntil, first, mergeMap } from 'rxjs/operators';
import { timer, throwError } from 'rxjs';


describe('Crud Model Services', () => {

  let modelServiceWrapper: ModelServiceWrapper<TestFoo>;

  function makeTestReadService(): TestFooReadService {
    return new TestFooReadService();
  }

  beforeEach(() => {
    const modelServiceWrapperSet = new ModelServiceWrapperSet();
    modelServiceWrapper = modelServiceWrapperSet.initWrapper<TestFoo>({
      type: TEST_FOO_MODEL_TYPE
    });
  });

  describe('ModelReadService', () => {

    let testReadService: TestFooReadService;
    let modelReadService: ModelReadService<TestFoo>;

    beforeEach(() => {
      testReadService = makeTestReadService();
      modelReadService = modelServiceWrapper.wrapReadService(testReadService);
    });

    it('#type should return the type.', () => {
      expect(modelReadService.type).toBe(TEST_FOO_MODEL_TYPE);
    });

    describe('#read()', () => {

      describe('on generic error', () => {

        const testKeys = [1, 2, 3];

        beforeEach(() => {
          testReadService.customReadDelegate = {
            handleRead: () => {
              return throwError(new Error('Test error.'));
            }
          };
        });

        it('should throw the error.', (done) => {
          modelReadService.read({
            modelKeys: testKeys
          }).subscribe({
            next: () => {
              fail('Should not have succeeeded.');
              done();
            },
            complete: () => {
              fail('Should not have completed.');
              done();
            },
            error: () => {
              done();
            }
          });

        });

      });

      it('should return the requested models.', (done) => {
        const testKeys = [1, 2, 3];

        modelReadService.read({
          modelKeys: testKeys
        }).pipe(
          first()
        ).subscribe((readResult) => {

          expect(readResult.failed).toBeEmptyArray();
          expect(readResult.models).toBeNonEmptyArray();

          testKeys.forEach((testKey) => {
            expect(ModelUtility.readModelKeys(readResult.models)).toContain(testKey);
          });

          done();
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

    describe('#quickContinuousReadOne()', () => {

      const testKey = 1;

      describe('on error', () => {

        beforeEach(() => {
          testReadService.customReadDelegate = {
            handleRead: () => {
              return throwError(new Error('Test error.'));
            }
          };
        });

        it('should return undefined.', (done) => {
          modelReadService.quickContinuousReadOne(testKey).subscribe({
            next: (result) => {
              expect(result).toBeUndefined();
              done();
            },
            complete: () => {
              fail('Should not have completed yet.');
              done();
            },
            error: () => {
              fail('Should not have encountered the error.');
              done();
            }
          });

        });

      });

    });

    describe('#quickContinuousRead()', () => {

      const testKeys = [1, 2, 3];

      describe('on error', () => {

        beforeEach(() => {
          testReadService.customReadDelegate = {
            handleRead: () => {
              return throwError(new Error('Test error.'));
            }
          };
        });

        it('should return undefined.', (done) => {
          modelReadService.quickContinuousRead(testKeys).subscribe({
            next: (result) => {
              expect(result).toBeEmptyArray();
              done();
            },
            complete: () => {
              fail('Should not have completed yet.');
              done();
            },
            error: () => {
              fail('Should not have encountered the error.');
              done();
            }
          });

        });

      });

    });

    describe('#continuousRead()', () => {

      describe('on generic error', () => {

        const testKeys = [1, 2, 3];

        beforeEach(() => {
          testReadService.customReadDelegate = {
            handleRead: () => {
              return throwError(new Error('Test error.'));
            }
          };
        });

        describe('lambda', () => {

          describe('#getModelsThrowReadResponseError', () => {

            it('should throw the error.', (done) => {

              modelReadService.continuousRead({
                modelKeys: testKeys
              }).pipe(
                mergeMap(getModelsThrowReadResponseError)
              ).subscribe({
                next: (readResult) => {
                  fail('Should have failed.');
                  done();
                },
                complete: () => {
                  fail('Should not have completed yet.');
                  done();
                },
                error: () => {
                  done();
                }
              });
            });

          });

        });

        it('should return the error.', (done) => {
          modelReadService.continuousRead({
            modelKeys: testKeys
          }).subscribe({
            next: (readResult) => {

              expect(readResult.error).toBeDefined();

              // Should contain all error keys.
              testKeys.forEach((testKey) => {
                expect(readResult.failed).toContain(testKey);
              });

              done();
            },
            complete: () => {
              fail('Should not have completed yet.');
              done();
            },
            error: () => {
              fail('Should not have encountered the error.');
              done();
            }
          });

        });

        it('should continue reading despite the error.', (done) => {

          let updates = 0;
          const debounce = 10;

          modelReadService.continuousRead({
            modelKeys: testKeys,
          }, debounce).subscribe({
            next: (readResult) => {
              updates += 1;

              switch (updates) {
                case 1:

                  expect(readResult.error).toBeDefined();

                  // Trigger new read through cache.
                  setTimeout(() => {
                    modelReadService.clearFromCache(testKeys);
                  }, debounce * 4);

                  break;
                case 2:
                  done();
                  break;
              }

            },
            complete: () => {
              if (updates < 2) {
                fail('Should not have completed yet.');
              }

              done();
            },
            error: () => {
              fail('Should not have encountered the error.');
              done();
            }
          });

        });

      });

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

              setTimeout(() => {
                modelServiceWrapper.cache.clear();
              }, 50);

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

                setTimeout(() => {
                  modelReadService.read(readRequest, true).subscribe(); // Skip Cache
                }, 50);
                break;
              case 2:
                done();
                break;
            }
          });

      });

      it('should succeed if models are re-read and not found.', (done) => {

        let updates = 0;
        let lastReadResult: ModelServiceResponse<TestFoo>;

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

              setTimeout(() => {

                // Update read service to not return model.
                testReadService.filteredKeysSet.add(unavailableTestKey);

                // Clear cache to trigger a new read.
                modelServiceWrapper.cache.clear();
              }, 50);
              break;
            case 2:
              expect(lastReadResult.failed).not.toBeEmptyArray();
              done();
              break;
          }
        });

      });

      it('should succeed if cache is cleared and models are not found on reload.', (done) => {

        let updates = 0;
        let lastReadResult: ModelServiceResponse<TestFoo>;

        const unavailableTestKey = 3;
        const unavailableTestKeys = [unavailableTestKey];
        const testKeys = [1, 2].concat(unavailableTestKeys);
        const readRequest = {
          modelKeys: testKeys
        };

        modelReadService.continuousRead(readRequest).pipe(
          // takeUntil(timer(600)) // End reading test after arbitrary amount of time.
        ).subscribe({

          next: (readResult) => {
            updates += 1;
            lastReadResult = readResult;

            switch (updates) {
              case 1:
                expect(readResult.failed).toBeEmptyArray();
                expect(readResult.models).not.toBeEmptyArray();

                setTimeout(() => {

                  // Update read service to not return model.
                  testReadService.filteredKeysSet.add(unavailableTestKey);

                  // Read by skipping the cache.
                  modelReadService.read(readRequest, true).subscribe();

                }, 20);

                break;
              case 2:
                expect(lastReadResult.failed).not.toBeEmptyArray();
                done();
                break;

            }
          },
          complete: () => {
            if (updates < 2) {
              fail('Should not have completed yet.');
            }

            done();
          },
          error: () => {
            fail('Should not have encountered the error.');
            done();
          }
        });

      });

    });

  });

});
