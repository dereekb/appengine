import { Ng2StateDeclaration } from '@uirouter/angular';

export const publicFutureState: Ng2StateDeclaration = {
  name: 'public.**',
  url: '/',
  loadChildren: './public/public.module#PublicModule'
};

export const secureFutureState: Ng2StateDeclaration = {
  name: 'app.**',
  url: '/app',
  redirectTo: 'app.home',
  loadChildren: './secure/secure.module#SecureModule'
};

export const ROOT_STATES = [
  publicFutureState,
  secureFutureState
];
