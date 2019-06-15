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
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { FooCreateDialogComponent } from './views/create-modal.component';
import { FooDeleteDialogComponent } from './views/delete-modal.component';

@NgModule({
  imports: [
    CommonModule,
    GaeComponentsModule,
    MatListModule,
    MatIconModule,
    MatTabsModule,
    MatToolbarModule,
    MatDialogModule,
    MatButtonModule
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
    FooCreateDialogComponent,
    FooSegueToCreatedViewDirective,
    FooDeleteFormComponent,
    FooDeleteDialogComponent,
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
    FooCreateDialogComponent,
    FooSegueToCreatedViewDirective,
    FooDeleteFormComponent,
    FooDeleteDialogComponent,
    FooListContentComponent,
    FooListViewComponent,
    FooModelFormComponent,
    FooUpdateFormComponent
  ],
  entryComponents: [FooCreateDialogComponent, FooDeleteDialogComponent]
})
export class FooComponentsModule { }
