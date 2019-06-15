import { Ng2StateDeclaration } from '@uirouter/angular';
import { HomeComponent } from './home.component';

export const homeState: Ng2StateDeclaration = {
  parent: 'app',
  url: '/home',
  name: 'home',
  component: HomeComponent
};

export const HOME_STATES: Ng2StateDeclaration[] = [
  homeState
];
