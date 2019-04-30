import { OnDestroy, AfterContentInit, Provider, Type } from '@angular/core';

import { FormGroup } from '@angular/forms';
import { Observable, Subscription, BehaviorSubject } from 'rxjs';
import { delay, debounceTime, startWith } from 'rxjs/operators';
import { SubscriptionObject } from '@gae-web/appengine-utility';

export interface FormErrors {
    [key: string]: string;
}

export interface ValidationMessages {
    [key: string]: string;
}

export interface ValidationMessagesSet {
    [key: string]: ValidationMessages;
}

export interface FormValidationErrors {
    [key: string]: FieldValidationError;
}

export interface FormGroupComponentErrors {
    formErrors: ValidationMessages;
    controlErrors: ValidationMessages;
}

export interface FormComponentEvent {
    readonly isComplete: boolean;
    readonly state: FormComponentState;
}

export class FormComponent {
    readonly isComplete: boolean;
    readonly state: FormComponentState;
    readonly stream: Observable<FormComponentEvent>;
}

export function ProvideFormComponent<S extends FormComponent>(sourceType: Type<S>): Provider[] {
    return [{ provide: FormComponent, useExisting: sourceType }];
}

export class FormGroupComponent extends FormComponent {
    readonly controlErrorsObs: Observable<FormErrors>;
    readonly formErrorsObs: Observable<FormErrors>;
}

export function ProvideFormGroupComponent<S extends FormGroupComponent>(sourceType: Type<S>): Provider[] {
    return [...ProvideFormComponent(sourceType), { provide: FormGroupComponent, useExisting: sourceType }];
}

// MARK: Validation Errors
export enum FieldValidationErrorType {
    Field,
    FormGroup
}

export enum FieldValidationErrorChange {
    Set,
    Clear   // DEPRECATED
}

export class FieldValidationError {

    public errorKey?: string;
    public type = FieldValidationErrorType.Field;
    public makeDirty = false;

    static clearFieldError(): FieldValidationError {
        const config = new FieldValidationError();

        config.type = FieldValidationErrorType.Field;
        config.makeDirty = false;

        return config;
    }

    constructor() { }

}

// MARK: Component
export enum FormComponentState {
    Incomplete = 0,
    Complete = 1,
    Reset = 2
}

/**
 * Base component that wraps a FormGroup and provides validation.
 */
export abstract class AbstractFormGroupComponent implements FormGroupComponent, OnDestroy, AfterContentInit {

    private _initialized = false;

    private _form: FormGroup;
    private _formSub = new SubscriptionObject();
    private _controlNames: string[];

    private _updatedSubject = new BehaviorSubject<FormComponentEvent>({ isComplete: false, state: FormComponentState.Reset });
    private _updatedObservable = this._updatedSubject.asObservable();

    protected delay = 250;
    protected debounce = 50;

    private _formErrors = new BehaviorSubject<FormErrors>({});
    private _controlErrors = new BehaviorSubject<FormErrors>({});

    protected validationMessages: ValidationMessagesSet = {};

    constructor() { }

    ngAfterContentInit() {
        this._initialize();
    }

    ngOnDestroy(): void {
        this._formSub.destroy();
        this._updatedSubject.complete();

        this._formErrors.complete();
        this._controlErrors.complete();
    }

    // MARK: External Accessors
    public get isComplete() {
        return this._form.valid;
    }

    public get isValid() {
        return this._form.valid;
    }

    public get formValue() {
        return this._form.value;
    }

    public get stream() {
        return this._updatedObservable;
    }

    public get state() {
        return this._updatedSubject.value.state;
    }

    // MARK: Internal Accessor
    public get formErrors() {
        return this._formErrors.value;
    }

    public get formErrorsObs(): Observable<FormErrors> {
        return this._formErrors;
    }

    public get controlErrors() {
        return this._controlErrors.value;
    }

