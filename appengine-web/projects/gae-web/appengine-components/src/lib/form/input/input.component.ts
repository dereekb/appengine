import { Component, ViewEncapsulation, Input, Optional } from '@angular/core';
import { AbstractExtendedFormControlComponent, GaeFormGroupErrorsDirective } from '../control.component';

@Component({
  template: `
  <mat-form-field class="form-view-control" [formGroup]="form" [required]="required" [ngClass]="{ warning: hasError, required: required }" [color]="color">
      <input type="{{type}}" matInput [formControlName]="field" [placeholder]="placeholder"/>
      <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
      <span matPrefix><ng-content select="[inputPrefix]"></ng-content></span>
      <span matSuffix><ng-content select="[inputSuffix]"></ng-content></span>
  </mat-form-field>
  `,
  selector: 'gae-input-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeInputFormControlComponent extends AbstractExtendedFormControlComponent {

  @Input()
  public type = 'text';

  constructor(@Optional() errors: GaeFormGroupErrorsDirective) {
    super(errors);
  }

}
