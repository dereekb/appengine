import { Directive, Input, OnDestroy, AfterContentInit } from '@angular/core';
import { SignInGateway, SignInGatewayState } from './gateway';
import { UserLoginTokenService, LoginTokenPair } from '@gae-web/appengine-token';
import { GatewaySegueService } from '../state.service';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';

/**
 * Directive used to handle successful logins.
 */
@Directive({
    selector: '[gaeSignInGatewaySuccess]',
    exportAs: 'gaeSignInGatewaySuccess'
})
export class SignInGatewaySuccessDirective implements OnDestroy {

    @Input()
    public redirect = true;

    @Input()
    public stayLoggedIn = true;

    @Input()
    public newUserLogin = false;

    private _sub: Subscription;

    constructor(private _service: UserLoginTokenService, private _segueService: GatewaySegueService) {}

    ngOnDestroy(): void {
        this._clearSub();
    }

    @Input()
    public set gaeSignInGatewaySuccess(gateway: SignInGateway) {
        this._sub = gateway.stream.pipe(
            filter((x) => x.state === SignInGatewayState.Done)
        ).subscribe((event) => {
            this.login(event.token);
        });
    }

    // MARK: Login
    protected login(token: LoginTokenPair) {
        const sub = this._service.login(token, this.stayLoggedIn)
            .subscribe((result) => {

                if (this.redirect) {
                    this.redirectToApp();
                }

                sub.unsubscribe();
            }, (error) => {
                console.log('Error occured while logging in: ' + error);
            });
    }

    protected redirectToApp() {
        if (this.newUserLogin) {
            this._segueService.segueToOnboarding();
        } else {
            this._segueService.segueToApp();
        }
    }

    // MARK: Internal
    private _clearSub() {
        if (this._sub) {
            this._sub.unsubscribe();
            delete this._sub;
        }
    }

}
