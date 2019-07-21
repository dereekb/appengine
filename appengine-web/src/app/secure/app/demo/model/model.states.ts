import { Ng2StateDeclaration, Transition } from '@uirouter/angular';
import { ModelDemoComponent } from './model.component';
import { ModelListComponent } from './list/list.component';
import { ModelViewComponent } from './view/view.component';
import { SingleElementReadSource , ModelKey } from '@gae-web/appengine-utility';
import { Observable, of } from 'rxjs';
import { FooReadSourceFactory } from 'src/app/secure/shared/api/model/foo/foo.service';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { ModelInfoViewComponent } from './view/info.component';
import { ModelEditViewComponent } from './view/edit.component';

export const modelDemoState: Ng2StateDeclaration = {
  name: 'demo.model',
  redirectTo: 'demo.model.list',
  url: '/model',
  component: ModelDemoComponent
};

export const modelDemoListState: Ng2StateDeclaration = {
  name: 'demo.model.list',
  url: '/list',
  component: ModelListComponent
};

export function getFooModelKey(transition: Transition) {
  return transition.params().fooKey;
}

export function makeFooSource(fooKey: ModelKey, readSourceFactory: FooReadSourceFactory): SingleElementReadSource<Foo> {
  const source = readSourceFactory.makeSource();
  source.input = of(fooKey);
  return source;
}

export const modelDemoViewState: Ng2StateDeclaration = {
  name: 'demo.model.view',
  redirectTo: 'demo.model.view.info',
  url: '/{fooKey:int}',
  resolve: [
    { token: 'fooKey', deps: [Transition], resolveFn: getFooModelKey },
    { token: 'fooSource', deps: ['fooKey', FooReadSourceFactory], resolveFn: makeFooSource }
  ],
  component: ModelViewComponent
};

export const modelDemoViewInfoState: Ng2StateDeclaration = {
  url: '/info',
  name: 'demo.model.view.info',
  component: ModelInfoViewComponent
};

export const modelDemoEditInfoState: Ng2StateDeclaration = {
  url: '/edit',
  name: 'demo.model.view.edit',
  component: ModelEditViewComponent
};

export const MODEL_DEMO_STATES: Ng2StateDeclaration[] = [
  modelDemoState,
  modelDemoListState,
  modelDemoViewState,
  modelDemoViewInfoState,
  modelDemoEditInfoState
];
