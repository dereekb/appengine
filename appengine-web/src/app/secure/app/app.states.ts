import { Ng2StateDeclaration, UIView } from '@uirouter/angular';
import { AppComponent } from './app.component';
import { AppSidenavComponent } from './sidenav.component';

export const appState: Ng2StateDeclaration = {
  url: '/app',
  name: 'app',
  redirectTo: 'home',
  component: AppComponent,
  views: {
    $default: {
      component: AppComponent
    },
    '$default.content': {
      component: UIView
    },
    '$default.sidenav': {
      component: AppSidenavComponent
    }
  },
  data: {
    isSecure: true
  }
};

export const APP_STATES: Ng2StateDeclaration[] = [
  appState
];
