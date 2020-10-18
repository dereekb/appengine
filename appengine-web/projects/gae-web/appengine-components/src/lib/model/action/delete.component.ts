import { MatDialogRef } from '@angular/material/dialog';
import { SubscriptionObject, UniqueModel } from '@gae-web/appengine-utility';
import { AbstractActionDirective } from '../../shared/action.directive';
import { AbstractActionDialogCompoment } from '../../shared/action.component';
import { DeleteActionDirective, DeleteActionDirectiveEvent } from './delete.directive';
import { ViewChild, Directive } from '@angular/core';

/**
 * AbstractActionDialogCompoment for a DeleteActionDirective.
 */
@Directive()
export abstract class AbstractDeleteActionDialogCompoment<T extends UniqueModel> extends AbstractActionDialogCompoment<DeleteActionDirectiveEvent<T>> {

  @ViewChild(DeleteActionDirective, {static: true})
  set deleteActionDirective(directive: DeleteActionDirective<T>) {
    super.setActionDirective(directive);
  }

}
