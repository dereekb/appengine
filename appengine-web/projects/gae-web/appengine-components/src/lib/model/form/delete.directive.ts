import { Directive } from '@angular/core';

import { AbstractFormActionSubmitController } from './form-submit.controller';
import { UniqueModel } from '@gae-web/appengine-utility';
import { DeleteResponse } from '@gae-web/appengine-api';
import { DeleteActionDirective } from '../action/delete.directive';

@Directive({
    selector: '[gaeDeleteModelFormController]',
    exportAs: 'gaeDeleteModelFormController'
})
export class GaeDeleteModelFormControllerDirective<T extends UniqueModel> extends AbstractFormActionSubmitController<T> {

    private _response?: DeleteResponse<T>;

    // MARK: Internal
    protected get deleteAction(): DeleteActionDirective<T> {
        return this.action as DeleteActionDirective<T>;
    }

    protected resetAction() {
        delete this._response;
        this._response = undefined;
        this.submit.locked = false; // Unlock.
        super.resetAction();
    }

    protected doSubmit(): void {
        const template: T | undefined = this.form.model;

        if (template) {
            const obs = this.deleteAction.doDelete({
                keys: template.modelKey
            });

            obs.subscribe((result) => {
                this.setResult(result as DeleteResponse<T>);
            });
        }
    }

    protected setResult(result: DeleteResponse<T>) {
        this.submit.locked = true;
        this._response = result;
    }

}
