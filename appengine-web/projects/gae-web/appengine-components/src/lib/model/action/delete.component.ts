import { MatDialogRef } from '@angular/material/dialog';
import { SubscriptionObject, UniqueModel } from '@gae-web/appengine-utility';
import { AbstractActionDirective } from '../../shared/action.directive';
import { AbstractActionDialogComponent } from '../../shared/action.component';
import { DeleteActionDirective, DeleteActionDirectiveEvent } from './delete.directive';
import { ViewChild, Directive, Inject } from '@angular/core';

/**
 * AbstractActionDialogComponent for a DeleteActionDirective.
 */
@Directive()
export abstract class AbstractDeleteActionDialogComponent<T extends UniqueModel> extends AbstractActionDialogComponent<DeleteActionDirectiveEvent<T>> {

  constructor(@Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<AbstractDeleteActionDialogComponent<T>>) {
    super(dialogRef);
  }

  @ViewChild(DeleteActionDirective, {static: true})
  set deleteActionDirective(directive: DeleteActionDirective<T>) {
    super.setActionDirective(directive);
  }

}
