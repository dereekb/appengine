import { NgModule } from '@angular/core';
import { FooCreateActionDirective, FooCreateActionAnalyticDirective } from './client/create.directive';
import { FooDeleteActionDirective, FooDeleteActionAnalyticDirective } from './client/delete.directive';
import { FooQuerySourceComponent, FooDefaultQuerySourceComponent } from './client/query.component';
import { FooUpdateActionDirective, FooUpdateActionAnalyticDirective } from './client/update.directive';
import { FooReadSourceComponent } from './client/read.component';
import { FooCreateFormComponent, FooSegueToCreatedViewDirective } from './views/create-form.component';
import { FooDeleteFormComponent } from './views/delete-form.component';
import { FooListContentComponent } from './views/list-content.component';
import { FooListViewComponent } from './views/list-view.component';
import { FooModelFormComponent } from './views/model-form.component';
import { FooUpdateFormComponent } from './views/update-form.component';
import { GaeComponentsModule } from '@gae-web/appengine-components';
import { CommonModule } from '@angular/common';
import { MatListModule, MatIconModule, MatTabsModule, MatToolbarModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    GaeComponentsModule,
    MatListModule,
    MatIconModule,
    MatTabsModule,
    MatToolbarModule
  ],
  declarations: [
    // Client
    FooCreateActionDirective,
    FooCreateActionAnalyticDirective,
    FooDeleteActionDirective,
    FooDeleteActionAnalyticDirective,
    FooQuerySourceComponent,
    FooDefaultQuerySourceComponent,
    FooReadSourceComponent,
    FooUpdateActionDirective,
    FooUpdateActionAnalyticDirective,
    // Views
    FooCreateFormComponent,
    FooSegueToCreatedViewDirective,
    FooDeleteFormComponent,
    FooListContentComponent,
    FooListViewComponent,
    FooModelFormComponent,
    FooUpdateFormComponent
  ],
  exports: [
    // Client
    FooCreateActionDirective,
    FooCreateActionAnalyticDirective,
    FooDeleteActionDirective,
    FooDeleteActionAnalyticDirective,
    FooQuerySourceComponent,
    FooDefaultQuerySourceComponent,
    FooReadSourceComponent,
    FooUpdateActionDirective,
    FooUpdateActionAnalyticDirective,
    // Views
    FooCreateFormComponent,
    FooSegueToCreatedViewDirective,
    FooDeleteFormComponent,
    FooListContentComponent,
    FooListViewComponent,
    FooModelFormComponent,
    FooUpdateFormComponent
  ]
})
export class FooComponentsModule { }
