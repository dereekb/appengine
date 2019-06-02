import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { MODEL_DEMO_STATES } from './model.states';
import { CommonModule } from '@angular/common';
import { ModelListComponent } from './list/list.component';
import { ModelDemoComponent } from './model.component';
import { ModelViewComponent } from './view/view.component';
import { SecureComponentsModule } from 'src/app/secure/shared/components/components.module';
import { MatButtonModule } from '@angular/material/button';
import { ModelInfoViewComponent } from './view/info.component';
import { ModelEditViewComponent } from './view/edit.component';
import { FlexLayoutModule } from '@angular/flex-layout';

export const ROUTER_CONFIG: StatesModule = {
  states: MODEL_DEMO_STATES
};

@NgModule({
  declarations: [
    ModelDemoComponent,
    ModelViewComponent,
    ModelInfoViewComponent,
    ModelEditViewComponent,
    ModelListComponent
  ],
  imports: [
    CommonModule,
    SecureComponentsModule,
    FlexLayoutModule,
    MatButtonModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class ModelDemoModule { }
