import { OnDestroy } from '@angular/core';
import { Subscription, BehaviorSubject, Observable } from 'rxjs';
import { ActionState, HandleActionResult, HandleActionError, ActionFactory, ActionEvent, TypedActionObject } from './action';
import { filter, share } from 'rxjs/operators';

/**
 * Abstract component that provides inputs/outputs for some Observable action.
 */
export abstract class AbstractActionDirective<E extends ActionEvent> implements TypedActionObject<E>, OnDestroy {

  private _sub?: Subscription;
  private _stream = new BehaviorSubject<E>(this.makeResetState());

  constructor() { }

  ngOnDestroy() {
    this._stream.complete();
    this._resetSub();
  }

  get stream(): Observable<E> {
    return this._stream;
  }

  get state(): ActionState {
    return this._stream.value.state;
  }

  get canWork() {
    return this.state !== ActionState.Working;
  }

  get isWorking() {
    return Boolean(this._sub);
  }

  // MARK: Stream
  public reset() {
    this._resetSub();
    this.next(this.makeResetState());
  }

  protected next(event: E) {
    this._stream.next(event);
  }

  protected doAction<O>(doFn: ActionFactory<O>, success: HandleActionResult<O, E>, error?: HandleActionError): Observable<O> {
    if (this.isWorking) {
      return Observable.throw(new Error('Action is currently running.'));
    } else {
      this.next(this.makeWorkingState());

      const obs = doFn().pipe(share()); // Share result so action is not re-executed multiple times.

      this._sub = obs.subscribe((r) => {
        const completedState = success(r);
        this.next(completedState);
      }, (e) => {
        const recovery: any = (error) ? error(e) : this.defaultHandleError(e);

        if (recovery) {
          return recovery;
        } else {
          throw e;    // Rethrow the error.
        }
      }).add(() => {
        this._resetSub();
      });

      return obs;
    }
  }

  protected makeResetState(): E {
    return {
      state: ActionState.Reset
    } as E;
  }

  protected makeWorkingState(): E {
    return {
      state: ActionState.Working
    } as E;
  }

  protected defaultHandleError(error: any): void {
    const errorState = {
      state: ActionState.Error,
      error
    } as E;

    this.next(errorState);
  }

  private _resetSub() {
    if (this._sub) {
      this._sub.unsubscribe();
      delete this._sub;
    }
  }

}

export abstract class AbstractActionWatcherDirective<E extends ActionEvent> implements OnDestroy {

  private _actionSub: Subscription;

  constructor() { }

  ngOnDestroy() {
    if (this._actionSub) {
      this._actionSub.unsubscribe();
      delete this._actionSub;
    }
  }

  protected setActionObject(component: TypedActionObject<E>) {
    this._clearActionSub();

    this._actionSub = component.stream.pipe(
      filter(e => this.filterEvent(e))
    )
      .subscribe((event) => {
        this.updateWithAction(event);
      });
  }

  // MARK: Event
  public filterEvent(_: E) {
    return true;
  }

  protected abstract updateWithAction(event: E);

  // MARK: Internal
  private _clearActionSub() {
    if (this._actionSub) {
      this._actionSub.unsubscribe();
      delete this._actionSub;
    }
  }

}
