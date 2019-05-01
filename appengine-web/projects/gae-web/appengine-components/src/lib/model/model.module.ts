import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatProgressSpinnerModule, MatProgressBarModule } from '@angular/material';
import { GaeReadSourceKeyDirective } from './resource/read.component';
import { GaeTransformationSourceDirective } from './resource/source.component';
import { GaeMultiModelLoaderDirective } from './loading/multi-model-loader.directive';
import { GaeModelLoaderComponent } from './loading/model-loader.component';
import { GaeLoadingModule } from '../loading/loading.module';
import { GaeModelLoadingViewComponent } from './loading/loading.component';

@NgModule({
  imports: [
    GaeLoadingModule
  ],
  declarations: [
    GaeReadSourceKeyDirective,
    GaeTransformationSourceDirective,
    GaeModelLoadingViewComponent,
    GaeModelLoaderComponent,
    GaeMultiModelLoaderDirective
  ],
  exports: [
    GaeReadSourceKeyDirective,
    GaeTransformationSourceDirective,
    GaeModelLoadingViewComponent,
    GaeModelLoaderComponent,
    GaeMultiModelLoaderDirective
  ]
})
export class GaeModelComponentsModule { }
