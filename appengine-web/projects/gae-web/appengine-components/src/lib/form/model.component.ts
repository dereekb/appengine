import { Input, Output, Provider, Type } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormComponentEvent, FormComponentState, AbstractFormGroupComponent, FormComponent, ProvideFormGroupComponent, ProvideFormComponent, FormGroupComponent } from './form.component';
import { Observable } from 'rxjs';
import { UniqueModel, MutableUniqueModel } from '@gae-web/appengine-utility';

export interface ModelFormComponentDelegate<T> {
    submit(input: T): Observable<T>;
}

export abstract class ModelFormComponent<T> extends FormGroupComponent {
    model: T | undefined;
    abstract reset(): void;
}

export function ProvideModelFormComponent<S extends ModelFormComponent<any>>(sourceType: Type<S>): Provider[] {
    return [...ProvideFormGroupComponent(sourceType), { provide: ModelFormComponent, useExisting: sourceType }];
}

/**
 * AbstractFormGroupComponent used for generating a model.
 */
export abstract class AbstractModelFormComponent<T extends MutableUniqueModel> extends AbstractFormGroupComponent implements ModelFormComponent<T> {

    @Input()
    public hideBeforeInput: boolean;

    private _inputModel?: T;
    private _cachedModel?: T;

    constructor(protected _formBuilder: FormBuilder) {
        super();
        this.resetFormGroup();
    }

    // MARK: Form
    protected resetFormGroup() {
        const formGroup = this.makeNewFormGroup();
        this.setFormGroup(formGroup);
    }

    protected abstract makeNewFormGroup(): FormGroup;

    // MARK: View
    public show() {
        return this.hasInputModel() || !this.hideBeforeInput;
    }

    public hide() {
        return !this.show();
    }

    // MARK: Model
    public get isModelEdit() {
        return Boolean(this._inputModel && this._inputModel.key);
    }

    public hasInputModel() {
        return Boolean(this._inputModel);
    }

    public get model(): T | undefined {
        return (this.isComplete) ? this.getOrCreateModel() : undefined;
    }

    public set model(model: T | undefined) {
        this.setModel(model);
    }

    public reset() {
        this.setModel(this._inputModel);
        this.next({
            isComplete: this.isComplete,
            state: FormComponentState.Reset
        });
    }

    protected setModel(model: T | undefined) {
        this._inputModel = model;
        const data = (model) ? this.convertToFormData(model) : this.newModelData;
        this.setModelData(data);
    }

    protected get newModelData(): any {
        return {};
    }

    protected setModelData(data: any) {
        this.form.reset(data);

        if (this.isModelEdit) {
            this.resetForModelEdit(this._inputModel);
        }
    }

    protected resetForModelEdit(inputModel: T) {
        // Do nothing by default.
    }

    protected getOrCreateModel() {
        if (!this._cachedModel) {
            this._cachedModel = this.makeForSubmission(this.formValue);

            if (!this._cachedModel.modelKey && this._inputModel) {
                this._cachedModel.modelKey = this._inputModel.modelKey;
            }
        }

        return this._cachedModel;
    }

    protected abstract makeForSubmission(value: any): T;

    protected abstract convertToFormData(model: T): any;

    // MARK: Update
    protected updateForChange() {
        super.updateForChange();
        this._cachedModel = undefined;
    }

}