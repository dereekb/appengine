import { ProvideFormGroupComponent } from './form.component';
import { GaeConfirmModelFormComponent, GaeConfiguredConfirmModelFormComponent } from './confirm-form.component';
import { Component } from '@angular/core';

@Component({
  selector: 'gae-confirm-delete-model-form',
  template: '<gae-confirm-model-form [hint]=hint [input]="input"></gae-confirm-model-form>',
  providers: [ProvideFormGroupComponent(GaeConfirmModelFormComponent)]
})
export class GaeConfirmDeleteModelFormComponent<T> extends GaeConfiguredConfirmModelFormComponent<T> {

  constructor() {
    super();
    this.hint = 'Confirmation is required to delete.';
  }

}
