import { Subscription } from 'rxjs';
import { OneOrMore, ValueUtility } from './value';

/**
 * Object that should be destroyed when usage is complete.
 */
export interface Destroyable {
  destroy();
}

/**
 * Destroyable object that wraps a subscription.
 */
export class SubscriptionObject implements Destroyable {

  private _subscription: Subscription;

  constructor(sub?: Subscription) {
    if (sub) {
      this.setSub(sub);
    }
  }

  public set subscription(sub: Subscription) {
    this.setSub(sub);
  }

  public setSub(sub: Subscription) {
    this.unsub();
    this._subscription = sub;
  }

  public unsub() {
    if (this._subscription) {
      this._subscription.unsubscribe();
      delete this._subscription;
    }
  }

  public destroy() {
    this.unsub();
  }

}

/**
 * Destroyable object that wraps an array of subscriptions.
 * 
 * NOTE: In some cases it might be better to use merge(...[]) and subscribe to a single item.
 */
export class MultiSubscriptionObject implements Destroyable {

  private _subscriptions: Subscription[];

  constructor(subs?: OneOrMore<Subscription>) {
    if (subs) {
      this.setSubs(subs);
    }
  }

  public set subscriptions(subs: OneOrMore<Subscription>) {
    this.setSubs(subs);
  }

  public setSubs(subs: OneOrMore<Subscription>) {
    this.unsub();
    this._subscriptions = ValueUtility.normalizeArray(subs);
  }

  public unsub() {
    if (this._subscriptions) {
      this._subscriptions.forEach(x => x.unsubscribe());
      delete this._subscriptions;
    }
  }

  public destroy() {
    this.unsub();
  }

}
