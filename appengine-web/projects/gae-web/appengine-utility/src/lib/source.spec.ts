import 'jasmine-expect';
import { SourceState, AbstractConversionSource, ConversionSourceInputResult } from './source';
import { ModelKey } from './model';
import { of, Observable, throwError, Subject } from 'rxjs';
import { delay, filter } from 'rxjs/operators';

describe('ConversionSource', () => {
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
      });

    });

    describe('from the source', () => {

      beforeEach(() => {
        source.input = throwError(new Error());
      });

      it('should enter the error state.', (done) => {
        expect(source.state).toBe(SourceState.Error);
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

    describe('from a infinite source', () => {

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

class TestConversionSource extends AbstractConversionSource<number, string> {

  public testConversionDelay = 1000;
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
      obs = of({
        models: inputData.map(x => String(x))
      });
    }

    return obs.pipe(delay(this.testConversionDelay));
  }

}
