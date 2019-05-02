import { Output, OnDestroy, EventEmitter, Optional, Host, Inject } from '@angular/core';
import { AbstractFormActionSubmitController } from './form-submit.controller';
import { TemplateResponse } from '@gae-web/appengine-api';
import { UniqueModel } from '@gae-web/appengine-utility';
import { BehaviorSubject, Observable } from 'rxjs';
import { TemplateActionDirective } from '../action/template.directive';
import { ModelFormComponent } from '../../form/model.component';

/**
 * Abstract ModelFormController directive for a TemplateResponse.
 */
export abstract class AbstractTemplateModelFormControllerDirective<R extends TemplateResponse<T>, T extends UniqueModel>
    extends AbstractFormActionSubmitController<T> implements OnDestroy {

    @Output()
    public readonly resultSet = new EventEmitter<R>();

    private _response = new BehaviorSubject<R | undefined>(undefined);

    constructor(@Optional() @Host() @Inject(ModelFormComponent) form: ModelFormComponent<T>) {
        super(form);
    }

    ngOnDestroy() {
        this._response.complete();
    }

    // MARK: Accessors
    public get result(): R | undefined {
        return this._response.value;
    }

    public get hasResult(): boolean {
        return Boolean(this.result);
    }

    public get firstResult(): T | undefined {
        return (this.result) ? this.result.models[0] : undefined;
    }

    public get hasFirstResult(): boolean {
        return Boolean(this.result && this.result.models.length);
    }

    public get responseStream(): Observable<R | undefined> {
        return this._response.asObservable();
    }

    // MARK: Internal
    protected get templateAction(): TemplateActionDirective<T> {
        return this.action as TemplateActionDirective<T>;
    }

    protected resetAction() {
        this._response.next(undefined);
        this.submit.locked = false; // Unlock.
        super.resetAction();
    }

    protected doSubmit(): void {
        const template: T | undefined = this.form.model;

        if (template) {
            const obs = this.templateAction.doTemplateAction({
                templates: template
            });

            obs.subscribe((result) => {
                this.setResult(result as R);
            });
        } else {
            throw new Error('No model is available on the current form.');
        }
    }

    protected setResult(result: R | undefined) {
        this.submit.locked = true;
        this._response.next(result);
        this.resultSet.next(result);
    }

}

/*
export abstract class AbstractTemplateModelFormComponent<T extends UniqueModel> extends AbstractFormActionObject<T> {

    constructor(child: GaeCreateModelFormControllerDirective<T>) {
        super(child);
    }

}
*/
