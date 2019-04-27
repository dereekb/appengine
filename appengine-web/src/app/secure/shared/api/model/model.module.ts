import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { FooClientModule } from './foo/foo.module';

@NgModule({
  imports: [
    FooClientModule.forApp()
  ]
})
export class SecureModelApiModule { }
