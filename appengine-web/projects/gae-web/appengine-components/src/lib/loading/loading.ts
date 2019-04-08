import { BehaviorSubject, Observable } from 'rxjs';

export interface LoadingEvent {
  isLoading?: boolean;
  error?: any;
}

export type LoadingContextCheckCompletionFunction = () => any[];

export interface LoadingContextConfiguration {
  isLoading?: boolean;
  checkDone?: LoadingContextCheckCompletionFunction;
}

export interface LoadingContext {
  readonly isLoading: boolean;
  readonly stream: Observable<LoadingEvent>;
}

/**
 * Simple LoadingContext implementation
 */
export class SimpleLoadingContext implements LoadingContext {

  private _subject: BehaviorSubject<LoadingEvent>;
  private _error: any;

  constructor(isLoading = true) {
    this._subject = new BehaviorSubject<LoadingEvent>({ isLoading });
  }

  public get isLoading(): boolean {
    return this._subject.value.isLoading;
  }

  public hasError(): boolean {
    return Boolean(this._error);
  }

  public clearError() {
    delete this._error;
  }

  public get stream() {
    return this._subject.asObservable();
  }

  public setLoading(isLoading: boolean = true) {
    this._subject.next({
      isLoading
    });
  }

  public setError(error: any, isLoading: boolean = false) {
    this._subject.next({
      isLoading,
      error
    });
  }
}

/**
 * Utility object for maintaining a loading stream. Is triggered into isLoading, then can be triggered again to see if elements have all completed isLoading or not.
 */
export class ValuesLoadingContext extends SimpleLoadingContext {

  private _checkDone?: LoadingContextCheckCompletionFunction;

  constructor({ checkDone, isLoading = true }: LoadingContextConfiguration = {}) {
    super(isLoading);
    this._checkDone = checkDone;
  }

  /**
   * Check the array for objects to see if isLoading is completed.
   *
   * The isLoading state is always modified unless there is an error or no check function.
   */
  public check() {
    if (!this.hasError()) {
      if (this._checkDone) {
        const checkArray = this._checkDone();
        let isLoading = true;

        if (checkArray.length > 0) {
          const checkResult = checkArray.filter((x) => x === undefined);  // If any are undefined, still isLoading.
          isLoading = (checkResult.length > 0);
        }

        this.setLoading(isLoading);
      } else {
        throw new Error('Attempted to check without a check function set.');
      }
    }
  }

}
