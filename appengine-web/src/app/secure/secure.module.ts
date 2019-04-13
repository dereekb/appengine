import { NgModule } from '@angular/core';
import { UIRouterModule } from '@uirouter/angular';
import { SECURE_STATES } from './secure.states';
import { GaeApiModule } from '@gae-web/appengine-api';
import { GaeClientModule } from '@gae-web/appengine-client';

export const ROUTER_CONFIG: any = {
  states: SECURE_STATES
};

@NgModule({
  imports: [
    // GAE Configurations
    GaeApiModule.forApp({
      version: 'v1',
      name: 'test'
    }),
    GaeClientModule.forApp(),
    // Routing
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class SecureModule { }
