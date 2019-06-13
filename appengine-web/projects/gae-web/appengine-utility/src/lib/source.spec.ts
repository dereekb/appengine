import 'jasmine-expect';
import { SourceState, AbstractConversionSource, ConversionSourceInputResult } from './source';
import { ModelKey } from './model';
import { of, Observable, throwError, Subject, BehaviorSubject } from 'rxjs';
import { delay, filter, first } from 'rxjs/operators';

describe('ConversionSource', () => {

  describe('simple cases', () => {

    let source: TestConversionSource;

    beforeEach(() => {
      source = new TestConversionSource();
    });
    describe('error', () => {

      describe('via test error state', () => {

        beforeEach(() => {
          source.setTestError();
        });

        it('should enter the error state.', () => {
          expect(source.state).toBe(SourceState.Error);
          expect(source.currentEvent.error).toBeDefined();
        });

      });

      describe('from the source', () => {

        beforeEach(() => {
          source.input = throwError(new Error());
        });

        it('should enter the error state.', (done) => {
          expect(source.state).toBe(SourceState.Error);
          expect(source.currentEvent.error).toBeDefined();
          done();
        });

      });

      describe('from the conversion', () => {

        beforeEach(() => {
          source.testFail = true; // For the test call this first.
          source.input = of([1]); // Then try and convert a non-empty array.
        });

        it('should enter the error state.', (done) => {
          expect(source.state).toBe(SourceState.Error);
          expect(source.currentEvent.error).toBeDefined();
          done();
        });

      });

    });

    describe('loading', () => {

      describe('via test loading state', () => {

        beforeEach(() => {
          source.setTestLoading();
        });

        it('should enter the loading state.', () => {
          expect(source.state).toBe(SourceState.Loading);
        });

      });

      describe('from the source', () => {

        beforeEach(() => {
          source.input = of([1]).pipe(delay(100000));
        });

        it('should enter the loading state.', (done) => {
          expect(source.state).toBe(SourceState.Loading);
          done();
        });

      });

      describe('from the conversion', () => {

        beforeEach(() => {
          source.input = of([1]);
          source.testConversionDelay = 10000;
        });

        it('should enter the loading state.', (done) => {
          expect(source.state).toBe(SourceState.Loading);
          done();
        });

      });

    });

    describe('elements', () => {

      const testInput = [1];
      const testElements = testInput.map(x => String(x));

      describe('via test elements state', () => {

        beforeEach(() => {
          source.setTestElements(testElements);
        });

        it('should have the result elements.', (done) => {
          source.stream.pipe(
            filter((x) => {
              return x.state === SourceState.Done;
            })
          ).subscribe((x) => {
            expect(x.elements).toBeArrayOfStrings();
            done();
          });
        });

      });

      describe('from a finite source', () => {

        beforeEach(() => {
          source.input = of(testInput);
          source.testConversionDelay = 0;
        });

        it('should enter the done state.', (done) => {
          source.stream.pipe(
            filter((x) => {
              return x.state === SourceState.Done;
            })
          ).subscribe((x) => {
            done();
          });
        });

        it('should have the result elements.', (done) => {
          source.stream.pipe(
            filter((x) => {
              return x.state === SourceState.Done;
            })
          ).subscribe((x) => {
            expect(x.elements).toBeArrayOfStrings();
            done();
          });
        });

      });

      describe('from a infinite input source', () => {

        const inputObs = new Subject<number[]>();

        beforeEach(() => {
          source.input = inputObs.asObservable();
          inputObs.next(testInput);
          source.testConversionDelay = 0;
        });

        it('should enter the idle state after elements are loaded.', (done) => {
          source.stream.pipe(
            filter((x) => {
              return x.state === SourceState.Idle;
            })
          ).subscribe((x) => {
            expect(x.elements).toBeArrayOfStrings();
            done();
          });
        });

      });

    });

  });

  describe('complex cases', () => {

    let complexSource: ComplexTestConversionSource;

    describe(`where the input observable completes, but the conversion source does not`, () => {

      beforeEach(() => {
        complexSource = new ComplexTestConversionSource();

        // Manually set the input and subject results.
        complexSource.input = of([1]);
        complexSource.testResultsSubject.next({
          models: ['1']
        });
      });

      it('should be considered done while the source is not loading.', (done) => {

        // Should be loading due to conversion delay, and since initial loading is not complete.
        expect(complexSource.state).toBe(SourceState.Loading);

        // Wait for done.
        complexSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements).toBeArrayOfStrings();
          done();
        });
      });

      it('the conversion should continue to update the stream elements', (done) => {

        // Simulate source failing.
        complexSource.testConversionDelay = 1000; // Delay conversion
        complexSource.testResultsSubject.next({
          models: [],
          failed: [1]
        });

        complexSource.stream.pipe(
          filter((x) => {
            return x.state === SourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.failed).toBeArrayOfNumbers();
          expect(x.elements).toBeEmptyArray();
          done();
        });

      });

    });

  });

});

class TestConversionSource extends AbstractConversionSource<number, string> {

  public testConversionDelay = 500;
  public testFail = false;

  // MARK:
  public setTestElements(elements: string[] = [], newState = SourceState.Done) {
    this.setElements(elements, newState);
  }

  public setTestLoading() {
    this.setLoading();
  }

  public setTestError(error = new Error()) {
    this.setError(error);
  }

  // MARK: AbstractConversionSource
  protected convertInputData(inputData: number[]) {
    let obs;

    if (this.testFail) {
      obs = throwError(new Error());
    } else {
      obs = this._buildTestObs(inputData);
    }

    return obs.pipe(delay(this.testConversionDelay));
  }

  protected _buildTestObs(inputData: number[]) {
    return of({
      models: inputData.map(x => String(x))
    });
  }

}

/**
 * A more complex conversion source that must have its event items sent through the subject.
 *
 * Is used to simulate a more complex internal loader.
 */
class ComplexTestConversionSource extends TestConversionSource {

  public testResultsSubject = new BehaviorSubject<ConversionSourceInputResult<number, string>>({ models: [] });

  // MARK: AbstractConversionSource
  protected _buildTestObs(inputData: number[]) {
    return this.testResultsSubject.asObservable();
  }

}
