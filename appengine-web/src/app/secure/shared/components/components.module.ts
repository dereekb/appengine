import { NgModule } from '@angular/core';
import { UIRouterModule, StatesModule } from '@uirouter/angular';
import { SecureModelComponentsModule } from './model/model.module';
import { AppSegueDirective } from './app/segue.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { GaeComponentsModule } from '@gae-web/appengine-components';

@NgModule({
  imports: [
    SecureModelComponentsModule
  ],
  declarations: [
    AppSegueDirective
  ],
  exports: [
    SecureModelComponentsModule,
    GaeComponentsModule,
    AppSegueDirective,
    InfiniteScrollModule
  ]
})
export class SecureComponentsModule { }
