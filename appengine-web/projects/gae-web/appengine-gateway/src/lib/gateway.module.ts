import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UIRouterModule } from '@uirouter/angular';
import { GatewayComponent } from './gateway.component';
import { GaeGoogleModule, GaeFacebookModule } from '@gae-web/appengine-services';
import { GaeApiModule } from '@gae-web/appengine-api';
import { GATEWAY_STATES } from './gateway.states';
import { GaeGatewayMaterialModule } from './material.module';
import { FlexLayoutModule } from '@angular/flex-layout';
import { GaeGatewayComponentsModule } from './components/components.module';
import { GaeGatewayViewsModule } from './view/view.module';

// TODO: as Any temporary to override compiler issues with ui router.)
export const ROUTER_CONFIG: any = {
  states: GATEWAY_STATES,
  // config: routerConfigFn // TODO: Add back later for protecting app?
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
export class GatewayModule { }
