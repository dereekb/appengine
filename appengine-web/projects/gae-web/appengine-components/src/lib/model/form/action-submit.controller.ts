import { Input, OnDestroy } from '@angular/core';
import { GaeSubmitViewComponent } from './submit.component';
import { ActionState, ActionObject, ActionEvent } from '../../shared/action';
import { SubscriptionObject } from '@gae-web/appengine-utility';

/**
 * Abstract controller used to direct an action and submit button.
 */
export abstract class AbstractActionSubmitController implements OnDestroy {

    private _submit: GaeSubmitViewComponent;
    private _submitSub = new SubscriptionObject();

    private _action: ActionObject;
    private _actionSub = new SubscriptionObject();

    constructor() { }

    ngOnDestroy() {
        this._submitSub.destroy();
        this._actionSub.destroy();
    }

    // MARK: Accessors
    public get submit() {
        return this._submit;
    }

    @Input()
    public set submit(submit: GaeSubmitViewComponent) {
        if (submit) {
            this._submit = submit;
            this._submitSub.subscription = this._submit.onClick.subscribe(() => this.submitClicked());
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
        switch (event.state) {
            case ActionState.Working:
                this._submit.working = true;
                break;
            default:
                this._submit.working = false;
                break;
        }
    }

    // MARK: Functions
    protected resetAction() {
        if (this._action) {
            this._action.reset();
        }
    }

}

/**
 * Abstract component that wraps/contains a AbstractActionSubmitController.
 */
export abstract class AbstractActionObject {

    protected abstract get _controller();

    public get controller(): AbstractActionSubmitController {
        return this._controller;
    }

    public reset() {
        this._controller.resetAction();
    }

}
