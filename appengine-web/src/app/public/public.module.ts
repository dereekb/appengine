import { NgModule } from '@angular/core';
import { PublicComponent } from './public.component';
import { UIRouterModule } from '@uirouter/angular';
import { PUBLIC_STATES } from './public.states';
import { PublicImportsModule } from './public.imports';

export const ROUTER_CONFIG: any = {
  states: PUBLIC_STATES
};

@NgModule({
  declarations: [PublicComponent],
  imports: [
    PublicImportsModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class PublicModule { }
