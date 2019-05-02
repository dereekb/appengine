import { Input, Directive, OnDestroy } from '@angular/core';

import { AbstractTemplateModelFormControllerDirective } from './template.directive';
import { UniqueModel } from '@gae-web/appengine-utility';
import { UpdateResponse } from '@gae-web/appengine-api';
import { Subscription, Observable } from 'rxjs';
import { FormComponentEvent } from '../../form/form.component';

@Directive({
    selector: '[gaeUpdateModelFormController]',
    exportAs: 'gaeUpdateModelFormController'
})
export class GaeUpdateModelFormControllerDirective<T extends UniqueModel> extends AbstractTemplateModelFormControllerDirective<UpdateResponse<T>, T> implements OnDestroy {

    private _modelSub: Subscription;

    ngOnDestroy() {
        super.ngOnDestroy();
        this._clearModelSub();
    }

    @Input()
    public set model(model: Observable<T | undefined>) {
        if (model) {
            this._clearModelSub();

            this._modelSub = model.subscribe((x) => {
                this.form.model = x;
            });
        }
    }

    // MARK: Send/Update
    protected updateForFormEvent(event: FormComponentEvent) {
        super.updateForFormEvent(event);
        this.submit.locked = false; // Unlock on edits.
    }

    private _clearModelSub() {
        if (this._modelSub) {
            this._modelSub.unsubscribe();
            delete this._modelSub;
        }
    }

}
