import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GaeBasicLoadingComponent } from './basic-loading.component';
import { GaeLoadingProgressComponent } from './loading-progress.component';
import { MatProgressSpinnerModule, MatProgressBarModule } from '@angular/material';
import { GaeErrorComponent } from './error.component';

@NgModule({
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatProgressBarModule
  ],
  declarations: [
    GaeBasicLoadingComponent,
    GaeLoadingProgressComponent,
    GaeErrorComponent
  ],
  exports: [
    GaeBasicLoadingComponent,
    GaeLoadingProgressComponent,
    GaeErrorComponent
  ]
})
export class GaeLoadingModule { }
