import { Subscription } from 'rxjs';

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
