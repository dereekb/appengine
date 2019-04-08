import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GaeBasicLoadingComponent } from './basic-loading.component';
import { GaeLoadingProgressComponent } from './loading-progress.component';
import { MatProgressSpinnerModule, MatProgressBarModule } from '@angular/material';
import { GaeErrorComponent } from './error.component';
import { GaeLoadingComponent } from './loading.component';

@NgModule({
  imports: [
    CommonModule,
    MatProgressSpinnerModule,
    MatProgressBarModule
  ],
  declarations: [
    GaeLoadingComponent,
    GaeBasicLoadingComponent,
    GaeLoadingProgressComponent,
    GaeErrorComponent
  ],
  exports: [
    GaeLoadingComponent,
    GaeBasicLoadingComponent,
    GaeLoadingProgressComponent,
    GaeErrorComponent
  ]
})
export class GaeLoadingModule { }
