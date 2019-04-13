import { NgModule } from '@angular/core';
import { APP_STATES } from './app.states';
import { AppComponent } from './app.component';
import { UIRouterModule } from '@uirouter/angular';

export const ROUTER_CONFIG: any = {
  states: APP_STATES
};

@NgModule({
  declarations: [AppComponent],
  exports: [AppComponent],
  imports: [
    UIRouterModule.forChild(ROUTER_CONFIG)
  ]
})
export class AppModule { }
