import { MatDialogRef } from '@angular/material/dialog';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { ActionEvent, ActionState } from './action';
import { AbstractActionDirectiveWatcherDirective } from './action.directive';
import { OnDestroy, Inject, Directive } from '@angular/core';

/**
 * Abstract action dialog for a MatDialogRef.
 *
 * Automatically listens to the action events and once a complete event
 * is recieved this will close itself and return the result.
 */
@Directive()
export abstract class AbstractActionDialogComponent<E extends ActionEvent> extends AbstractActionDirectiveWatcherDirective<E> {

  constructor(@Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<AbstractActionDialogComponent<E>>) {
    super();
  }

  // MARK: Actions
  onCancelClick(): void {
    this.dialogRef.close();
  }

  protected filterEvent(e: E) {
    return e.state === ActionState.Complete;
  }

  protected updateForActionEvent(event: E) {
    this.onActionCompleted(event.result);
  }

  protected onActionCompleted(result: any) {
    this.dialogRef.close(result);
  }

}
