import { Ng2StateDeclaration } from '@uirouter/angular';
import { AppComponent } from './app.component';

export const appState: Ng2StateDeclaration = {
  url: '/app',
  name: 'app',
  component: AppComponent,
  data: {
    isSecure: true
  }
};

export const APP_STATES: Ng2StateDeclaration[] = [
  appState
];
