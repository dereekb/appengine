import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GaeBasicLoadingComponent } from './basic-loading.component';
import { GaeLoadingProgressComponent } from './loading-progress.component';
import { MatProgressSpinnerModule, MatProgressBarModule } from '@angular/material';

@NgModule({
  declarations: [
    GaeBasicLoadingComponent,
    GaeLoadingProgressComponent
  ],
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatProgressBarModule
  ],
  exports: [
    GaeBasicLoadingComponent,
    GaeLoadingProgressComponent
  ]
})
export class GaeLoadingModule { }
