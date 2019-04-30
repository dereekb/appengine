import { Component, ViewEncapsulation, Input } from '@angular/core';
import { GaeInputFormControlComponent } from './input.component';
import { Observable } from 'rxjs';
import { GaeFormGroupErrorsDirective } from '../control.component';

@Component({
  template: `
  <mat-form-field class="form-view-control" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }" [color]="color">
      <input type="{{type}}" matInput [formControlName]="field" [matAutocomplete]="autoCompleteView" [placeholder]="placeholder"/>
      <mat-autocomplete #autoCompleteView="matAutocomplete">
          <mat-option *ngFor="let option of autoCompleteOptions | async" [value]="option">{{ option }}</mat-option>
      </mat-autocomplete>
      <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
  </mat-form-field>
  `,
  selector: 'gae-auto-complete-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeAutoCompleteFormControlComponent extends GaeInputFormControlComponent {

  @Input()
  public autoCompleteOptions: Observable<any>;

  constructor(errors: GaeFormGroupErrorsDirective) {
      super(errors);
  }

}
