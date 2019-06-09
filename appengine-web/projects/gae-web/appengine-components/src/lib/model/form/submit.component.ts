import { Component, EventEmitter, Input, Output, ViewEncapsulation, Inject, Directive, Optional, OnDestroy, Type } from '@angular/core';

import { MatProgressButtonOptions } from 'mat-progress-buttons';
import { ProgressSpinnerMode } from '@angular/material/progress-spinner';

/**
 * Is linked to by a child GaeSubmitViewComponent.
 *
 * This allows calling submit from a context higher than the SubmitView's immediate context.
 *
 * This component can only remotely hit submit though, and does not have access to the result of the submit.
 */
@Directive({
    selector: '[gaeSubmitViewAction]',
    exportAs: 'gaeSubmitViewAction'
})
export class GaeSubmitViewActionDirective {

    private _submitView?: GaeSubmitComponent;

    public get disabled(): boolean {
        return (this._submitView) ? this._submitView.isDisabled : true;
    }

    public get locked(): boolean {
        return (this._submitView) ? this._submitView.isLocked : true;
    }

    public get working(): boolean {
        return (this._submitView) ? this._submitView.isWorking : false;
    }

    public doSubmit() {
        if (this._submitView) {
            this._submitView.submit();
        }
    }

    public set submitView(submitView: GaeSubmitComponent) {
        this._submitView = submitView;
    }

}

export abstract class GaeSubmitComponent {
    isDisabled: boolean;
    isWorking: boolean;
    isLocked: boolean;

    readonly text: string;
    readonly submitClicked: EventEmitter<{}>;
    abstract submit(): boolean;
}

export function ProvideGaeSubmitComponent<S extends GaeSubmitComponent>(listViewType: Type<S>) {
    return [{ provide: GaeSubmitComponent, useExisting: listViewType }];
}

export abstract class GaeAbstractSubmitComponent implements OnDestroy {

    public readonly submitClicked = new EventEmitter<{}>();

    private _working = false;
    private _locked = false;
    private _disabled = false;

    @Input()
    public raised = false;

    @Input()
    public spinner = false;

    @Input()
    public mode: ProgressSpinnerMode = 'indeterminate';

    @Input()
    public text = 'Submit';

    @Input()
    public lockedText = 'Locked!';

    constructor(@Optional() @Inject(GaeSubmitViewActionDirective) parent: GaeSubmitViewActionDirective) {
        if (parent) {
            parent.submitView = this;
        }
    }

    ngOnDestroy() {
        this.submitClicked.complete();
    }

    get buttonText() {
        return (this.isLocked) ? this.lockedText : this.text;
    }

    get isWorking() {
        return this._working;
    }

    @Input()
    set isWorking(working: boolean) {
        this._working = working;
    }

    get isLocked() {
        return this._locked;
    }

    @Input()
    set isLocked(locked: boolean) {
        this._locked = locked;
    }

    get isEnabled() {
        return !this.isDisabled;
    }

    get isDisabled() {
        return this._disabled || this._locked || this._working;
    }

    @Input()
    set isDisabled(disabled: boolean) {
        this._disabled = disabled;
    }

    // MARK: Click
    public submit(): boolean {
        if (!this.isDisabled) {
            this.submitClicked.emit();
            return true;
        }

        return false;
    }

}

export enum GaeSubmitButtonType {
    Bar = 0,
    Spinner = 1
}

@Component({
    selector: 'gae-submit-button',
    template: `
    <ng-container [ngSwitch]="buttonType">
        <mat-bar-button *ngSwitchCase="0" (btnClick)="submit()" [options]="btnOptions"></mat-bar-button>
        <mat-spinner-button *ngSwitchCase="1" (btnClick)="submit()" [options]="btnOptions"></mat-spinner-button>
    </ng-container>
    `,
    providers: [ProvideGaeSubmitComponent(GaeSubmitButtonComponent)]
})
export class GaeSubmitButtonComponent extends GaeAbstractSubmitComponent {

    @Input()
    public raised = false;

    @Input()
    public spinner = false;

    @Input()
    public mode: ProgressSpinnerMode = 'indeterminate';

    public get btnOptions(): MatProgressButtonOptions {
        return {
            active: this.isWorking,
            text: this.buttonText,
            buttonColor: 'accent',
            barColor: 'accent',
            raised: this.raised,
            stroked: true,
            flat: false,
            mode: this.mode,
            disabled: this.isDisabled
        };
    }

    public get buttonType(): GaeSubmitButtonType {
        return this.spinner ? GaeSubmitButtonType.Spinner : GaeSubmitButtonType.Bar;
    }

}
