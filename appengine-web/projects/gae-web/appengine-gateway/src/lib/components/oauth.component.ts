import { ViewChild, Component, Input } from '@angular/core';

import { OAuthButtonSignInHandlerDirective } from './oauth.directive';
import { SignInGateway } from './gateway';

@Component({
    selector: 'gae-oauth-login-view',
    templateUrl: './oauth.component.html'
})
export class OAuthLoginViewComponent implements SignInGateway {

    @Input()
    public disabled = false;

    @ViewChild(OAuthButtonSignInHandlerDirective)
    public readonly buttonHandler: OAuthButtonSignInHandlerDirective;

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
