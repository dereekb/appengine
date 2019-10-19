import { NgModule } from '@angular/core';
import { GaeListViewReadSourceDirective, GaeListViewKeySearchSourceDirective } from './source.directive';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { CommonModule } from '@angular/common';
import { GaeLoadingComponentsModule } from '../loading/loading.module';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { GaeListLoadMoreComponent } from './load-more.component';

@NgModule({
  imports: [
    CommonModule,
    GaeLoadingComponentsModule,
    MatListModule,
    MatIconModule,
    MatButtonModule
  ],
  declarations: [
    GaeListViewWrapperComponent,
    GaeListViewReadSourceDirective,
    GaeListViewKeySearchSourceDirective,
    GaeListLoadMoreComponent
  ],
  exports: [
    GaeListViewWrapperComponent,
    GaeListViewReadSourceDirective,
    GaeListViewKeySearchSourceDirective,
    GaeListLoadMoreComponent
  ]
})
export class GaeListComponentsModule { }
