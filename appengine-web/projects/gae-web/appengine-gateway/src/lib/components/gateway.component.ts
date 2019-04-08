import { Component, Input, AfterContentInit, OnDestroy, ViewChild, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { SignInGateway, SignInGatewayState, SignInGatewayEvent } from './gateway';
import { SimpleLoadingContext, AbstractSubscriptionComponent } from '@gae-web/appengine-components';
import { LoginTokenPair } from '@gae-web/appengine-token';

@Component({
    selector: 'gae-sign-in-gateway-view',
    templateUrl: './gateway.component.html'
})
export class GaeSignInGatewayViewComponent extends AbstractSubscriptionComponent implements AfterViewInit, AfterContentInit {

    private _initialized = false;
    private _context = new SimpleLoadingContext(false);

    private _gateway: SignInGateway;
    private _token: LoginTokenPair;

    @ViewChild('success') customSuccessContent;

    private _hasCustomSuccess;

    constructor(private cdRef: ChangeDetectorRef) {
        super();
    }

    ngAfterContentInit() {
        this._bindToSource();
        this._initialized = true;
    }

    ngAfterViewInit() {
        this._hasCustomSuccess = Boolean(this.customSuccessContent);
        this.cdRef.detectChanges();
    }

    // MARK: Accessors
    public get context() {
        return this._context;
    }

    public get token() {
        return this._token;
    }

    public get showSignIn() {
        return !this._token;
    }

    public get showSuccess() {
        return Boolean(this._token);
    }

    public get hasCustomSuccess() {
        return this._hasCustomSuccess;
    }

    // MARK: SignInGateway
    @Input()
    public set gateway(gateway: SignInGateway) {
        this._gateway = gateway;

        if (this._initialized) {
            this._bindToSource();
        }
    }

    // MARK: Internal
    protected _bindToSource() {
        if (this._gateway) {
            this.sub = this._gateway.stream.subscribe((event) => {
                switch (event.state) {
                    case SignInGatewayState.Idle:
                        this._context.setLoading(false);
                        this._context.clearError();
                        break;
                    case SignInGatewayState.Working:
                        this._context.setLoading(true);
                        this._token = undefined;
                        break;
                    case SignInGatewayState.Done:
                        this._context.setLoading(false);
                        this._token = event.token;
                        break;
                    case SignInGatewayState.Error:
                        this._context.setError(event.error);
                        break;
                }
            });
        }
    }

}
