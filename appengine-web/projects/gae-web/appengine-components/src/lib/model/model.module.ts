import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule, MatProgressBarModule } from '@angular/material';
import { GaeReadSourceKeyDirective } from './read.component';
import { GaeTransformationSourceDirective } from './source.component';

@NgModule({
  imports: [],
  declarations: [
    GaeReadSourceKeyDirective,
    GaeTransformationSourceDirective
  ],
  exports: [
    GaeReadSourceKeyDirective,
    GaeTransformationSourceDirective
  ]
})
export class GaeModelModule { }
