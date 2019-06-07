import { NgModule } from '@angular/core';
import { GaeReadSourceKeyDirective } from './resource/read.component';
import { GaeTransformationSourceDirective } from './resource/source.component';
import { GaeMultiModelLoaderDirective } from './loading/multi-model-loader.directive';
import { GaeModelLoaderComponent } from './loading/model-loader.component';
import { GaeLoadingComponentsModule } from '../loading/loading.module';
import { GaeModelLoadingViewComponent } from './loading/loading.component';
import { GaeFormComponentsModule } from '../form/form.module';
import { GaeCreateModelFormControllerDirective } from './form/create.directive';
import { GaeSubmitViewActionDirective, GaeSubmitButtonComponent } from './form/submit.component';
import { GaeUpdateModelFormControllerDirective } from './form/update.directive';
import { GaeDeleteModelFormControllerDirective } from './form/delete.directive';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { CommonModule } from '@angular/common';
import { MatProgressButtonsModule } from 'mat-progress-buttons';
import { GaeActionViewComponent, GaeActionDoneViewComponent, GaeActionResetViewComponent } from './form/action.component';
import { MatIconModule, MatButtonModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatIconModule,
    MatButtonModule,
    GaeLoadingComponentsModule,
    GaeFormComponentsModule,
    MatProgressSpinnerModule,
    MatProgressButtonsModule
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
    GaeActionViewComponent,
    GaeActionDoneViewComponent,
    GaeActionResetViewComponent,
    GaeSubmitViewActionDirective,
    GaeSubmitButtonComponent
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
    GaeActionViewComponent,
    GaeActionDoneViewComponent,
    GaeActionResetViewComponent,
    GaeSubmitViewActionDirective,
    GaeSubmitButtonComponent
  ]
})
export class GaeModelComponentsModule { }
