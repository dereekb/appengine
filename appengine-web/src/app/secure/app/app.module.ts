import { NgModule } from '@angular/core';
import { APP_STATES } from './app.states';
import { AppComponent } from './app.component';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { CommonModule } from '@angular/common';
import { SecureAppImportsModule } from './app.imports';
import { HomeModule } from './home/home.module';
import { DemoModule } from './demo/demo.module';
import { AppSidenavComponent } from './sidenav.component';
import { GaeMaterialComponentsModule } from '@gae-web/appengine-components';

export const ROUTER_CONFIG: StatesModule = {
  states: APP_STATES
};

@NgModule({
  declarations: [AppComponent, AppSidenavComponent],
  exports: [AppComponent],
  imports: [
    GaeMaterialComponentsModule,
    CommonModule,
    HomeModule,
    DemoModule,
    SecureAppImportsModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class SecureAppModule { }
