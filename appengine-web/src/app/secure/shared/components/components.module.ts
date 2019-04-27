import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { SecureModelComponentsModule } from './model/model.module';

@NgModule({
  imports: [
    SecureModelComponentsModule
  ]
})
export class SecureComponentsModule { }
