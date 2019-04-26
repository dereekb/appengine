import { Ng2StateDeclaration } from '@uirouter/angular';
import { ModelDemoComponent } from './model.component';
import { ModelListComponent } from './list/list.component';
import { ModelViewComponent } from './view/view.component';

export const modelDemoState: Ng2StateDeclaration = {
  url: '/model',
  name: 'demo.model',
  redirectTo: 'demo.model.list',
  component: ModelDemoComponent
};

export const modelDemoListState: Ng2StateDeclaration = {
  url: '/list',
  name: 'demo.model.list',
  component: ModelListComponent
};

export const modelDemoViewState: Ng2StateDeclaration = {
  url: '/view',
  name: 'demo.model.view',
  component: ModelViewComponent
};

export const MODEL_DEMO_STATES: Ng2StateDeclaration[] = [
  modelDemoState,
  modelDemoListState,
  modelDemoViewState
];
