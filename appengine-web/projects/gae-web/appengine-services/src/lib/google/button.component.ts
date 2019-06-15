import { Directive } from '@angular/core';

import { GoogleOAuthService } from './auth.service';
import { AbstractOAuthLoginServiceButton } from '../shared/oauth.button.component';

@Directive({
  selector: '[gaeGoogleSignInButton]',
  exportAs: 'gaeGoogleSignInButton'
})
export class GoogleSignInButtonDirective extends AbstractOAuthLoginServiceButton {

  constructor(service: GoogleOAuthService) {
    super(service);
  }

}
