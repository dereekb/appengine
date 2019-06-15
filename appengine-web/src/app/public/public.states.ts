import { Ng2StateDeclaration } from '@uirouter/angular';
import { PublicComponent } from './public.component';

export const publicState: Ng2StateDeclaration = {
  url: '/',
  name: 'public',
  component: PublicComponent,
};

export const PUBLIC_STATES: Ng2StateDeclaration[] = [
  publicState
];
