import { Component, EventEmitter, Input, Output, ViewEncapsulation, Inject, Directive, Optional, OnDestroy, Type, ChangeDetectorRef, AfterViewInit } from '@angular/core';

import { MatProgressButtonOptions } from 'mat-progress-buttons';
import { ProgressSpinnerMode } from '@angular/material/progress-spinner';
import { GaeViewUtility } from '../../shared/utility';

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
export class GaeSubmitViewActionDirective implements AfterViewInit {

    private _initialized = false;
    private _submitView?: GaeSubmitComponent;

    constructor(@Inject(ChangeDetectorRef) protected readonly _cdRef: ChangeDetectorRef) { }

    public get disabled(): boolean {
        return (this._submitView) ? this._submitView.isDisabled : true;
    }

    public get locked(): boolean {
        return (this._submitView) ? this._submitView.isLocked : true;
    }

    public get working(): boolean {
        return (this._submitView) ? this._submitView.isWorking : false;
    }

    ngAfterViewInit() {
        this._initialized = true;

        if (this._submitView) {
            GaeViewUtility.safeDetectChanges(this._cdRef);
        }
    }

    public doSubmit() {
        if (this._submitView) {
            this._submitView.submit();
        }
    }

    public set submitView(submitView: GaeSubmitComponent) {
        if (submitView !== this._submitView) {
            this._submitView = submitView;

            if (this._initialized) {
                GaeViewUtility.safeDetectChanges(this._cdRef);
            }
        }
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

@Directive()
export abstract class GaeAbstractSubmitComponent implements OnDestroy {

    @Output()
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

    /**
     * Convenience input for isWorking
     */
    @Input()
    set working(working: boolean) {
        this.isWorking = working;
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

    /**
     * Convenience input for disabled
     */
    @Input()
    set disabled(disabled: boolean) {
        this.isDisabled = disabled;
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
    public spinnerSize = 17;

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
            spinnerSize: this.spinnerSize,
            // Only disabled if we're not working, in order to show the animation.
            disabled: !this.working && this.disabled
        };
    }

    public get buttonType(): GaeSubmitButtonType {
        return this.spinner ? GaeSubmitButtonType.Spinner : GaeSubmitButtonType.Bar;
    }

}
