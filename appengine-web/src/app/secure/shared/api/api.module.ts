import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { SecureModelApiModule } from './model/model.module';

@NgModule({
  imports: [SecureModelApiModule]
})
export class SecureApiModule { }
