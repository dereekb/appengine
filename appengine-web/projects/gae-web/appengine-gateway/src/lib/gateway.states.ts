import { Ng2StateDeclaration } from '@uirouter/angular';
import { GatewayComponent } from './gateway.component';
import { GaeSignUpComponent } from './view/signup/signup.component';
import { GaeSignInComponent } from './view/signin/signin.component';
import { GaeSignOutComponent } from './view/signout/signout.component';

export const gatewayState: Ng2StateDeclaration = {
  name: 'gateway',
  redirectTo: 'signin',
  component: GatewayComponent,
};

export const signUpState: Ng2StateDeclaration = {
  parent: 'gateway',
  name: 'signup',
  url: '/app/signup',
  component: GaeSignUpComponent,
  data: {
    appRedirect: true
  }
};

export const signInState: Ng2StateDeclaration = {
  parent: 'gateway',
  name: 'signin',
  url: '/app/signin',
  component: GaeSignInComponent,
  data: {
    appRedirect: true
  }
};

export const signOutState: Ng2StateDeclaration = {
  parent: 'gateway',
  name: 'signout',
  url: '/app/signout',
  component: GaeSignOutComponent,
};

export const GATEWAY_STATES: Ng2StateDeclaration[] = [
  gatewayState,
  signUpState,
  signInState,
  signOutState
];
