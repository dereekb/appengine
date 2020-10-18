import { Output, OnDestroy, EventEmitter, Optional, Host, Inject, Directive } from '@angular/core';
import { AbstractFormActionSubmitController } from './form-submit.controller';
import { TemplateResponse } from '@gae-web/appengine-api';
import { UniqueModel } from '@gae-web/appengine-utility';
import { BehaviorSubject, Observable } from 'rxjs';
import { TemplateActionDirective } from '../action/template.directive';
import { ModelFormComponent } from '../../form/model.component';
import { ActionState } from '../../shared/action';
import { map } from 'rxjs/operators';



export class TemplateModelFormEvent<R extends TemplateResponse<T>, T extends UniqueModel> {
    state: ActionState;
    response?: R;
    error?: Error;
}

/**
 * Abstract ModelFormController directive for a TemplateResponse.
 */
@Directive()
export abstract class AbstractTemplateModelFormControllerDirective<R extends TemplateResponse<T>, T extends UniqueModel>
    extends AbstractFormActionSubmitController<T> implements OnDestroy {

    @Output()
    public readonly resultSet = new EventEmitter<R>();

    private _events = new BehaviorSubject<TemplateModelFormEvent<R, T>>({ state: ActionState.Reset });

    constructor(@Optional() @Host() @Inject(ModelFormComponent) form: ModelFormComponent<T>) {
        super(form);
    }

    ngOnDestroy() {
        this._events.complete();
    }

    // MARK: Accessors
    public get result(): R | undefined {
        return this._events.value.response;
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
        return this.controllerStream.pipe(
            map(x => x.response)
        );
    }

    public get lastControllerEvent() {
        return this._events.value;
    }

    public get controllerStream(): Observable<TemplateModelFormEvent<R, T>> {
        return this._events.asObservable();
    }

    // MARK: Internal
    protected get templateAction(): TemplateActionDirective<T> {
        return this.action as TemplateActionDirective<T>;
    }

    protected resetAction() {
        this._events.next({ state: ActionState.Reset });
        this.submit.isLocked = false; // Unlock.
        super.resetAction();
    }

    protected doSubmit(): void {
        const template: T | undefined = this.form.model;

        if (template) {
            this._events.next({
                state: ActionState.Working
            });

            const obs = this.templateAction.doTemplateAction({
                templates: template
            });

            obs.subscribe((result) => {
                this.setResult(result as R);
            }, (error) => {
                this.setError(error);
            });
        } else {
            throw new Error('No model is available on the current form.');
        }
    }

    protected setResult(result: R | undefined) {
        this.submit.isLocked = true;
        this._events.next({
            state: ActionState.Complete,
            response: result
        });
        this.resultSet.next(result);
    }

    protected setError(error: any) {
        this.submit.isLocked = false;
        this._events.next({
            state: ActionState.Error,
            error
        });
    }

}

/*
export abstract class AbstractTemplateModelFormComponent<T extends UniqueModel> extends AbstractFormActionObject<T> {

    constructor(child: GaeCreateModelFormControllerDirective<T>) {
        super(child);
    }

}
*/
