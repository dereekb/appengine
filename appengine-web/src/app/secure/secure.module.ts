import { NgModule } from '@angular/core';
import { UIRouterModule } from '@uirouter/angular';
import { SECURE_STATES } from './secure.states';
import { GaeApiModule } from 'projects/gae-web/appengine-api/src/public-api';
import { GaeAnalyticsModule } from 'projects/gae-web/appengine-analytics/src/public-api';

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
    // Routing
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class SecureModule { }
