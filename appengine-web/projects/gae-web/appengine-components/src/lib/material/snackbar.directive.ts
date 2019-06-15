import { Input, Directive, Host, Inject, Optional, OnInit } from '@angular/core';

import { MatSnackBar, MatSnackBarRef } from '@angular/material/snack-bar';
import { ActionEvent, ActionState, TypedActionObject } from '../shared/action';
import { AbstractActionWatcherDirective, ActionDirective } from '../shared/action.directive';

/**
 * Abstract action directive that uses the Material Snackbar.
 */
export abstract class AbstractActionSnackbarDirective<E extends ActionEvent> extends AbstractActionWatcherDirective<E> {

  constructor(@Inject(MatSnackBar) private _snackbar: MatSnackBar) {
    super();
  }

  // MARK: Event
  public filterEvent(event: E) {
    return event.state === ActionState.Complete;  // Only execute when the action completes.
  }

  protected updateForActionEvent(event: E) {
    this.updateSnackbarWithAction(this._snackbar, event);
  }

  protected abstract updateSnackbarWithAction(snackbar: MatSnackBar, event: E);

}

/**
 * Delegate for a GaeActionSnackbarDirective.
 */
export interface GaeActionSnackbarDelegate<E extends ActionEvent> {
  openSnackbar(snackbar: MatSnackBar, event: E): MatSnackBarRef<any> | undefined;
}

/**
 * Directive that listens to an ActionObject/ActionDirective and shows a snackbar based on it's delegate's configuration.
 */
@Directive({
  selector: '[gaeActionSnackbar]',
  exportAs: 'gaeActionSnackbar'
})
export class GaeActionSnackbarDirective<E extends ActionEvent> extends AbstractActionSnackbarDirective<E> {

  private _snackbarRef: MatSnackBarRef<any>;

  @Input()
  public delegate: GaeActionSnackbarDelegate<E>;

  constructor(@Optional() @Inject(ActionDirective) actionDirective: ActionDirective<E>, @Inject(MatSnackBar) snackbar: MatSnackBar) {
    super(snackbar);

    if (actionDirective) {
      this.setActionObject(actionDirective);
    }
  }

  // MARK: Send/Update
  protected updateSnackbarWithAction(snackbar: MatSnackBar, event: E) {
    const snackbarRef = this.delegate.openSnackbar(snackbar, event);
    this.setSnackbarRef(snackbarRef);
  }

  protected setSnackbarRef(ref: MatSnackBarRef<any>) {
    this._snackbarRef = ref;

    if (ref) {
      ref.afterDismissed().subscribe(() => {
        if (this._snackbarRef === ref) {
          this._snackbarRef = undefined;
        }
      });
    }
  }

}

/**
 * Pre-configured GaeActionSnackbarDirective that listens to an ActionObject/ActionDirective and shows a simple message.
 */
@Directive({
  selector: '[gaeActionSnackbarMessage]',
  exportAs: 'gaeActionSnackbarMessage'
})
export class GaeActionSnackbarMessageDirective extends GaeActionSnackbarDirective<ActionEvent> implements OnInit, GaeActionSnackbarDelegate<ActionEvent> {

  @Input()
  public snackbarAction = 'Ok!';

  @Input()
  public snackbarMessage = 'Success!';

  @Input()
  public set gaeActionSnackbarMessage(snackbarMessage) {
    this.snackbarMessage = snackbarMessage;
  }

  @Input()
  public snackbarDuration = 10000;    // 10 seconds.

  @Input()
  public set snackbarSeconds(seconds) {
    this.snackbarDuration = seconds * 1000;
  }

  ngOnInit() {
    this.delegate = this;
  }

  // MARK: Send/Update
  public openSnackbar(snackbar: MatSnackBar, _: ActionEvent): MatSnackBarRef<any> | undefined {
    return snackbar.open(this.snackbarMessage, this.snackbarAction, {
      duration: this.snackbarDuration
    });
  }

}
