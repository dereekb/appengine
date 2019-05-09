import { NgModule } from '@angular/core';
import { GaeListViewReadSourceDirective, GaeListViewKeyQuerySourceDirective } from './source.directive';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { CommonModule } from '@angular/common';
import { GaeLoadingComponentsModule } from '../loading/loading.module';
import { MatListModule, MatIconModule, MatButtonModule } from '@angular/material';

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
    GaeListViewKeyQuerySourceDirective
  ],
  exports: [
    GaeListViewWrapperComponent,
    GaeListViewReadSourceDirective,
    GaeListViewKeyQuerySourceDirective
  ]
})
export class GaeListComponentsModule { }
