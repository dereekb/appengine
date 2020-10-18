import { MatDialogRef } from '@angular/material/dialog';
import { SubscriptionObject, UniqueModel } from '@gae-web/appengine-utility';
import { AbstractActionDirective } from '../../shared/action.directive';
import { AbstractActionDialogCompoment } from '../../shared/action.component';
import { CreateActionDirective, CreateActionDirectiveEvent } from './create.directive';
import { ViewChild, Directive } from '@angular/core';

/**
 * AbstractActionDialogCompoment for a CreateActionDirective.
 */
@Directive()
export abstract class AbstractCreateActionDialogCompoment<T extends UniqueModel> extends AbstractActionDialogCompoment<CreateActionDirectiveEvent<T>> {

  @ViewChild(CreateActionDirective, {static: true})
  set createActionDirective(directive: CreateActionDirective<T>) {
    super.setActionDirective(directive);
  }

}
