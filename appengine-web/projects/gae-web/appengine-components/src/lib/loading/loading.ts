import { BehaviorSubject } from 'rxjs';

export interface LoadingEvent {
  loading?: boolean;
  error?: any;
}

/**
 * Utility object that helps control a loadingViewComponent.
 */
export class LoadingViewSubject {

  private _subject;
  private _error: any;

  constructor(private readonly _checkDoneFunction?: () => any[], loading: boolean = true) {
    this._subject = new BehaviorSubject<LoadingEvent>({ loading });
  }

  /**
   * Check the array for objects to see if loading is completed.
   *
   * The loading state is always modified unless there is an error or no check function.
   */
  public check() {
    if (!this.hasError()) {
      if (this._checkDoneFunction) {
        const checkArray = this._checkDoneFunction();
        let isLoading = true;

        if (checkArray.length > 0) {
          const checkResult = checkArray.filter((x) => x === undefined);  // If any are undefined, still loading.
          isLoading = (checkResult.length > 0);
        }

        this.setLoading(isLoading);
      } else {
        throw new Error('Attempted to check without a check function set.');
      }
    }
  }

  public get loading(): boolean {
    return this._subject.value.loading;
  }

  public hasError(): boolean {
    return Boolean(this._error);
  }

  public clearError() {
    delete this._error;
  }

  public get obs() {
    return this._subject.asObservable();
  }

  public setLoading(loading: boolean = true) {
    this._subject.next({
      loading
    });
  }

  public setError(error: any, loading: boolean = false) {
    this._subject.next({
      loading,
      error
    });
  }

}
