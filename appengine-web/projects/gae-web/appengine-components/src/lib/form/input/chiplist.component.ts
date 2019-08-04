import { Component, ViewEncapsulation, Input, Optional, ElementRef, ViewChild } from '@angular/core';
import { AbstractExtendedFormControlComponent, GaeFormGroupErrorsDirective } from '../control.component';
import { Observable } from 'rxjs';
import { MatAutocompleteSelectedEvent, MatChipInputEvent, MatAutocomplete } from '@angular/material';
import { FormControl } from '@angular/forms';

@Component({
  template: `
  <mat-form-field class="gae-form-view-control" [formGroup]="form" [color]="color">
      <mat-chip-list #chipList>
        <mat-chip *ngFor="let value of values" [selectable]="selectable"
                [removable]="removable" (removed)="remove(value)">
          {{ value }}
          <mat-icon matChipRemove *ngIf="removable">cancel</mat-icon>
        </mat-chip>
        <input #textInput
          placeholder="{{ placeholder }}"
          [formControl]="valueInputCtrl"
          [matAutocomplete]="autoCompleteView"
          [matChipInputFor]="chipList"
          [matChipInputSeparatorKeyCodes]="separatorKeysCodes"
          [matChipInputAddOnBlur]="addOnBlur"
          (matChipInputTokenEnd)="add($event)">
    </mat-chip-list>
    <mat-autocomplete #autoCompleteView="matAutocomplete" (optionSelected)="selected($event)">
        <mat-option *ngFor="let option of autoCompleteOptions | async" [value]="option">{{ option }}</mat-option>
    </mat-autocomplete>
  </mat-form-field>
  `,
  selector: 'gae-chiplist-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeChipListFormControlComponent extends AbstractExtendedFormControlComponent {

  @Input()
  public removable = true;

  // TODO: Add option to restrict values from autocomplete.

  @Input()
  public autoCompleteOptions: Observable<any>;

  public readonly valueInputCtrl = new FormControl();

  @ViewChild('textInput', { static: false }) textInput: ElementRef<HTMLInputElement>;
  @ViewChild('autoCompleteView', { static: false }) matAutocomplete: MatAutocomplete;

  constructor(@Optional() errors: GaeFormGroupErrorsDirective) {
    super(errors);
  }

  get values(): string[] {
    return this.formControl.value || [];
  }

  set values(values: string[]) {
    this.formControl.setValue(values || []);
  }

  // MARK: Input
  add(event: MatChipInputEvent): void {
    // Add value only when MatAutocomplete is not open to
    // make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = (event.value || '').trim();

      if (value) {
        this.values = this.values.concat(value);
      }

      // Reset the input value
      if (input) {
        input.value = '';
      }

      this.valueInputCtrl.setValue(null);
    }
  }

  remove(value: string): void {
    const index = this.values.indexOf(value);

    if (index >= 0) {
      this.values.splice(index, 1);
      this.values = this.values;
    }
  }

  // MARK: Autoselect
  selected(event: MatAutocompleteSelectedEvent): void {
    this.values = this.values.concat(event.option.viewValue);
    this.textInput.nativeElement.value = '';
    this.valueInputCtrl.setValue(null);
  }

}
