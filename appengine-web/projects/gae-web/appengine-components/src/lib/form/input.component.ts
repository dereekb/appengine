import { Input, Component, ViewEncapsulation, ViewChild } from '@angular/core';

import { MatDatepicker, MatCheckbox } from '@angular/material';

import { GaeFormGroupErrorsDirective, AbstractExtendedFormControlComponent } from './control.component';
import { Observable } from 'rxjs';

export interface SelectOption {
    readonly label: string;
    readonly value: any;
}

@Component({
    template: `
    <div class="mat-form-field select-form-control">
        <div class="mat-input-wrapper" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }">
            <mat-select class="form-view-control" [formControlName]="field" [placeholder]="placeholder">
                <mat-option *ngFor="let option of options | async" [value]="option.value">{{ option.label }}</mat-option>
            </mat-select>
        </div>
    </div>
    `,
    selector: 'gae-select-form-control',
    encapsulation: ViewEncapsulation.None
})
export class SelectFieldFormControlComponent extends AbstractExtendedFormControlComponent {

    @Input()
    public options: Observable<SelectOption>;

    constructor(errors: GaeFormGroupErrorsDirective) {
        super(errors);
    }

}

@Component({
    template: `
    <mat-form-field class="form-view-control date-form-control" [formGroup]="form"
        [ngClass]="{ warning: hasError, required: required }" [color]="color">
        <mat-datepicker #picker [touchUi]="touch"></mat-datepicker>
        <input matInput [formControlName]="field" [matDatepicker]="picker" [placeholder]="placeholder"/>
        <mat-datepicker-toggle class="form-suffix-button" matSuffix [for]="picker"></mat-datepicker-toggle>
        <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
    </mat-form-field>
    `,
    selector: 'gae-date-form-control',
    encapsulation: ViewEncapsulation.None
})
export class DateFieldFormControlComponent extends AbstractExtendedFormControlComponent {

    @ViewChild(MatDatepicker)
    public readonly picker: MatDatepicker<Date>;

    @Input()
    public touch: boolean;

    constructor(errors: GaeFormGroupErrorsDirective) {
        super(errors);
    }

    // Controls
    public open() {
        this.picker.open();
    }

    public close() {
        this.picker.close();
    }

}

@Component({
    template: `
    <mat-form-field class="form-view-control" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }" [color]="color">
        <input type="{{type}}" matInput [formControlName]="field" [placeholder]="placeholder"/>
        <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
        <span matPrefix><ng-content select="[inputPrefix]"></ng-content></span>
        <span matSuffix><ng-content select="[inputSuffix]"></ng-content></span>
    </mat-form-field>
    `,
    selector: 'gae-input-form-control',
    encapsulation: ViewEncapsulation.None
})
export class InputFieldFormControlComponent extends AbstractExtendedFormControlComponent {

    @Input()
    public type = 'text';

    constructor(errors: GaeFormGroupErrorsDirective) {
        super(errors);
    }

}

@Component({
    template: `
    <mat-form-field class="form-view-control" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }" [color]="color">
        <input type="{{type}}" matInput [formControlName]="field" [matAutocomplete]="autoCompleteView" [placeholder]="placeholder"/>
        <mat-autocomplete #autoCompleteView="matAutocomplete">
            <mat-option *ngFor="let option of autoCompleteOptions | async" [value]="option">{{ option }}</mat-option>
        </mat-autocomplete>
        <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
    </mat-form-field>
    `,
    selector: 'gae-auto-complete-input-form-control',
    encapsulation: ViewEncapsulation.None
})
export class AutoCompleteInputFieldFormControlComponent extends InputFieldFormControlComponent {

    @Input()
    public autoCompleteOptions: Observable<any>;

    constructor(errors: GaeFormGroupErrorsDirective) {
        super(errors);
    }

}

@Component({
    template: `
    <mat-form-field class="form-view-control" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }" [color]="color">
        <textarea matInput [formControlName]="field" [placeholder]="placeholder"></textarea>
        <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
    </mat-form-field>
    `,
    selector: 'gae-textarea-form-control',
    encapsulation: ViewEncapsulation.None
})
export class TextAreaFormControlComponent extends AbstractExtendedFormControlComponent {

    constructor(errors: GaeFormGroupErrorsDirective) {
        super(errors);
    }

}

@Component({
    template: `
    <div class="form-view-control form-view-checkbox-control" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }">
        <mat-checkbox class="form-view-checkbox" [formControlName]="field" [labelPosition]="labelPosition" [color]="color">
        {{ placeholder }}
        </mat-checkbox>
        <p class="form-view-hint" *ngIf="hintMsg">{{ hintMsg }}</p>
    </div>
    `,
    selector: 'gae-checkbox-form-control',
    encapsulation: ViewEncapsulation.None
})
export class CheckboxFormControlComponent extends AbstractExtendedFormControlComponent {

    @ViewChild(MatCheckbox)
    private _checkbox: MatCheckbox;

    @Input()
    public labelPosition: 'before' | 'after' = 'before';

    constructor(errors: GaeFormGroupErrorsDirective) {
        super(errors);
    }

    public get checked() {
        return this._checkbox.checked;
    }

    public set checked(checked) {
        this._checkbox.checked = checked;
    }

}
