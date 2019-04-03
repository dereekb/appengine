import { Directive, Input, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { OAuthLoginApiService } from '@gae-web/appengine-api';
import { OAuthLoginServiceButton, OAuthButtonEvent, OAuthButtonState, OAuthLoginServiceTokenResponse } from '@gae-web/appengine-services';
import { timeout } from 'rxjs/operators';
import { AbstractSignInGateway, SignInGateway } from './gateway';


// MARK: Handlers
/**
 * Used for watching OAuth Button events from a number of OAuthLoginServiceButtons.
 */
@Directive({
    selector: '[gaeOAuthButtonSignIn]'
})
export class OAuthButtonSignInDirective extends AbstractSignInGateway implements SignInGateway, OnDestroy {

    private _buttons: OAuthLoginServiceButton[];
    private _subs: Subscription[];

    constructor(private _loginService: OAuthLoginApiService) {
        super();
    }

    ngOnDestroy() {
        super.ngOnDestroy();
        this._clearSubs();
    }

    // MARK: Accessors
    @Input()
    public set gaeOAuthButtonSignIn(buttons: OAuthLoginServiceButton[]) {
        this._clearSubs();

        this._buttons = buttons;

        this._subs = buttons.map((button) => {
            return button.stream.subscribe((event) => this.updateForGatewayEvent(event));
        });
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

    private _clearSubs() {
        if (this._subs) {
            this._subs.forEach((x) => x.unsubscribe());
        }
    }

}
