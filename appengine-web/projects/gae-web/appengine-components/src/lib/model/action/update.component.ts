import { SubscriptionObject, UniqueModel } from '@gae-web/appengine-utility';
import { AbstractActionDialogComponent } from '../../shared/action.component';
import { UpdateActionDirective, UpdateActionDirectiveEvent } from './update.directive';
import { MatDialogRef } from '@angular/material/dialog';
import { ViewChild, Directive, Inject } from '@angular/core';

/**
 * AbstractActionDialogComponent for a UpdateActionDirective.
 */
@Directive()
export abstract class AbstractUpdateActionDialogComponent<T extends UniqueModel> extends AbstractActionDialogComponent<UpdateActionDirectiveEvent<T>> {

  constructor(@Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<AbstractUpdateActionDialogComponent<T>>) {
    super(dialogRef);
  }

  @ViewChild(UpdateActionDirective, { static: true })
  set updateActionDirective(directive: UpdateActionDirective<T>) {
    super.setActionDirective(directive);
  }

}
