import { NgModule } from '@angular/core';
import {
  GaeSelectionListContentComponent, GaeSelectionListControllerDirective,
  GaeSelectionListViewComponent, GaeSelectionListViewNamedConversionDirective, GaeSelectionListViewSourceDirective
} from './selection-list.component';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { MatSelectModule } from '@angular/material/select';
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
