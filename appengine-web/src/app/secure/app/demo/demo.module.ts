import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { ModelDemoModule } from './model/model.module';
import { DEMO_STATES } from './demo.states';

export const ROUTER_CONFIG: StatesModule = {
  states: DEMO_STATES
};

@NgModule({
  imports: [
    ModelDemoModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class DemoModule { }
