import { ActionEvent, TypedActionObject, ActionState } from '../shared/action';
import { AbstractActionWatcherDirective } from '../shared/action.directive';
import { OnInit } from '@angular/core';
import { AnalyticsSender } from '@gae-web/appengine-analytics';

/**
 * Abstract directive that signals an analytics event when an action occurs.
 */
export abstract class AbstractActionAnalyticDirective<E extends ActionEvent> extends AbstractActionWatcherDirective<E> implements OnInit {

  constructor(private _action: TypedActionObject<E>, private _analytics: AnalyticsSender) {
    super();
  }

  ngOnInit() {
    this.setActionObject(this._action);
  }

  // MARK: Anayltics
  public filterEvent(event: E) {
    return event.state === ActionState.Complete;
  }

  protected updateWithAction(event: E) {
    this.updateAnalyticsWithAction(event, this._analytics);
  }

  protected abstract updateAnalyticsWithAction(event: E, analytics: AnalyticsSender);

}
