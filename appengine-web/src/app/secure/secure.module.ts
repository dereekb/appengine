import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule, UIRouter, Category, StateService } from '@uirouter/angular';
import { GaeApiModule } from '@gae-web/appengine-api';
import { GaeClientModule } from '@gae-web/appengine-client';
import { SECURE_STATES } from './secure.states';
import { SecureAppModule } from './app/app.module';
import { GaeTokenModule } from '@gae-web/appengine-token';
import { AppSegueService } from './segue.service';
import { GaeGatewayModule, secureGatewayHook, GaeGatewayViewsModule, GatewaySegueService } from 'projects/gae-web/appengine-gateway/src/public-api';

export function routerConfigFn(router: UIRouter) {
  const transitionService = router.transitionService;

  secureGatewayHook(transitionService);

  router.trace.enable(Category.TRANSITION);
}

export const ROUTER_CONFIG: StatesModule = {
  config: routerConfigFn,
  states: SECURE_STATES
};

@NgModule({
  imports: [
    GaeGatewayModule,
    SecureAppModule,
    // GAE Configurations
    GaeGatewayViewsModule.forRoot({}),
    GaeTokenModule.forRoot(),
    GaeApiModule.forApp({
      version: 'v1',
      name: 'test'
    }),
    GaeApiModule.makeJwtModuleForRoot(),
    GaeClientModule.forApp(),
    // Routing
    UIRouterModule.forChild(ROUTER_CONFIG)
  ],
  providers: [
    // Configurations
    {
      provide: AppSegueService,
      deps: [StateService]
    },
    {
      provide: GatewaySegueService,
      useExisting: AppSegueService
    }
  ]
})
export class SecureModule {

}
