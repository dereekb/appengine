import { Input, OnDestroy, Optional, Host, Inject } from '@angular/core';
import { ActionSubmitController, AbstractActionObject } from './action-submit.controller';
import { Subscription } from 'rxjs';
import { ModelFormComponent } from '../../form/model.component';
import { distinct } from 'rxjs/operators';
import { FormComponentEvent, FormComponentState } from '../../form/form.component';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { ActionEvent, ActionState } from '../../shared/action';

/**
 * ActionSubmitController used for sending data from a ModelFormComponent.
 *
 * Will enable/disable the submit button based on the current form state.
 */
export abstract class AbstractFormActionSubmitController<T> extends ActionSubmitController implements OnDestroy {

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
        this._formSub.subscription = this._form.stream
            .subscribe((event) => this.updateForFormEvent(event));
    }

    protected updateForFormEvent(event: FormComponentEvent) {
        this.submit.isDisabled = !event.isComplete;

        if (event.state === FormComponentState.Reset) {
            this.resetAction(); // Reset the action if the model is reset.
        }
    }

    protected updateForActionEvent(event: ActionEvent) {
        super.updateForActionEvent(event);

        switch (event.state) {
            case ActionState.Reset:
                this.resetForm();
                break;
        }
    }

    public resetForm() {
        if (this._form && this._form.state !== FormComponentState.Reset) {
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
