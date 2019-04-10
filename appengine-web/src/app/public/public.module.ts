import { NgModule } from '@angular/core';
import { PublicComponent } from './public.component';
import { UIRouterModule } from '@uirouter/angular';
import { PUBLIC_STATES } from './public.states';

export const ROUTER_CONFIG: any = {
  states: PUBLIC_STATES
};

@NgModule({
  declarations: [PublicComponent],
  exports: [PublicComponent],
  imports: [
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class PublicModule { }
