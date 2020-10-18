import { Directive, Input, EventEmitter, Output, OnDestroy } from '@angular/core';
import { LoginTokenPair } from '@gae-web/appengine-token';
import { Subscription, merge } from 'rxjs';
import { AbstractSignInGateway, SignInGateway, SignInGatewayEvent, SignInGatewayState } from './gateway';
import { SubscriptionObject } from '@gae-web/appengine-utility';

/**
 * Used for watching different sign in gateways.
 */
@Directive({
    selector: '[gaeSignInGatewayGroup]',
    exportAs: 'gaeSignInGatewayGroup'
})
export class SignInGatewayGroupDirective extends AbstractSignInGateway implements OnDestroy {

    private _sub = new SubscriptionObject();
    private _gateways: SignInGateway[];

    constructor() {
        super();
    }

    ngOnDestroy() {
        super.ngOnDestroy();
        this._sub.destroy();
    }

    // MARK: Accessors
    @Input()
    public set gaeSignInGatewayGroup(gateways: SignInGateway[]) {
        this._gateways = gateways;
        const streams = gateways.map((x) => x.stream);
        this._sub.subscription = merge(...streams).subscribe((x: SignInGatewayEvent) => {
            this.updateForGatewayEvent(x);
        });
    }

    // MAKR: Events
    protected updateForGatewayEvent(event: SignInGatewayEvent) {
        this.next(event);
    }

    // MARK: Internal
    public resetSignInGateway() {
        if (this._gateways) {
            this._gateways.forEach((x) => x.resetSignInGateway());
        }
    }

}


/**
 * Abstract directive that encapsulates a SignIn Gateway and emits login events when it fires.
 */
@Directive()
export abstract class AbstractSignInGatewayDirective extends AbstractSignInGateway implements OnDestroy {

    @Output()
    public readonly loginCompleted = new EventEmitter<LoginTokenPair>();

    private _gateway: SignInGateway;
    private _gatewaySub: Subscription;

    constructor() {
        super();
    }

    ngOnDestroy() {
        super.ngOnDestroy();
        this.loginCompleted.complete();
    }

    // MARK: Accessors
    public get isDone() {
        return this.state === SignInGatewayState.Done;
    }

    public get isWorking() {
        return this.state === SignInGatewayState.Working;
    }

    protected setGateway(gateway: SignInGateway) {
        this._clearSourceSub();

        this._gateway = gateway;

        if (gateway) {
            this._gatewaySub = gateway.stream.subscribe((event) => {
                this.updateForGatewayEvent(event);
            });
        }
    }

    // MARK: Handler
    protected updateForGatewayEvent(event: SignInGatewayEvent) {
        switch (event.state) {
            case SignInGatewayState.Done:
                this.updateWithLoginToken(event.token);
                break;
            case SignInGatewayState.Error:
                this.emitError(event.error);
                break;
            default:
                this.next(event);
        }
    }

    protected abstract updateWithLoginToken(loginToken: LoginTokenPair);

    protected nextLoginToken(loginTokenPair: LoginTokenPair) {
        super.nextLoginToken(loginTokenPair);
        this.emitLogin(loginTokenPair);
    }

    protected emitLogin(login: LoginTokenPair) {
        this.loginCompleted.emit(login);
    }

    protected emitErrorMessage(error: string) {
        this.emitError(new Error(error));
    }

    protected emitError(error: Error) {
        this.nextError(error);
    }

    // MARK: Internal
    public resetSignInGateway() {
        if (this._gateway) {
            this._gateway.resetSignInGateway();
        }
    }

    private _clearSourceSub() {
        if (this._gatewaySub) {
            this._gatewaySub.unsubscribe();
            delete this._gatewaySub;
        }
    }

}
