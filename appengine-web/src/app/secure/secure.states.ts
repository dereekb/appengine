import { Ng2StateDeclaration } from '@uirouter/angular';

export const gatewayFutureState: Ng2StateDeclaration = {
  name: 'gateway.**',
  url: '/gateway',
  redirectTo: 'signin',
  loadChildren: './gateway/gateway.module#GatewayModule'
};

export const appFutureState: Ng2StateDeclaration = {
  name: 'app.**',
  url: '/app',
  loadChildren: './app/app.module#AppModule'
};

export const SECURE_STATES = [
  gatewayFutureState,
  appFutureState
];
