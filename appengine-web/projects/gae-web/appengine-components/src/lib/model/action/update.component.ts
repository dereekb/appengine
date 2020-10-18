import { SubscriptionObject, UniqueModel } from '@gae-web/appengine-utility';
import { AbstractActionDialogCompoment } from '../../shared/action.component';
import { UpdateActionDirective, UpdateActionDirectiveEvent } from './update.directive';
import { ViewChild, Directive } from '@angular/core';

/**
 * AbstractActionDialogCompoment for a UpdateActionDirective.
 */
@Directive()
export abstract class AbstractUpdateActionDialogCompoment<T extends UniqueModel> extends AbstractActionDialogCompoment<UpdateActionDirectiveEvent<T>> {

  @ViewChild(UpdateActionDirective, {static: true})
  set updateActionDirective(directive: UpdateActionDirective<T>) {
    super.setActionDirective(directive);
  }

}
