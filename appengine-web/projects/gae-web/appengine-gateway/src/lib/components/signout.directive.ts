import { Directive, Input, AfterContentInit } from '@angular/core';
import { UserLoginTokenService } from '@gae-web/appengine-token';

export type SignInGatewaySignOutState = 'init' | 'signedin' | 'working' | 'signedout';

/**
 * Directive used for signing out immediately.
 */
@Directive({
    selector: '[gaeSignInGatewaySignOut]',
    exportAs: 'gaeSignInGatewaySignOut'
})
export class SignInGatewaySignOutDirective implements AfterContentInit {

    @Input()
    public autoLogout = false;

    private _state: SignInGatewaySignOutState = 'init';

    constructor(private _service: UserLoginTokenService) { }

    ngAfterContentInit(): void {
        this._state = 'signedin';
    }

    // MARK: Accessors
    public get isDone() {
        return this._state === 'signedout';
    }

    public get state() {
        return this._state;
    }

    // MARK: Sign Out
    public logout() {
        this._state = 'working';

        this._service.logout().subscribe(() => {
            this._state = 'signedout';
        });
    }

}
