import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { FooComponentsModule } from './foo/foo.module';

@NgModule({
  imports: [
    FooComponentsModule
  ]
})
export class SecureModelComponentsModule { }
