import { Component, Input, AfterContentInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { SignInGateway, SignInGatewayState, SignInGatewayEvent } from './gateway';

@Component({
    selector: 'gae-login-state-view',
    templateUrl: './login.component.html'
})
export class LoginStateViewComponent implements OnDestroy, AfterContentInit {

    private _initialized = false;

    private _sub: Subscription;
    private _source: SignInGateway;

    private _state = SignInGatewayState.Idle;
    private _error: Error;

    ngAfterContentInit() {
        this._bindToSource();
        this._initialized = true;
    }

    ngOnDestroy(): void {
        this._clearSub();
    }

    // MARK: Accessors
    public get state() {
        return this._state;
    }

    public get isWorking() {
        return this.state === SignInGatewayState.Working;
    }

    public get isDone() {
        return this.state === SignInGatewayState.Done;
    }

    public get error() {
        return this._error;
    }

    // MARK: SignInGateway
    @Input()
    public set source(source: SignInGateway) {
        this._source = source;

        if (this._initialized) {
            this._bindToSource();
        }
    }

    // MARK: Internal
    protected _bindToSource() {
        if (this._source) {
            this._clearSub();

            this._sub = this._source.stream.subscribe((event) => {
                this.updateForGatewayEvent(event);
            });
        }
    }

    protected updateForGatewayEvent(event: SignInGatewayEvent) {
        this._state = event.state;
        this._error = event.error;
    }

    private _clearSub() {
        if (this._sub) {
            this._sub.unsubscribe();
            delete this._sub;
        }
    }

}
