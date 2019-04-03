import { Ng2StateDeclaration } from '@uirouter/angular';
import { GatewayComponent } from './gateway.component';
import { SignUpComponent } from './signup/signup.component';
import { SignInComponent } from './signin/signin.component';
import { SignOutComponent } from './signout/signout.component';

export const gatewayState: Ng2StateDeclaration = {
  name: 'gateway',
  redirectTo: 'signin',
  component: GatewayComponent,
};

export const signUpState: Ng2StateDeclaration = {
  parent: 'auth',
  name: 'signup',
  url: '/signup',
  component: SignUpComponent,
  data: {
    appRedirect: true
  }
};

export const signInState: Ng2StateDeclaration = {
  parent: 'auth',
  name: 'signin',
  url: '/signin',
  component: SignInComponent,
  data: {
    appRedirect: true
  }
};

export const signOutState: Ng2StateDeclaration = {
  parent: 'auth',
  name: 'signout',
  url: '/signout',
  component: SignOutComponent,
};

export const GATEWAY_STATES: Ng2StateDeclaration[] = [
  gatewayState,
  signUpState,
  signInState,
  signOutState
];
