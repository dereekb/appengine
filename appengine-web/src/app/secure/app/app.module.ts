import { NgModule } from '@angular/core';
import { APP_STATES } from './app.states';
import { AppComponent } from './app.component';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { CommonModule } from '@angular/common';
import { SecureAppImportsModule } from './app.imports';
import { HomeModule } from './home/home.module';
import { DemoModule } from './demo/demo.module';

export const ROUTER_CONFIG: StatesModule = {
  states: APP_STATES
};

@NgModule({
  declarations: [AppComponent],
  exports: [AppComponent],
  imports: [
    CommonModule,
    HomeModule,
    DemoModule,
    SecureAppImportsModule,
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class SecureAppModule { }