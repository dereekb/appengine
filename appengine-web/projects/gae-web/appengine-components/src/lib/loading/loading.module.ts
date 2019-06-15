import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GaeBasicLoadingComponent } from './basic-loading.component';
import { GaeLoadingProgressComponent } from './loading-progress.component';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
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
    GaeErrorComponent,
    MatProgressSpinnerModule,
    MatProgressBarModule
  ]
})
export class GaeLoadingComponentsModule { }
