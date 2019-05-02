import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GaeFormGroupErrorsDirective } from './control.component';
import { MatCheckboxModule, MatSelectModule, MatOptionModule, MatInputModule,
  MatDatepickerModule, MatAutocompleteModule, MatNativeDateModule } from '@angular/material';
import { GaeConfirmModelFormComponent } from './confirm-form.component';
import { CommonModule } from '@angular/common';
import { GaeSelectFormControlComponent } from './input/select.component';
import { GaeDateFormControlComponent } from './input/date.component';
import { GaeInputFormControlComponent } from './input/input.component';
import { GaeAutoCompleteFormControlComponent } from './input/autocomplete.component';
import { GaeTextareaFormControlComponent } from './input/textarea.component';
import { GaeCheckboxFormControlComponent } from './input/checkbox.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    MatCheckboxModule,
    MatSelectModule,
    MatOptionModule,
    MatInputModule,
    MatDatepickerModule,
    MatAutocompleteModule,
    MatNativeDateModule
  ],
  declarations: [
    GaeFormGroupErrorsDirective,
    GaeConfirmModelFormComponent,
    GaeSelectFormControlComponent,
    GaeDateFormControlComponent,
    GaeInputFormControlComponent,
    GaeAutoCompleteFormControlComponent,
    GaeTextareaFormControlComponent,
    GaeCheckboxFormControlComponent
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    GaeFormGroupErrorsDirective,
    GaeConfirmModelFormComponent,
    GaeSelectFormControlComponent,
    GaeDateFormControlComponent,
    GaeInputFormControlComponent,
    GaeAutoCompleteFormControlComponent,
    GaeTextareaFormControlComponent,
    GaeCheckboxFormControlComponent
  ]
})
export class GaeFormComponentsModule { }
