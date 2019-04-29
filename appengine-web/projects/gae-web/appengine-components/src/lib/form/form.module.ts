import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GaeFormGroupErrorsDirective } from './control.component';
import {
  SelectFieldFormControlComponent, DateFieldFormControlComponent,
  InputFieldFormControlComponent, AutoCompleteInputFieldFormControlComponent,
  TextAreaFormControlComponent, CheckboxFormControlComponent
} from './input.component';
import { MatCheckboxModule, MatSelectModule, MatOptionModule, MatInputModule, MatDatepickerModule, MatAutocompleteModule } from '@angular/material';
import { GaeConfirmModelFormComponent } from './confirm-form.component';
import { CommonModule } from '@angular/common';

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
  ],
  declarations: [
    GaeFormGroupErrorsDirective,
    GaeConfirmModelFormComponent,
    SelectFieldFormControlComponent,
    DateFieldFormControlComponent,
    InputFieldFormControlComponent,
    AutoCompleteInputFieldFormControlComponent,
    TextAreaFormControlComponent,
    CheckboxFormControlComponent
  ],
  exports: [
    GaeFormGroupErrorsDirective,
    GaeConfirmModelFormComponent,
    SelectFieldFormControlComponent,
    DateFieldFormControlComponent,
    InputFieldFormControlComponent,
    AutoCompleteInputFieldFormControlComponent,
    TextAreaFormControlComponent,
    CheckboxFormControlComponent
  ]
})
export class GaeFormComponentsModule { }
