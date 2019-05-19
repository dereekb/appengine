import 'jasmine-expect';
import { TestFoo } from '@gae-web/appengine-api';
import { MergedReadQuerySource, ReadSource } from './source';
import { SourceState } from '@gae-web/appengine-utility';
import { filter, first } from 'rxjs/operators';
import { TestFooTestReadSourceFactory, TestFooTestKeyQuerySource } from '../../test/foo.testing';

describe('Source', () => {

  // TODO: Do KeyQuerySource

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


  });

  describe('MergedReadQuerySource', () => {

    let mergedReadQuerySource: MergedReadQuerySource<TestFoo>;
    let testReadSource: ReadSource<TestFoo>;
    let testQuerySource: TestFooTestKeyQuerySource;

    beforeEach(() => {
      testReadSource = TestFooTestReadSourceFactory.makeReadSource();
      testQuerySource = new TestFooTestKeyQuerySource();
      mergedReadQuerySource = new MergedReadQuerySource<TestFoo>(testReadSource, testQuerySource);
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
        mergedReadQuerySource.stream.pipe(
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
        mergedReadQuerySource.stream.pipe(
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
          mergedReadQuerySource.stream.pipe()
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
          mergedReadQuerySource.reset();
          mergedReadQuerySource.next();
        });
      });

    });

  });

});
