import { OnDestroy, Type, Provider } from '@angular/core';
import { Subscription, BehaviorSubject, Observable, throwError } from 'rxjs';
import { ActionState, HandleActionResult, HandleActionError, ActionFactory, ActionEvent, TypedActionObject } from './action';
import { filter, share } from 'rxjs/operators';
import { SubscriptionObject } from '@gae-web/appengine-utility';

export function ProvideActionDirective<S extends ActionDirective<any>>(directiveType: Type<S>): Provider[] {
  return [{ provide: ActionDirective, useExisting: directiveType }];
}

export abstract class ActionDirective<E extends ActionEvent> extends TypedActionObject<E> {}

/**
 * Abstract component that provides inputs/outputs for some Observable action.
 */
export abstract class AbstractActionDirective<E extends ActionEvent> extends ActionDirective<E> implements OnDestroy {

  private _sub = new SubscriptionObject();
  private _stream = new BehaviorSubject<E>(this.makeResetState());

  ngOnDestroy() {
    this._stream.complete();
    this._sub.destroy();
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
    return this._sub.hasSubscription;
  }

  // MARK: Stream
  public reset() {
    this._sub.destroy();
    this.next(this.makeResetState());
  }

  protected next(event: E) {
    this._stream.next(event);
  }

  protected doAction<O>(doFn: ActionFactory<O>, success: HandleActionResult<O, E>, error?: HandleActionError): Observable<O> {
    if (this.isWorking) {
      return throwError(new Error('Action is currently running.'));
    } else {
      this.next(this.makeWorkingState());

      const obs = doFn().pipe(share()); // Share result so action is not re-executed multiple times.

      this._sub.subscription = obs.subscribe((r) => {
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
        this._sub.unsub();
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

}

export abstract class AbstractActionWatcherDirective<E extends ActionEvent> implements OnDestroy {

  private _actionSub = new SubscriptionObject();

  constructor() { }

  ngOnDestroy() {
    this._actionSub.destroy();
  }

  protected setActionObject(component: TypedActionObject<E>) {
    this._actionSub.subscription = component.stream.pipe(
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

}
