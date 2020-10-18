import { MatDialogRef } from '@angular/material/dialog';
import { SubscriptionObject, UniqueModel } from '@gae-web/appengine-utility';
import { AbstractActionDirective } from '../../shared/action.directive';
import { AbstractActionDialogComponent } from '../../shared/action.component';
import { CreateActionDirective, CreateActionDirectiveEvent } from './create.directive';
import { ViewChild, Directive, Inject } from '@angular/core';

/**
 * AbstractActionDialogComponent for a CreateActionDirective.
 */
@Directive()
export abstract class AbstractCreateActionDialogComponent<T extends UniqueModel> extends AbstractActionDialogComponent<CreateActionDirectiveEvent<T>> {

  constructor(@Inject(MatDialogRef) public readonly dialogRef: MatDialogRef<AbstractCreateActionDialogComponent<T>>) {
    super(dialogRef);
  }

  @ViewChild(CreateActionDirective, { static: true })
  set createActionDirective(directive: CreateActionDirective<T>) {
    super.setActionDirective(directive);
  }

}
