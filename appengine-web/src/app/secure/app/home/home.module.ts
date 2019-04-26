import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { HomeComponent } from './home.component';
import { HOME_STATES } from './home.states';

export const ROUTER_CONFIG: StatesModule = {
  states: HOME_STATES
};

@NgModule({
  declarations: [HomeComponent],
  imports: [
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class HomeModule { }
