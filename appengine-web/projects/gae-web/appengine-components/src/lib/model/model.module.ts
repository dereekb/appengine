import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule, MatProgressBarModule } from '@angular/material';
import { ReadSourceModelKeyDirective } from './read.component';

@NgModule({
  imports: [],
  declarations: [
    ReadSourceModelKeyDirective,
  ],
  exports: [
    ReadSourceModelKeyDirective
  ]
})
export class GaeModelModule { }
