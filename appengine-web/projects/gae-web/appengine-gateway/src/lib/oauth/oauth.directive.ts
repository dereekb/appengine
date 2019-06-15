import { Directive, Input, OnDestroy } from '@angular/core';
import { Subscription, merge } from 'rxjs';
import { OAuthLoginApiService } from '@gae-web/appengine-api';
import { OAuthLoginServiceButton, OAuthButtonEvent, OAuthButtonState, OAuthLoginServiceTokenResponse } from '@gae-web/appengine-services';
import { timeout } from 'rxjs/operators';
import { AbstractSignInGateway, SignInGateway } from '../components/gateway';


// MARK: Handlers
/**
 * Used for watching OAuth Button events from a number of OAuthLoginServiceButtons.
 */
@Directive({
    selector: '[gaeOAuthButtonSignIn]'
})
export class OAuthButtonSignInDirective extends AbstractSignInGateway implements SignInGateway, OnDestroy {

    private _buttons: OAuthLoginServiceButton[];
    private _buttonSub: Subscription;

    constructor(private _loginService: OAuthLoginApiService) {
        super();
    }

    ngOnDestroy() {
        super.ngOnDestroy();
        this._clearButtonSub();
    }

    // MARK: Accessors
    @Input()
    public set gaeOAuthButtonSignIn(buttons: OAuthLoginServiceButton[]) {
        this._clearButtonSub();

        this._buttons = buttons;

        const buttonStreams = buttons.map(x => x.stream);
        this._buttonSub = merge(...buttonStreams).subscribe(event => this.updateForGatewayEvent(event));
    }

    // MAKR: Events
    protected updateForGatewayEvent(event: OAuthButtonEvent) {
        switch (event.state) {
            case OAuthButtonState.Working:
                this.nextWorking();
                break;
            case OAuthButtonState.Done:
                this.loadLoginToken(event.response);
                break;
            case OAuthButtonState.Error:
                this.nextError(event.error);
                break;
        }
    }

    private loadLoginToken(response: OAuthLoginServiceTokenResponse) {
        this.nextWorking(); // Keep working if not working.

        const service = response.service;
        const accessToken = response.token.accessToken;
        const obs = this._loginService.loginWithAccessToken(service, accessToken)
            .pipe(timeout(15000));
        this.setTokenSub(obs);
    }

    // MARK: Internal
    public resetSignInGateway() {
        if (this._buttons) {
            this._buttons.forEach((x) => x.resetButton());
        }

        super.resetSignInGateway();
    }

    private _clearButtonSub() {
        if (this._buttonSub) {
            this._buttonSub.unsubscribe();
        }
    }

}
