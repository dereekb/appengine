import { MatDialogRef } from '@angular/material/dialog';
import { SubscriptionObject, UniqueModel } from '@gae-web/appengine-utility';
import { AbstractActionDirective } from '../../shared/action.directive';
import { AbstractActionDialogCompoment } from '../../shared/action.component';
import { CreateActionDirective, CreateActionDirectiveEvent } from './create.directive';
import { ViewChild } from '@angular/core';

/**
 * AbstractActionDialogCompoment for a CreateActionDirective.
 */
export abstract class AbstractCreateActionDialogCompoment<T extends UniqueModel> extends AbstractActionDialogCompoment<CreateActionDirectiveEvent<T>> {

  @ViewChild(CreateActionDirective, {static: false})
  set createActionDirective(directive: CreateActionDirective<T>) {
    super.setActionDirective(directive);
  }

}
