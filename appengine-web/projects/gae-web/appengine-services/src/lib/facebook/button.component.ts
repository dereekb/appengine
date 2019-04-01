import { Directive } from '@angular/core';

import { FacebookOAuthService } from './auth.service';
import { AbstractOAuthLoginServiceButton } from '../shared/oauth.button.component';

@Directive({
  selector: '[gaeFacebookSignInButton]',
  exportAs: 'gaeFacebookSignInButton'
})
export class FacebookSignInButtonDirective extends AbstractOAuthLoginServiceButton {

  constructor(service: FacebookOAuthService) {
    super(service);
  }

}
