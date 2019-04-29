import { Directive, Input, OnInit, AfterContentInit, OnDestroy } from '@angular/core';
import { BehaviorSubject, Subscription, Observable } from 'rxjs';

import { FormGroup } from '@angular/forms';

import { FormErrors } from './form.component';
import { ThemePalette } from '@angular/material';

@Directive({
    selector: '[gaeFormGroupErrors]'
})
export class GaeFormGroupErrorsDirective implements OnDestroy {

    private _errorsSubject = new BehaviorSubject<FormErrors>({});
    private _sub?: Subscription;

    constructor() { }

    ngOnDestroy() {
        this._errorsSubject.complete();
        this._clearSubject();
    }

    public get formErrors(): Observable<FormErrors> {
        return this._errorsSubject;
    }

    @Input()
    public set gaeFormGroupErrors(error: Observable<FormErrors>) {
        this._clearSubject();

        this._sub = error.subscribe((e) => {
            this._errorsSubject.next(e);
        });
    }

    private _clearSubject() {
        if (this._sub) {
            this._sub.unsubscribe();
            delete this._sub;
        }
    }

}

/**
 * Abstract form control component.
 */
export abstract class AbstractFormControlComponent {

    @Input()
    public color: ThemePalette;

    @Input()
    public required = false;

    @Input()
    public disabled = false;

    @Input()
    public field: string;

    @Input()
    public hint: string;

    private _placeholder?: string;

    @Input()
    public get placeholder(): string {
        return (this._placeholder || this.field);
    }

    public set placeholder(placeholder: string) {
        this._placeholder = placeholder;
    }

}

export abstract class AbstractExtendedFormControlComponent extends AbstractFormControlComponent implements AfterContentInit, OnDestroy {

    @Input()
    public form: FormGroup;

    public error?: string;

    private _errorSub?: Subscription;

    constructor(private _errors: GaeFormGroupErrorsDirective) {
        super();
    }

    ngAfterContentInit() {
        this._errorSub = this._errors.formErrors.subscribe((errors) => {
            const error = errors[this.field];
            this.error = (error && error.length > 0) ? error : undefined;
        });
    }

    ngOnDestroy() {
        this._clearErrorSub();
    }

    public get hasError(): boolean {
        return this.error !== undefined;
    }

    public get hintMsg(): string | undefined {
        return this.error || this.hint;
    }

    // MARK: Internal
    private _clearErrorSub() {
        if (this._errorSub) {
            this._errorSub.unsubscribe();
            this._errorSub = undefined;
        }
    }

}

