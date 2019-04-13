import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UIRouterModule } from '@uirouter/angular';
import { GatewayComponent } from './gateway.component';
import { GATEWAY_STATES } from './gateway.states';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GaeGatewayViewsModule } from './view/view.module';

// TODO: as Any temporary to override compiler issues with ui router.)
export const ROUTER_CONFIG: any = {
  states: GATEWAY_STATES
};

@NgModule({
  declarations: [
    GatewayComponent
  ],
  exports: [
    GatewayComponent
  ],
  imports: [
    CommonModule,
    FlexLayoutModule,
    GaeGatewayViewsModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class GaeGatewayModule { }
