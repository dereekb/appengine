import { Ng2StateDeclaration } from '@uirouter/angular';

export const publicFutureState: Ng2StateDeclaration = {
  name: 'public.**',
  url: '/',
  loadChildren: () => import('./public/public.module').then(m => m.PublicModule)
};

export const secureFutureState: Ng2StateDeclaration = {
  name: 'app.**',
  url: '/app',
  redirectTo: 'app.home',
  loadChildren: () => import('./secure/secure.module').then(m => m.SecureModule)
};

export const ROOT_STATES = [
  publicFutureState,
  secureFutureState
];
