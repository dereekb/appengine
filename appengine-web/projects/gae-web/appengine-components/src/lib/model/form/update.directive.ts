import { Input, Directive, OnDestroy } from '@angular/core';

import { AbstractTemplateModelFormControllerDirective } from './template.directive';
import { UniqueModel, SubscriptionObject } from '@gae-web/appengine-utility';
import { UpdateResponse } from '@gae-web/appengine-api';
import { Subscription, Observable } from 'rxjs';
import { FormComponentEvent } from '../../form/form.component';

@Directive({
    selector: '[gaeUpdateModelFormController]',
    exportAs: 'gaeUpdateModelFormController'
})
export class GaeUpdateModelFormControllerDirective<T extends UniqueModel> extends AbstractTemplateModelFormControllerDirective<UpdateResponse<T>, T> implements OnDestroy {

    private _modelSub = new SubscriptionObject();

    ngOnDestroy() {
        super.ngOnDestroy();
        this._modelSub.destroy();
    }

    @Input()
    public set model(model: Observable<T | undefined>) {
        if (model) {
            this._modelSub.subscription = model.subscribe((x) => {
                this.form.model = x;
            });
        }
    }

    // MARK: Send/Update
    protected updateForFormEvent(event: FormComponentEvent) {
        super.updateForFormEvent(event);
        this.submit.isLocked = false; // Unlock on edits.
    }

}
