import { Component, ViewEncapsulation, ViewChild, Input, Optional } from '@angular/core';
import { MatDatepicker } from '@angular/material/datepicker';
import { AbstractExtendedFormControlComponent, GaeFormGroupErrorsDirective } from '../control.component';

@Component({
  template: `
  <mat-form-field class="form-view-control date-form-control" [formGroup]="form"
      [ngClass]="{ warning: hasError, required: required }" [color]="color">
      <mat-datepicker #picker [touchUi]="touch"></mat-datepicker>
      <input matInput [formControlName]="field" [matDatepicker]="picker" [placeholder]="placeholder"/>
      <mat-datepicker-toggle class="form-suffix-button" matSuffix [for]="picker"></mat-datepicker-toggle>
      <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
  </mat-form-field>
  `,
  selector: 'gae-date-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeDateFormControlComponent extends AbstractExtendedFormControlComponent {

  @ViewChild(MatDatepicker)
  public readonly picker: MatDatepicker<Date>;

  @Input()
  public touch: boolean;

  constructor(@Optional() errors: GaeFormGroupErrorsDirective) {
    super(errors);
  }

  // Controls
  public open() {
    this.picker.open();
  }

  public close() {
    this.picker.close();
  }

}
