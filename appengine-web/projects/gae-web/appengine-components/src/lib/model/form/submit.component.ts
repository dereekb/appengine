import { Component, EventEmitter, Input, Output, ViewEncapsulation, Inject, Directive, Optional, OnDestroy } from '@angular/core';

/**
 * Is linked to by a child GaeSubmitViewComponent.
 *
 * This allows calling submit from a context higher than the SubmitView's immediate context.
 */
@Directive({
    selector: '[gaeSubmitViewAction]',
    exportAs: 'gaeSubmitViewAction'
})
export class GaeSubmitViewActionDirective {

    private _submitView?: GaeSubmitViewComponent;

    public get disabled(): boolean {
        return (this._submitView) ? this._submitView.disabled : true;
    }

    public doSubmit() {
        if (this._submitView) {
            this._submitView.clicked();
        }
    }

    public set submitView(submitView: GaeSubmitViewComponent) {
        this._submitView = submitView;
    }

}

@Component({
    templateUrl: './submit.component.html',
    selector: 'gae-submit-view',
    styleUrls: ['./submit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class GaeSubmitViewComponent implements OnDestroy {

    public onClick = new EventEmitter();

    private _working = false;
    private _locked = false;
    private _disabled = false;

    @Input()
    public action = 'Submit';

    @Input()
    public lockedText = 'Locked!';

    @Input()
    public buttonClasses = 'button-primary-color';

    constructor(@Optional() @Inject(GaeSubmitViewActionDirective) parent: GaeSubmitViewActionDirective) {
        if (parent) {
            parent.submitView = this;
        }
    }

    ngOnDestroy() {
        this.onClick.complete();
    }

    get text() {
        return (this.locked) ? this.lockedText : this.action;
    }

    get working() {
        return this._working;
    }

    @Input()
    set working(working: boolean) {
        this._working = working;
    }

    get locked() {
        return this._locked;
    }

    @Input()
    set locked(locked: boolean) {
        this._locked = locked;
    }

    get enabled() {
        return !this.disabled;
    }

    set enabled(enabled: boolean) {
        this.disabled = !enabled;
    }

    get disabled() {
        return this._disabled || this._locked || this._working;
    }

    @Input()
    set disabled(disabled: boolean) {
        this._disabled = disabled;
    }

    // MARK: Click
    public clicked(): boolean {
        if (this.enabled) {
            this.onClick.emit();
            return true;
        }

        return false;
    }

}
