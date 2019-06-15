import { Directive, Input } from '@angular/core';
import { LoginTokenPair, DecodedLoginToken, TokenType } from '@gae-web/appengine-token';
import { AbstractSignInGatewayDirective } from './gateway.directive';
import { SignInGateway } from './gateway';

/**
 * Used for watching another SignInGateway and emitting either a login event or error depending on the response.
 */
@Directive({
    selector: '[gaeSignInGateway]',
    exportAs: 'gaeSignInGateway'
})
export class SignInGatewayDirective extends AbstractSignInGatewayDirective {

    // MARK: Accessors
    public get disableLogins() {
        return this.isWorking || this.isDone;
    }

    @Input()
    public set gaeSignInGateway(gateway: SignInGateway) {
        this.setGateway(gateway);
    }

    // MARK: Source
    protected updateWithLoginToken(loginToken: LoginTokenPair) {
        const decoded: DecodedLoginToken = loginToken.decode();

        switch (decoded.type) {
            case TokenType.Registration:
                this.emitErrorMessage('An account with this login does not exist.');
                break;
            case TokenType.Full:
                this.nextLoginToken(loginToken);
                break;
            case TokenType.Refresh:
                throw new Error('Refresh token passed where not expected.');
        }
    }

}
