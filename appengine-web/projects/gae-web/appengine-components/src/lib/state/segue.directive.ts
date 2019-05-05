import { Input, Directive } from '@angular/core';
import { AbstractActionWatcherDirective } from '../shared/action.directive';
import { StateService } from '@uirouter/core';
import { ActionEvent, ActionState, TypedActionObject } from '../shared/action';

@Directive({
  selector: '[gaeActionSegue]'
})
export class GaeActionSegueDirective extends AbstractActionWatcherDirective<ActionEvent> {

  // State to go to.
  @Input()
  public actionSegueRef: string;

  constructor(private _state: StateService) {
    super();
  }

  @Input()
  public set gaeActionSegue(component: TypedActionObject<ActionEvent>) {
    this.setActionObject(component);
  }

  // MARK: Send/Update
  public filterEvent(event: ActionEvent) {
    return event.state === ActionState.Complete;
  }

  protected updateWithAction(_: ActionEvent) {
    this._state.go(this.actionSegueRef);
  }

}
