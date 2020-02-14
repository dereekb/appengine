import 'jasmine-expect';
import { TestFoo, TestFooReadService } from '@gae-web/appengine-api';
import {
  MergedReadIterateSource, ReadSource, CachedKeySource, CachedKeySourceCache,
  CachedKeySourceCacheFactory, KeyedPredictiveOrderedQueryStream, KeyedPredictiveOrderedQueryStreamEvent
} from './source';
import { SourceState, SourceEvent, ModelKey } from '@gae-web/appengine-utility';
import { filter, first } from 'rxjs/operators';
import { TestFooTestReadSourceFactory, TestFooTestKeyQuerySource } from '../../test/foo.testing';
import { of, BehaviorSubject, Subject } from 'rxjs';
import { TestFooCachedKeySourceCache } from '../../test/foo.service';
import { KeyedPredictiveOrderedQueryDelegate } from './search.service';

describe('Source', () => {

  describe('ReadSource', () => {

    let testReadSource: ReadSource<TestFoo>;
    let testReadService: TestFooReadService;

    beforeEach(() => {
      testReadSource = TestFooTestReadSourceFactory.makeReadSource();
      testReadService = (testReadSource as any)._service as TestFooReadService;
    });

    describe('with empty input', () => {

      beforeEach(() => {
        testReadSource.input = of([]);
      });

      it('should hit done state', (done) => {

        // Wait for the stream to complete.
        testReadSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements.length).toBe(0);
          done();
        });

      });

      it('should update if the input changes', (done) => {

        // Wait for the stream to complete.
        testReadSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe(() => {
          const items = [1, 2, 3];

          testReadSource.input = of(items);

          // Wait for the stream to complete with the new items.
          testReadSource.stream.pipe(
            filter((x) => {
              return x.state === SourceState.Done;
            }),
            first()
          ).subscribe((x) => {
            expect(x.elements.length).toBe(items.length);
            done();
          });
        });

      });

    });

    describe('with only unavailable content', () => {

      const FAILED_KEY = 1;
      const TEST_KEYS = [FAILED_KEY];

      beforeEach(() => {
        testReadService.filteredKeysSet.add(FAILED_KEY);
      });

      it('should succeed with the unavailable items in fail.', (done) => {
        testReadSource.input = of(TEST_KEYS);

        // Wait for the stream to complete.
        testReadSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements).toBeEmptyArray();
          expect(x.failed).toContain(FAILED_KEY);
          done();
        });

      });

    });

    describe('with input with keys', () => {

      it('should hit the loading state.', (done) => {

        testReadService.loadingTime = 100000; // Simulate loading time.

        const testInputA = [3, 4, 5];

        testReadSource.input = of(testInputA);

        // Wait for the initial stream to complete.
        testReadSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Loading;
          }),
          first()
        ).subscribe((x) => {
          expect(x.state).toBe(SourceState.Loading);
          done();
        });

      });

    });

    describe('changing input', () => {

      it('should load the new items', (done) => {

        const testInputA = [];
        const testInputB = [3, 4, 5];

        testReadSource.input = of(testInputA);

        // Wait for the initial stream to complete.
        testReadSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements.length).toBe(0);

          testReadSource.input = of(testInputB);

          testReadSource.stream.pipe(
            filter((y) => {
              return y.state === SourceState.Done;
            }),
            first()
          ).subscribe((y) => {
            expect(y.elements.length).toBe(testInputB.length);
            done();
          });
        });
      });

    });

  });

  describe('KeyQuerySource', () => {

    let testQuerySource: TestFooTestKeyQuerySource;

    beforeEach(() => {
      testQuerySource = new TestFooTestKeyQuerySource();
    });

    describe('with no query results', () => {
      const testKeyResults = [];

      beforeEach(() => {
        testQuerySource.testQueryService.keyResults = testKeyResults;
      });

      it('should hit done state when the query returns nothing.', (done) => {

        // Load next elements.
        testQuerySource.next();

        // Wait for the stream to complete.
        testQuerySource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements.length).toBe(0);
          done();
        });
      });

    });

    describe('with query results', () => {
      const testKeyResults = [1, 2, 3, 4, 5];

      beforeEach(() => {
        testQuerySource.testQueryService.keyResults = testKeyResults;
      });

      it('should hit done state when the query returns items less than the limit.', (done) => {

        // Load next elements.
        testQuerySource.next();

        // Wait for the stream to complete.
        testQuerySource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements.length).toBe(testKeyResults.length);
          done();
        });
      });

      it('should send messages to all listeners.', (done) => {

        const eventsA = [];
        const eventsB = [];

        // Wait for the stream to complete.
        testQuerySource.stream.subscribe((x) => {
          eventsA.push(x);
        });

        testQuerySource.stream.subscribe((x) => {
          eventsB.push(x);
        });

        // Load next elements.
        testQuerySource.next();

        // Wait for the stream to complete.
        testQuerySource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(eventsA.length).toBe(eventsB.length);
          done();
        });

      });

      it('should send elements to.', (done) => {

        const eventsA = [];
        const eventsB = [];

        // Subscribe to elements.
        testQuerySource.elements.subscribe((x) => {
          eventsA.push(x);
        });

        testQuerySource.elements.subscribe((x) => {
          eventsB.push(x);
        });

        // Load next elements.
        testQuerySource.next();

        // Wait for the stream to complete.
        testQuerySource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(eventsA.length).toBe(eventsB.length);
          done();
        });

      });

      describe('nextUntil()', () => {
        // TODO: ...
      });

    });


  });

  describe('MergedReadIterateSource', () => {

    let mergedReadIterateSource: MergedReadIterateSource<TestFoo>;
    let testReadSource: ReadSource<TestFoo>;
    let testQuerySource: TestFooTestKeyQuerySource;

    beforeEach(() => {
      testReadSource = TestFooTestReadSourceFactory.makeReadSource();
      testQuerySource = new TestFooTestKeyQuerySource();
      mergedReadIterateSource = new MergedReadIterateSource<TestFoo>(testReadSource, testQuerySource);
    });

    describe('with no query results', () => {
      const testKeyResults = [];

      beforeEach(() => {
        testQuerySource.testQueryService.keyResults = testKeyResults;
      });

      it('should hit done state when the query returns nothing.', (done) => {

        // Load next elements.
        testQuerySource.next();

        // Wait for the stream to complete.
        mergedReadIterateSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements.length).toBe(0);
          done();
        });
      });

    });

    describe('with query results', () => {
      const testKeyResults = [1, 2, 3, 4];

      beforeEach(() => {
        testQuerySource.testQueryService.keyResults = testKeyResults;
      });

      it(`it should not get stuck in a loading state if both sources are done loading and reset is called.`, (done) => {

        // No loading should have occured yet on either source.
        expect(testQuerySource.state).toBe(SourceState.Reset);
        expect(testReadSource.state).toBe(SourceState.Loading);

        // Load next elements.
        testQuerySource.next();

        // Wait for the stream to complete.
        mergedReadIterateSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((firstSourceEvent) => {
          // NOTE: Extra test. Not really relevant.
          expect(firstSourceEvent.elements.length).toBe(testKeyResults.length);

          // Reset and load again.
          let hitLoading = false;
          let hitDone = false;
          mergedReadIterateSource.stream.pipe()
            .subscribe((sourceEvent) => {

              switch (sourceEvent.state) {
                case SourceState.Done:
                  hitDone = true;
                  break;
                case SourceState.Loading:
                  hitLoading = true;
                  break;
              }

              if (hitLoading && hitDone) {
                done();
              }
            });

          // TODO: Reset should potentially also directly call next here?
          mergedReadIterateSource.reset();
          mergedReadIterateSource.next();
        });
      });

    });

  });

  describe('CachedKeySource', () => {

    let testQuerySource: TestFooTestKeyQuerySource;
    let testCachedKeySourceCache: CachedKeySourceCache<TestFoo>;
    let testCachedKeySource: CachedKeySource<TestFoo>;

    let eventStream: Subject<KeyedPredictiveOrderedQueryStreamEvent>;
    let stream: KeyedPredictiveOrderedQueryStream;

    beforeEach(() => {
      testQuerySource = new TestFooTestKeyQuerySource();
      eventStream = new Subject<KeyedPredictiveOrderedQueryStreamEvent>();

      stream = {
        delegateStream: eventStream
      };

      testCachedKeySourceCache = new CachedKeySourceCache<TestFoo>(testQuerySource, stream);
      testCachedKeySource = new CachedKeySource(testCachedKeySourceCache);
    });

    describe('with query results', () => {
      const testKeyResults = [1, 2, 3, 4];

      beforeEach(() => {
        testQuerySource.testQueryService.keyResults = testKeyResults;
      });

      describe('reset()', () => {

        describe('autoNextOnReset is true', () => {

          beforeEach(() => {
            testCachedKeySource.autoNextOnReset = true;
          });

          it('should call next() automatically when reset.', (done) => {
            testCachedKeySource.reset();
            testCachedKeySource.stream.pipe(filter(x => x.state === SourceState.Done)).subscribe((x) => {
              expect(x.elements).toBeArrayOfNumbers();
              done();
            });
          });

        });

        it('should not throw an error when initial is called after being reset.', (done) => {
          testCachedKeySource.reset();
          testCachedKeySource.initial().then(() => {
            done();
          });
        });

      });

      describe('next()', () => {

        it('it should not emit loading when already in the done state.', (done) => {

          // Call initial next.
          testCachedKeySource.initial().then((initialResult) => {
            expect(initialResult).toBeArrayOfNumbers();

            // Call next again.
            testCachedKeySource.next().finally(() => {
              expect(testCachedKeySource.state).toBe(SourceState.Done);

              const states: SourceEvent<ModelKey>[] = [];

              testCachedKeySource.stream.subscribe((x) => {
                states.push(x);
              });

              // Call next several times back-to-back
              testCachedKeySource.next().catch(() => undefined).finally(() =>
                testCachedKeySource.next().catch(() => undefined).finally(() =>
                  testCachedKeySource.next().catch(() => undefined)
                )
              );

              testCachedKeySource.next().then(() => {
                fail('Should have rejected as no new elements to load.');

                done();
              }, () => {
                expect(states.length).toBe(1);
                expect(states[0].state).toBe(SourceState.Done);
                expect(states[0].elements).toBeArrayOfNumbers();

                done();
              });
            });

          });

        });

      });

    });

  });

});
