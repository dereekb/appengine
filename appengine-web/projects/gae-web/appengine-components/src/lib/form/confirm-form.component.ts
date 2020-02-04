import { Component, Input, ViewChild, OnDestroy } from '@angular/core';

import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { FormComponentState, AbstractFormGroupComponent, ProvideFormGroupComponent, FormComponentEvent, FormErrors } from '../form/form.component';
import { ModelFormComponent } from '../form/model.component';
import { Observable } from 'rxjs';
import { IsTruthy } from './validators';
import { SubscriptionObject } from '@gae-web/appengine-utility';

@Component({
    templateUrl: './confirm-form.component.html',
    selector: 'gae-confirm-model-form',
    providers: [ProvideFormGroupComponent(GaeConfirmModelFormComponent)]
})
export class GaeConfirmModelFormComponent<T> extends AbstractFormGroupComponent implements ModelFormComponent<T>, OnDestroy {

    validationMessages = {
        confirm: {
            required: 'Confirmation is required.',
            isTruthy: 'Confirmation is required.'
        }
    };

    @Input()
    public hint: string;

    private _model: T;
    private _input: Observable<T>;
    private _sub = new SubscriptionObject();

    buildFormGroup(formBuilder: FormBuilder): FormGroup {
        const group = formBuilder.group({
            confirm: [false, [IsTruthy(), Validators.required]]
        });
        return group;
    }

    ngOnDestroy() {
        this._sub.destroy();
    }

    @Input()
    public set input(input: Observable<T>) {
        this._input = input;

        if (this.initialized) {
            this._bindToInput();
        }
    }

    public get isComplete(): boolean {
        return this.isValid && Boolean(this._model);
    }

    // MARK: Model
    public get hasModel() {
        return Boolean(this._model);
    }

    public get model(): T | undefined {
        return (this.isComplete) ? this._model : undefined;
    }

    public reset() {
        this.form.reset();
        this.next({
            isComplete: this.isComplete,
            state: FormComponentState.Reset
        });
    }

    // MARK: Initialize
    protected initialize() {
        this._bindToInput();
        super.initialize();
    }

    private _bindToInput() {
        if (this._input) {
            this._sub.subscription = this._input.subscribe((model) => {
                this._model = model;
            });
        }
    }

}

/**
 * Abstract component used for views that pre-configure a GaeConfirmModelFormComponent.
 */
export abstract class GaeConfiguredConfirmModelFormComponent<T> implements ModelFormComponent<T> {

    @ViewChild(GaeConfirmModelFormComponent, { static: true })
    private _form: GaeConfirmModelFormComponent<T>;

    @Input()
    public hint: string;

    @Input()
    public input: Observable<T>;

    // MARK: Form
    public get isComplete() {
        return this._form.isComplete;
    }

    public get formValue() {
        return this._form.formValue;
    }

    public get stream(): Observable<FormComponentEvent> {
        return this._form.stream;
    }

    public get state(): FormComponentState {
        return this._form.state;
    }

    public get controlErrorsObs(): Observable<FormErrors> {
        return this._form.controlErrorsObs;
    }

    public get formErrorsObs(): Observable<FormErrors> {
        return this._form.formErrorsObs;
    }

    // MARK: Model Form
    public get model() {
        return this._form.model;
    }

    public reset() {
        return this._form.reset();
    }

}
