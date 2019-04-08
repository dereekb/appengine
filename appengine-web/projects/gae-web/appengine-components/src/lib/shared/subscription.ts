import { OnDestroy } from '@angular/core';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { Subscription } from 'rxjs';

/**
 * Abstract component that contains a SubscriptionObject and will clean it up automatically.
 */
export abstract class AbstractSubscriptionComponent implements OnDestroy {

  private _subscriptionObject = new SubscriptionObject();

  ngOnDestroy() {
    this._subscriptionObject.destroy();
  }

  protected set sub(subscription: Subscription) {
    this._subscriptionObject.subscription = subscription;
  }

}
