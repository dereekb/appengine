import { Input, Directive } from '@angular/core';

import { MatSnackBar } from '@angular/material/snack-bar';
import { ActionEvent, ActionState, TypedActionObject } from '../shared/action';
import { AbstractActionWatcherDirective } from '../shared/action.directive';

/**
 * Abstract action directive that uses the snackbar.
 */
export abstract class AbstractActionSnackbarDirective<E extends ActionEvent> extends AbstractActionWatcherDirective<E> {

  constructor(private _snackbar: MatSnackBar) {
    super();
  }

  // MARK: Event
  public filterEvent(event: E) {
    return event.state === ActionState.Complete;
  }

  protected updateWithAction(event: E) {
    this.updateSnackbarWithAction(event, this._snackbar);
  }

  protected abstract updateSnackbarWithAction(event: E, snackbar: MatSnackBar);

}

@Directive({
  selector: '[gaeActionMessageSnackbar]'
})
export class GaeActionMessageSnackbarDirective extends AbstractActionSnackbarDirective<ActionEvent> {

  @Input()
  public snackbarAction = 'Ok!';

  @Input()
  public snackbarMessage = 'Success!';

  @Input()
  public snackbarDuration = 10000;    // 10 seconds.

  constructor(snackbar: MatSnackBar) {
    super(snackbar);
  }

  @Input()
  public set gaeActionMessageSnackbar(component: TypedActionObject<ActionEvent>) {
    this.setActionObject(component);
  }

  @Input()
  public set snackbarSeconds(seconds) {
    this.snackbarDuration = seconds * 1000;
  }

  // MARK: Send/Update
  protected updateSnackbarWithAction(event: ActionEvent, snackbar: MatSnackBar) {
    snackbar.open(this.snackbarMessage, this.snackbarAction, {
      duration: this.snackbarDuration
    });
  }

}
