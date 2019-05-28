import { NgModule } from '@angular/core';
import {
  GaeSelectionListContentComponent, GaeSelectionListControllerDirective,
  GaeSelectionListViewComponent, GaeSelectionListViewNamedConversionDirective, GaeSelectionListViewSourceDirective
} from './selection-list.component';
import { CommonModule } from '@angular/common';
import { MatSelectModule, MatIconModule, MatListModule } from '@angular/material';
import { GaeListComponentsModule } from './list.module';
import { GaeMaterialComponentsModule } from '../material/material.module';

@NgModule({
  imports: [
    CommonModule,
    MatSelectModule,
    MatIconModule,
    MatListModule,
    GaeListComponentsModule,
    GaeMaterialComponentsModule
  ],
  declarations: [
    GaeSelectionListContentComponent,
    GaeSelectionListControllerDirective,
    GaeSelectionListViewComponent,
    GaeSelectionListViewNamedConversionDirective,
    GaeSelectionListViewSourceDirective
  ],
  exports: [
    GaeSelectionListContentComponent,
    GaeSelectionListControllerDirective,
    GaeSelectionListViewComponent,
    GaeSelectionListViewNamedConversionDirective,
    GaeSelectionListViewSourceDirective
  ]
})
export class GaeSelectionListComponentsModule { }