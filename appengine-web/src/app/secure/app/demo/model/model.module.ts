import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { MODEL_DEMO_STATES } from './model.states';
import { CommonModule } from '@angular/common';
import { ModelListComponent } from './list/list.component';
import { ModelDemoComponent } from './model.component';
import { ModelViewComponent } from './view/view.component';
import { SecureComponentsModule } from 'src/app/secure/shared/components/components.module';
import { MatButtonModule } from '@angular/material';

export const ROUTER_CONFIG: StatesModule = {
  states: MODEL_DEMO_STATES
};

@NgModule({
  declarations: [
    ModelDemoComponent,
    ModelViewComponent,
    ModelListComponent
  ],
  imports: [
    CommonModule,
    SecureComponentsModule,
    MatButtonModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class ModelDemoModule { }
