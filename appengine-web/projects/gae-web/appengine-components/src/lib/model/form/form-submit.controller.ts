import { Input, OnDestroy, Optional, Host, Inject } from '@angular/core';
import { AbstractActionSubmitController, AbstractActionObject } from './action-submit.controller';
import { Subscription } from 'rxjs';
import { ModelFormComponent } from '../../form/model.component';
import { distinct } from 'rxjs/operators';
import { FormComponentEvent, FormComponentState } from '../../form/form.component';
import { SubscriptionObject } from '@gae-web/appengine-utility';

/**
 * AbstractActionSubmitController used for sending data from a ModelFormComponent.
 *
 * Will enable/disable the submit button based on the current form state.
 */
export abstract class AbstractFormActionSubmitController<T> extends AbstractActionSubmitController implements OnDestroy {

    private _form: ModelFormComponent<T>;
    private _formSub = new SubscriptionObject();

    ngOnDestroy() {
        super.ngOnDestroy();
        this._formSub.destroy();
    }

    constructor(@Optional() @Host() @Inject(ModelFormComponent) form: ModelFormComponent<T>) {
        super();

        if (form) {
            this.form = form;
        }
    }

    /**
     * Will set the form if the host is a ModelFormComponent.
     */
    @Optional() @Inject(ModelFormComponent) @Host()
    public set hostForm(hostForm: ModelFormComponent<T>) {
        if (hostForm) {
            this.form = hostForm;
        }
    }

    // MARK: Accessors
    public get form() {
        return this._form;
    }

    // MARK: Functions
    @Input()
    public set form(form: ModelFormComponent<T>) {
        this._form = form;
        this._formSub.subscription = this._form.stream.pipe(
            distinct()
        ).subscribe((event) => this.updateForFormEvent(event));
    }

    protected updateForFormEvent(event: FormComponentEvent) {
        this.submit.enabled = event.isComplete;

        if (event.state === FormComponentState.Reset) {
            this.resetAction(); // Reset the action if the model is reset.
        }
    }

    public resetForm() {
        if (this._form) {
            this._form.reset();
        }
    }

}

/**
 * Abstract component that wraps/contains a AbstractFormActionSubmitController.
 */
export abstract class AbstractFormActionObject<T> extends AbstractActionObject {

    public get controller(): AbstractFormActionSubmitController<T> {
        return this._controller as AbstractFormActionSubmitController<T>;
    }

    public reset() {
        super.reset();
        this.controller.resetForm();
    }

}
