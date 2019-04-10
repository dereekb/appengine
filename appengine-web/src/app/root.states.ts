import { Ng2StateDeclaration } from '@uirouter/angular';

export const publicFutureState: Ng2StateDeclaration = {
  name: 'public.**',
  url: '/public',
  loadChildren: './public/public.module#PublicModule'
};

export const secureFutureState: Ng2StateDeclaration = {
  name: '**',
  url: '/app',
  loadChildren: './secure/secure.module#SecureModule'
};

export const ROOT_STATES = [
  publicFutureState,
  secureFutureState
];
