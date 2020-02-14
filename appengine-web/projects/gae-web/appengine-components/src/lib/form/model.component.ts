import { Input, Output, Provider, Type, ChangeDetectorRef, Inject } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { FormComponentEvent, FormComponentState, AbstractFormGroupComponent, FormComponent, ProvideFormGroupComponent, ProvideFormComponent, FormGroupComponent } from './form.component';
import { Observable } from 'rxjs';
import { UniqueModel, MutableUniqueModel } from '@gae-web/appengine-utility';
import { GaeViewUtility } from '../shared/utility';

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
export abstract class AbstractModelFormComponent<T> extends AbstractFormGroupComponent implements ModelFormComponent<T> {

    @Input()
    public hideBeforeInput: boolean;

    protected _inputModel?: T;
    protected _cachedModel?: T;

    // MARK: View
    public show() {
        return this.hasInputModel() || !this.hideBeforeInput;
    }

    public hide() {
        return !this.show();
    }

    // MARK: Initialization
    protected initialize() {
        super.initialize();
        this.reset();
        GaeViewUtility.safeDetectChanges(this._cdRef);
    }

    // MARK: Model
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

        // Only set if initialized. Will reset when initialized.
        if (this.initialized) {
            const data = (model) ? this.convertToFormData(model) : this.newModelData;
            this.setModelData(data);
        }
    }

    protected get newModelData(): any {
        return {};
    }

    protected setModelData(data: any) {
        this.form.reset(data);
    }

    protected getOrCreateModel() {
        if (!this._cachedModel) {
            this._cachedModel = this.makeForSubmission(this.formValue);
            this.synchronizeCachedModelWithInputModel(this._cachedModel, this._inputModel);
        }

        return this._cachedModel;
    }

    protected synchronizeCachedModelWithInputModel(cachedModel: T, inputModel: T) { }

    protected abstract makeForSubmission(value: any): T;

    protected abstract convertToFormData(model: T): any;

    // MARK: Update
    protected updateForChange() {
        super.updateForChange();
        this._cachedModel = undefined;
    }

}

/**
 * AbstractModelFormComponent extension used for generating a unique model.
 */
export abstract class AbstractUniqueModelFormComponent<T extends MutableUniqueModel> extends AbstractModelFormComponent<T> {

    // MARK: Model
    public get isModelEdit() {
        return Boolean(this._inputModel && this._inputModel.key);
    }

    // MARK: Overrides
    protected setModelData(data: any) {
        super.setModelData(data);

        if (this.isModelEdit) {
            this.resetForModelEdit(this._inputModel);
        }
    }

    protected resetForModelEdit(inputModel: T) {
        // Do nothing by default.
    }

    protected synchronizeCachedModelWithInputModel(cachedModel: T, inputModel: T) {
        if (!cachedModel.modelKey && inputModel) {
            cachedModel.modelKey = inputModel.modelKey;
        }
    }

}
