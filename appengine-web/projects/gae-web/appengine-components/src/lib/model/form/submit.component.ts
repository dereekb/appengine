import { Component, EventEmitter, Input, Output, ViewEncapsulation } from '@angular/core';

@Component({
    templateUrl: './submit.component.html',
    selector: 'gae-submit-view',
    styleUrls: ['./submit.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class GaeSubmitViewComponent {

    public onClick = new EventEmitter<{}>();

    private _working = false;
    private _locked = false;
    private _disabled = false;

    @Input()
    public action = 'Submit';

    @Input()
    public lockedText = 'Locked!';

    @Input()
    public buttonClasses = 'button-primary-color';

    constructor() { }

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
