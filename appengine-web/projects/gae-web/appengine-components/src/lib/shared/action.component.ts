import { MatDialogRef } from '@angular/material';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { ActionEvent, ActionState } from './action';
import { AbstractActionDirective, ActionDirective } from './action.directive';
import { filter } from 'rxjs/operators';
import { OnDestroy } from '@angular/core';

/**
 * Abstract action dialog for a MatDialogRef.
 *
 * Automatically listens to the action events and once a complete event
 * is recieved this will close itself and return the result.
 */
export abstract class AbstractActionDialogCompoment<E extends ActionEvent> implements OnDestroy {

  private _actionDirective: ActionDirective<E>;
  private _actionSub = new SubscriptionObject();

  constructor(public readonly dialogRef: MatDialogRef<AbstractActionDialogCompoment<E>>) { }

  ngOnDestroy() {
    this._actionSub.destroy();
  }

  // MARK: Directive
  get directive() {
    return this._actionDirective;
  }

  set directive(directive) {
    this.setActionDirective(directive);
  }

  protected setActionDirective(directive: ActionDirective<E>) {
    if (directive) {
      this._actionDirective = directive;
      this._actionSub.subscription = directive.stream.pipe(
        filter((e) => {
          return e.state === ActionState.Complete;
        })
      ).subscribe((x) => {
        this.onActionCompleted(x.result);
      });
    }
  }

  // MARK: Actions
  onCancelClick(): void {
    this.dialogRef.close();
  }

  protected onActionCompleted(result: any) {
    this.dialogRef.close(result);
  }

}
