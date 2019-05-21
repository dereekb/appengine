import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule, UIRouter, Category, StateService } from '@uirouter/angular';
import { GaeApiModule } from '@gae-web/appengine-api';
import { GaeClientModule } from '@gae-web/appengine-client';
import { SECURE_STATES } from './secure.states';
import { SecureAppModule } from './app/app.module';
import { GaeTokenModule } from '@gae-web/appengine-token';
import { AppSegueService } from './segue.service';
import { GaeGatewayModule, secureGatewayHook, GaeGatewayViewsModule, GatewaySegueService } from 'projects/gae-web/appengine-gateway/src/public-api';
import { HttpClientModule } from '@angular/common/http';
import { SecureComponentsModule } from './shared/components/components.module';
import { SecureApiModule } from './shared/api/api.module';
import { GaeComponentsModule } from '@gae-web/appengine-components';

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
    HttpClientModule,
    GaeApiModule.makeJwtModuleForRoot(),
    ...GaeComponentsModule.allComponentsApp(),
    GaeGatewayViewsModule.forRoot({
      logoUrl: 'https://via.placeholder.com/350x150'
    }),
    GaeTokenModule.forRoot(),
    GaeApiModule.forApp({
      version: 'v1',
      name: 'test'
    }),
    GaeClientModule.forApp(),
    SecureApiModule,
    SecureComponentsModule,
    // Routing
    UIRouterModule.forChild(ROUTER_CONFIG)
  ],
  providers: [
    // Configurations
    AppSegueService,
    {
      provide: GatewaySegueService,
      useExisting: AppSegueService
    }
  ]
})
export class SecureModule {

}
