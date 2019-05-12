import 'jasmine-expect';
import { TestFoo, TestFooReadService, TestQueryService } from '@gae-web/appengine-api';
import { MergedReadQuerySource, ReadSource, ReadSourceFactory, KeyQuerySource, QuerySourceConfiguration } from './source';
import { SourceState } from '@gae-web/appengine-utility';
import { filter, first } from 'rxjs/operators';

describe('Source', () => {

  // TODO: Do KeyQuerySource

  describe('MergedReadQuerySource', () => {

    const testKeyResults = [1, 2, 3, 4];

    let mergedReadQuerySource: MergedReadQuerySource<TestFoo>;
    let testReadSource: ReadSource<TestFoo>;
    let testQuerySource: TestFooKeyQuerySource;

    beforeEach(() => {
      testReadSource = TestFooReadSourceFactory.makeReadSource();
      testQuerySource = new TestFooKeyQuerySource();

      testQuerySource.testQueryService.keyResults = testKeyResults;

      mergedReadQuerySource = new MergedReadQuerySource<TestFoo>(testReadSource, testQuerySource);
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

export class TestFooReadSourceFactory extends ReadSourceFactory<TestFoo> {

  constructor(public readonly testReadService = new TestFooReadService()) {
    super(testReadService);
  }

  public static makeReadSource(): ReadSource<TestFoo> {
    return new TestFooReadSourceFactory().makeSource();
  }

}

export class TestFooKeyQuerySource extends KeyQuerySource<TestFoo> {

  constructor(public readonly testQueryService = new TestQueryService<TestFoo>(), config?: QuerySourceConfiguration) {
    super(testQueryService, config);
  }

}
