import { Input, Directive, Optional, Inject } from '@angular/core';
import { AbstractActionWatcherDirective, ActionDirective } from '../shared/action.directive';
import { StateService, StateOrName } from '@uirouter/core';
import { ActionEvent, ActionState, TypedActionObject, ActionObject } from '../shared/action';

@Directive({
  selector: '[gaeActionSegue]',
  exportAs: 'gaeActionSegue'
})
export class GaeActionSegueDirective extends AbstractActionWatcherDirective<ActionEvent> {

  private _to: StateOrName;

  // State to go to.
  constructor(@Optional() @Inject(ActionDirective) actionObject: ActionDirective<ActionEvent>, private _state: StateService) {
    super(actionObject);
  }

  @Input()
  public set gaeActionSegue(to: StateOrName) {
    this._to = to;
  }

  @Input()
  public set action(component: TypedActionObject<ActionEvent>) {
    this.setActionObject(component);
  }

  // MARK: Send/Update
  public filterEvent(event: ActionEvent) {
    return event.state === ActionState.Complete;
  }

  protected updateForActionEvent(_: ActionEvent) {
    this._state.go(this._to);
  }

}
