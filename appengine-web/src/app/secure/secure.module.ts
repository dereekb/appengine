import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule, UIRouter, Category, StateService } from '@uirouter/angular';
import {
  GaeApiModule, GaeApiConfiguration, GaeLoginApiModule, GaeLoginApiModuleService,
  GaeEventApiModuleService, GaeLoginApiModuleConfiguration, GaeEventApiModule,
  GaeEventApiModuleConfiguration,
  GaeLoginApiConfiguredJwtModule
} from '@gae-web/appengine-api';
import { GaeClientModule } from '@gae-web/appengine-client';
import { SECURE_STATES } from './secure.states';
import { SecureAppModule } from './app/app.module';
import { GaeTokenModule } from '@gae-web/appengine-token';
import { AppSegueService } from './segue.service';
import { GaeGatewayModule, secureGatewayHook, GaeGatewayViewsModule, GatewaySegueService } from 'projects/gae-web/appengine-gateway/src/public-api';
import { HttpClientModule } from '@angular/common/http';
import { SecureComponentsModule } from './shared/components/components.module';
import { SecureApiModule } from './shared/api/api.module';
import { GaeComponentsModule, GaeMaterialComponentsModule, GaeComponentsPreConfiguredAppModule } from '@gae-web/appengine-components';
import { TestApiModuleService, TestApiModule, TestApiModuleConfiguration } from './shared/api/model/test.api';

export function routerConfigFn(router: UIRouter) {
  const transitionService = router.transitionService;
  secureGatewayHook(transitionService);
  router.trace.enable(Category.TRANSITION);
}

export function gaeApiConfigurationFactory(loginService: GaeLoginApiModuleService, eventService: GaeEventApiModuleService, fooService: TestApiModuleService) {
  return new GaeApiConfiguration([loginService, eventService, fooService]);
}

export const ROUTER_CONFIG: StatesModule = {
  config: routerConfigFn,
  states: SECURE_STATES
};

/**
 * The Appengine test server has only a single module.
 */
export const TEST_SERVER_MODULE_NAME = 'test';

export function loginApiModuleConfigurationFactory() {
  return GaeLoginApiModuleConfiguration.make({
    name: TEST_SERVER_MODULE_NAME
  });
}

export function eventApiModuleConfigurationFactory() {
  return GaeEventApiModuleConfiguration.make({
    name: TEST_SERVER_MODULE_NAME
  });
}

export function testApiModuleConfigurationFactory() {
  return TestApiModuleConfiguration.make({
    name: TEST_SERVER_MODULE_NAME
  });
}

@NgModule({
  imports: [
    GaeGatewayModule,
    SecureAppModule,
    // GAE Configurations
    HttpClientModule,
    GaeComponentsPreConfiguredAppModule,
    GaeGatewayViewsModule.forRoot({
      logoUrl: 'https://via.placeholder.com/350x150'
    }),
    GaeTokenModule.forRoot(),
    GaeLoginApiConfiguredJwtModule,
    GaeLoginApiModule.forApp(),
    GaeEventApiModule.forApp(),
    TestApiModule.forApp(),
    GaeApiModule.forApp({
      gaeApiConfigurationProvider: {
        provide: GaeApiConfiguration,
        useFactory: gaeApiConfigurationFactory,
        deps: [GaeLoginApiModuleService, GaeEventApiModuleService, TestApiModuleService]
      }
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
    },
    {
      provide: GaeLoginApiModuleConfiguration,
      useFactory: loginApiModuleConfigurationFactory
    },
    {
      provide: GaeEventApiModuleConfiguration,
      useFactory: eventApiModuleConfigurationFactory
    },
    {
      provide: TestApiModuleConfiguration,
      useFactory: testApiModuleConfigurationFactory
    }
  ]
})
export class SecureModule { }
