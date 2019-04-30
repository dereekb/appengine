import { Component, ViewEncapsulation } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';

// MARK: Component
/**
 * The Foo Foo List View. Not intended for reuse.
 */
@Component({
    selector: 'app-foo-model-form',
    templateUrl: './model-form.component.html'
})
export class FooModelFormComponent extends AbstractModelFormComponent<Foo> {

    validationMessages = {
        'name': {
            'required': 'Name is required.',
            'maxlength': 'Name must be no more than 24 characters long.'
        },
        'detail': {
            'maxlength': 'Detail must be no more than 140 characters long.'
        },
        'verb': {
            'maxlength': 'Verb must be no more than 16 characters long.'
        },
        'noun': {
            'maxlength': 'Noun must be no more than 16 characters long.'
        },
        'count': {
            'maxlength': 'Count must be no more than 16 characters long.'
        }
    };

    constructor(formBuilder: FormBuilder) {
        super(formBuilder);
    }

    // MARK: Internal
    protected makeNewFormGroup(): FormGroup {
        return this._formBuilder.group({
            'name': ['', [Validators.required, Validators.maxLength(24)]],
            'verb': ['', [Validators.maxLength(16)]],
            'noun': ['', [Validators.maxLength(16)]],
            'count': ['', [Validators.maxLength(16)]],
            'detail': ['', [Validators.maxLength(140)]],
            'unit': FooUnitTypeFormComponent.makeUnitTypeTargetFormGroup(this._formBuilder)
        });
    }

    protected convertToFormData(model: Foo): any {
        const data: any = {};

        data.name = model.name;
        data.verb = model.verb;
        data.noun = model.noun;
        data.detail = model.detail;
        data.count = model.count;
        data.unit = {
            unit: model.unit,
            type: String(model.type)
        };

        return data;
    }

    protected makeForSubmission(value: any): Foo {
        const model = new Foo();

        model.name = value.name;
        model.verb = value.verb;
        model.noun = value.noun;
        model.detail = value.detail;
        model.count = value.count;
        model.unit = value.unit.unit;
        model.type = Number(value.unit.type);

        return model;
    }

}
