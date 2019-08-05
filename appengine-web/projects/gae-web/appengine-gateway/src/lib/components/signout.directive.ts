import { Directive, Input, AfterContentInit, ChangeDetectorRef } from '@angular/core';
import { UserLoginTokenService } from '@gae-web/appengine-token';
import { map, first } from 'rxjs/operators';
import { Observable, of } from 'rxjs';
import { GaeViewUtility } from '@gae-web/appengine-components';

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

    constructor(private _service: UserLoginTokenService, private _cdRef: ChangeDetectorRef) { }

    ngAfterContentInit(): void {
        this._state = 'signedin';

        if (this.autoLogout) {
            this.logout();
        }
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

        this._service.isAuthenticated().pipe(
            map((isAuthenticated) => {
                if (isAuthenticated) {
                    return this._service.logout();
                } else {
                    return of(true);
                }
            }),
            first() // First
        ).subscribe(() => {
            this._state = 'signedout';
            GaeViewUtility.safeDetectChanges(this._cdRef);
        });
    }

}