    public get controlErrorsObs(): Observable<FormErrors> {
        return this._controlErrors;
    }

    protected get controlNames(): string[] {
        return this._controlNames;
    }

    public get form() {
        return this._form;
    }

    protected setFormGroup(formGroup: FormGroup) {
        this._form = formGroup;

        if (this._initialized) {
            this._bindToFormGroup();
        }
    }

    // MARK: Initialization
    protected get initialized() {
        return this._initialized;
    }

    private _initialize() {
        if (!this._initialized) {
            this.initialize();
        }
    }

    protected initialize() {
        this._bindToFormGroup();
        this._initialized = true;
    }

    protected _bindToFormGroup() {
        if (this._form) {
            this._controlNames = Object.keys(this._form.controls);
            this._formSub.subscription = this._form.valueChanges.pipe(
                delay(this.delay),
                debounceTime(this.debounce)
            ).subscribe(x => this.updateForChange());

            // Update for change immediately.
            this.updateForChange();
        }
    }

    // MARK: Update
    public forceUpdateForChange() {
        this.updateForChange();
    }

    protected updateForChange() {
        this.refreshValidation();
        this.next(this._nextUpdateEvent());
    }

    protected next(event: FormComponentEvent) {
        this._updatedSubject.next(event);
    }

    protected _nextUpdateEvent(): FormComponentEvent {
        const complete = this.isComplete;

        return {
            isComplete: complete,
            state: (complete) ? FormComponentState.Complete : FormComponentState.Incomplete
        };
    }

    // MARK: Validation
    protected refreshValidation() {
        const form = this._form;

        if (!form) { return; }

        const messages: FormGroupComponentErrors = {
            formErrors: {},
            controlErrors: {}
        };

        this.refreshFormGroupValidation(messages);
        this.refreshFormFieldValidation(messages);

        this._formErrors.next(messages.formErrors);
        this._controlErrors.next(messages.controlErrors);
    }

    private refreshFormGroupValidation(messages: FormGroupComponentErrors) {
        const errors = this.form.errors || {};

        // Look through each of the current errors, and set the controls to be invalid.
        // Their messages will be set below.
        Object.keys(errors).forEach((errorName) => {
            const formValidationErrors: FormValidationErrors = errors[errorName];
            this._buildMessagesWithFormValidationErrors(errorName, formValidationErrors, messages);
        });

        return messages;
    }

    private _buildMessagesWithFormValidationErrors(errorName: string, formValidationErrors: FormValidationErrors, messages: FormGroupComponentErrors) {
        Object.keys(formValidationErrors).forEach((control) => {
            // const control = this.form.get(control);
            const error: FieldValidationError = formValidationErrors[control];
            let errorMessages: ValidationMessages;

            if (!error) {
                return;
            }

            switch (error.type) {
                case FieldValidationErrorType.FormGroup:
                    errorMessages = messages.formErrors;
                    break;
                case FieldValidationErrorType.Field:
                default:
                    errorMessages = messages.controlErrors;

                    /*
                        if (error.makeDirty) {
                            control.markAsDirty(true);
                        }
                    */

                    break;
            }

            errorMessages[control] = this.validationMessages[control][error.errorKey || errorName];
        });
    }

    private refreshFormFieldValidation(messages: FormGroupComponentErrors): void {
        const controlErrorMessages: ValidationMessages = messages.controlErrors;

        this.controlNames.forEach((controlName) => {
            const control = this.form.get(controlName);

            if (control && control.dirty && !control.valid) {
                const controlValidationMessages = this.validationMessages[controlName];
                const controlErrors = control.errors;

                if (controlErrors && controlValidationMessages) {
                    for (const key in controlErrors) {
                        if (controlErrors[key] !== undefined) {
                            controlErrorMessages[controlName] = controlValidationMessages[key] || 'Unknown Error';    // Only show 1 error at a time.
                        }
                    }
                }
            }
        });
    }

}
