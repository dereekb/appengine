import { NgModule } from '@angular/core';
import { GaeReadSourceKeyDirective } from './resource/read.component';
import { GaeTransformationSourceDirective } from './resource/source.component';
import { GaeMultiModelLoaderDirective } from './loading/multi-model-loader.directive';
import { GaeModelLoaderComponent } from './loading/model-loader.component';
import { GaeLoadingComponentsModule } from '../loading/loading.module';
import { GaeModelLoadingViewComponent } from './loading/loading.component';
import { GaeFormComponentsModule } from '../form/form.module';
import { GaeCreateModelFormControllerDirective } from './form/create.directive';
import { GaeSubmitViewComponent, GaeSubmitViewActionDirective } from './form/submit.component';
import { GaeUpdateModelFormControllerDirective } from './form/update.directive';
import { GaeDeleteModelFormControllerDirective } from './form/delete.directive';
import { MatProgressSpinnerModule } from '@angular/material';
import { CommonModule } from '@angular/common';

@NgModule({
  imports: [
    CommonModule,
    GaeLoadingComponentsModule,
    GaeFormComponentsModule,
    MatProgressSpinnerModule
  ],
  declarations: [
    GaeReadSourceKeyDirective,
    GaeTransformationSourceDirective,
    GaeModelLoadingViewComponent,
    GaeModelLoaderComponent,
    GaeMultiModelLoaderDirective,
    GaeCreateModelFormControllerDirective,
    GaeUpdateModelFormControllerDirective,
    GaeDeleteModelFormControllerDirective,
    GaeSubmitViewActionDirective,
    GaeSubmitViewComponent
  ],
  exports: [
    GaeReadSourceKeyDirective,
    GaeTransformationSourceDirective,
    GaeModelLoadingViewComponent,
    GaeModelLoaderComponent,
    GaeMultiModelLoaderDirective,
    GaeCreateModelFormControllerDirective,
    GaeUpdateModelFormControllerDirective,
    GaeDeleteModelFormControllerDirective,
    GaeSubmitViewActionDirective,
    GaeSubmitViewComponent
  ]
})
export class GaeModelComponentsModule { }
