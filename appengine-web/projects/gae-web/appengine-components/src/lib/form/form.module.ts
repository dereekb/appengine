import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { GaeFormGroupErrorsDirective } from './control.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatOptionModule, MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { GaeConfirmModelFormComponent } from './confirm-form.component';
import { CommonModule } from '@angular/common';
import { GaeSelectFormControlComponent } from './input/select.component';
import { GaeDateFormControlComponent } from './input/date.component';
import { GaeInputFormControlComponent } from './input/input.component';
import { GaeAutoCompleteFormControlComponent } from './input/autocomplete.component';
import { GaeTextareaFormControlComponent } from './input/textarea.component';
import { GaeCheckboxFormControlComponent } from './input/checkbox.component';
import { GaeConfirmDeleteModelFormComponent } from './confirm-delete-form.component';
import { GaeChipListFormControlComponent } from './input/chiplist.component';
import { MatChipsModule, MatIconModule } from '@angular/material';

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
    MatNativeDateModule,
    MatChipsModule,
    MatIconModule
  ],
  declarations: [
    GaeFormGroupErrorsDirective,
    GaeConfirmModelFormComponent,
    GaeConfirmDeleteModelFormComponent,
    GaeSelectFormControlComponent,
    GaeDateFormControlComponent,
    GaeInputFormControlComponent,
    GaeAutoCompleteFormControlComponent,
    GaeTextareaFormControlComponent,
    GaeCheckboxFormControlComponent,
    GaeChipListFormControlComponent
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    GaeFormGroupErrorsDirective,
    GaeConfirmModelFormComponent,
    GaeConfirmDeleteModelFormComponent,
    GaeSelectFormControlComponent,
    GaeDateFormControlComponent,
    GaeInputFormControlComponent,
    GaeAutoCompleteFormControlComponent,
    GaeTextareaFormControlComponent,
    GaeCheckboxFormControlComponent,
    GaeChipListFormControlComponent
  ]
})
export class GaeFormComponentsModule { }
