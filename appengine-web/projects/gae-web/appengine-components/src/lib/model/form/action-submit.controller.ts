import { Input, OnDestroy } from '@angular/core';
import { GaeSubmitComponent } from './submit.component';
import { ActionState, ActionObject, ActionEvent } from '../../shared/action';
import { SubscriptionObject } from '@gae-web/appengine-utility';

/**
 * Abstract controller used to direct an action and submit button.
 */
export abstract class ActionSubmitController implements OnDestroy {

    private _submit: GaeSubmitComponent;
    private _submitSub = new SubscriptionObject();

    private _action: ActionObject;
    private _actionSub = new SubscriptionObject();

    private _error?: any;

    constructor() { }

    ngOnDestroy() {
        this._submitSub.destroy();
        this._actionSub.destroy();
    }

    // MARK: Accessors
    public get error() {
        return this._error;
    }

    public get submit(): GaeSubmitComponent {
        return this._submit;
    }

    @Input()
    public set submit(submit: GaeSubmitComponent) {
        if (submit) {
            this._submit = submit;
            this._submitSub.subscription = this._submit.submitClicked.subscribe(() => this.submitClicked());
        }
    }

    protected submitClicked() {
        if (this._action && this._action.canWork) {
            this.doSubmit();
        }
    }

    protected abstract doSubmit(): void;

    public get action() {
        return this._action;
    }

    @Input()
    public set action(action: ActionObject) {
        this.setAction(action);
    }

    protected setAction(action: ActionObject) {
        if (action) {
            this._action = action;
            this._actionSub.subscription = action.stream.subscribe((event) => this.updateForActionEvent(event));
        }
    }

    protected updateForActionEvent(event: ActionEvent) {
        this._error = event.error;

        switch (event.state) {
            case ActionState.Working:
                this._submit.isWorking = true;
                break;
            default:
                this._submit.isWorking = false;
                break;
        }
    }

    // MARK: Functions
    protected resetAction() {
        if (this._action && this._action.state !== ActionState.Reset) {
            this._action.reset();
        }
    }

}

/**
 * Abstract component that wraps/contains a ActionSubmitController.
 */
export abstract class AbstractActionObject {

    protected abstract get _controller();

    public get controller(): ActionSubmitController {
        return this._controller;
    }

    public reset() {
        this._controller.resetAction();
    }

}
