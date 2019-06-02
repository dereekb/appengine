import { ViewChild, Component, Input, forwardRef } from '@angular/core';

import { OAuthButtonSignInDirective } from './oauth.directive';
import { SignInGateway } from '../components/gateway';

@Component({
    selector: 'gae-oauth-sign-in-gateway',
    templateUrl: './oauth.component.html'
})
export class OAuthSignInGatewayComponent implements SignInGateway {

    @Input()
    public disabled = false;

    @ViewChild(forwardRef(() => OAuthButtonSignInDirective), {static: true})
    public readonly buttonHandler: OAuthButtonSignInDirective;

    constructor() { }

    // MARK: SignInGateway
    public get state() {
        return this.buttonHandler.state;
    }

    public get stream() {
        return this.buttonHandler.stream;
    }

    public resetSignInGateway() {
        this.buttonHandler.resetSignInGateway();
    }

}
