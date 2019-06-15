import { Ng2StateDeclaration, UIView } from '@uirouter/angular';

export const demoState: Ng2StateDeclaration = {
  parent: 'app',
  url: '/demo',
  name: 'demo',
  redirectTo: 'demo.model',
  component: UIView
};

export const DEMO_STATES: Ng2StateDeclaration[] = [
  demoState
];
