import { Component, ViewEncapsulation } from '@angular/core';

import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AbstractModelFormComponent } from '@gae-web/appengine-components';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { DateTime } from 'luxon';
import { ValueUtility, DateTimeUtility } from '@gae-web/appengine-utility';

// MARK: Component
/**
 * The Foo Model Form View. Not intended for reuse.
 */
@Component({
    selector: 'app-foo-model-form',
    templateUrl: './model-form.component.html'
})
export class FooModelFormComponent extends AbstractModelFormComponent<Foo> {

    validationMessages = {
        name: {
            maxlength: 'Name must be no more than 24 characters long.'
        },
        date: {},
        number: {},
        numberList: {},
        stringSet: {}
    };

    constructor(formBuilder: FormBuilder) {
        super(formBuilder);
    }

    // MARK: Internal
    protected makeNewFormGroup(): FormGroup {
        return this._formBuilder.group({
            name: ['', [Validators.maxLength(24)]],
            date: [DateTime.local(), []],
            number: [0, []],
            numberList: [[], []],
            stringSet: [[], []],
        });
    }

    protected convertToFormData(model: Foo): any {
        const data: any = {};

        data.name = model.name;
        data.date = (model.date) ? model.date.toJSDate() : null;
        data.number = model.number;
        data.numberList = ValueUtility.normalizeArrayCopy(model.numberList);
        data.stringSet = ValueUtility.setToArray(model.stringSet);

        return data;
    }

    protected makeForSubmission(value: any): Foo {
        const model = new Foo();

        model.name = value.name;
        model.date = (value.date) ? DateTimeUtility.dateTimeFromInput(value.date) : undefined;
        model.number = value.number;
        model.numberList = ValueUtility.normalizeArrayCopy(value.numberList);
        model.stringSet = ValueUtility.arrayToSet(value.stringSet);

        return model;
    }

}
