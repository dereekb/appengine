import { OnDestroy, Type, Provider } from '@angular/core';
import { Subscription, BehaviorSubject, Observable, throwError } from 'rxjs';
import { ActionState, HandleActionResult, HandleActionError, ActionFactory, ActionEvent, TypedActionObject } from './action';
import { filter, share, shareReplay } from 'rxjs/operators';
import { SubscriptionObject } from '@gae-web/appengine-utility';

export function ProvideActionDirective<S extends ActionDirective<any>>(directiveType: Type<S>): Provider[] {
  return [{ provide: ActionDirective, useExisting: directiveType }];
}

export abstract class ActionDirective<E extends ActionEvent> extends TypedActionObject<E> { }

/**
 * Abstract component that provides inputs/outputs for some Observable action.
 */
// TODO: Add Angular decorator.
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

  // TODO: Consider restructuring this doAction to only execute once the observable is subscribed to.
  protected doAction<O>(doFn: ActionFactory<O>, success: HandleActionResult<O, E>, error?: HandleActionError): Observable<O> {
    if (this.isWorking) {
      return throwError(new Error('Action is currently running.'));
    } else {
      this.next(this.makeWorkingState());

      const obs = doFn().pipe(shareReplay(1)); // Share latest result so action is not re-executed multiple times.

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

/**
 * Watches an ActionObject for events.
 */
// TODO: Add Angular decorator.
export abstract class AbstractActionWatcherDirective<E extends ActionEvent> implements OnDestroy {

  private _actionSub = new SubscriptionObject();

  constructor(actionObject?: TypedActionObject<E>) {
    if (actionObject) {
      this.setActionObject(actionObject);
    }
  }

  ngOnDestroy() {
    this._actionSub.destroy();
  }

  protected setActionObject(actionObject: TypedActionObject<E>) {
    let subscription: Subscription;

    if (actionObject) {
      subscription = this.makeActionSubscription(actionObject.stream);
    }

    this._actionSub.subscription = subscription;
  }

  protected makeActionSubscription(stream: Observable<E>): Subscription {
    return stream.pipe(
      filter(e => this.filterEvent(e))
    ).subscribe((event) => {
      this.updateForActionEvent(event);
    });
  }

  // MARK: Event
  protected filterEvent(_: E) {
    return true;
  }

  protected abstract updateForActionEvent(event: E);

}

/**
 * Watches an ActionDirective for events.
 */
export abstract class AbstractActionDirectiveWatcherDirective<E extends ActionEvent> extends AbstractActionWatcherDirective<E> {

  private _actionDirective: ActionDirective<E>;

  // MARK: Directive
  get actionDirective() {
    return this._actionDirective;
  }

  set actionDirective(actionDirective) {
    this.setActionDirective(actionDirective);
  }

  protected setActionDirective(actionDirective: ActionDirective<E>) {
    this._actionDirective = actionDirective;
    super.setActionObject(actionDirective);
  }

}
