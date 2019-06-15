import { Component, ViewEncapsulation, Optional } from '@angular/core';
import { AbstractExtendedFormControlComponent, GaeFormGroupErrorsDirective } from '../control.component';

@Component({
  template: `
  <mat-form-field class="gae-form-view-control" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }" [color]="color">
      <textarea matInput [formControlName]="field" [placeholder]="placeholder"></textarea>
      <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
  </mat-form-field>
  `,
  selector: 'gae-textarea-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeTextareaFormControlComponent extends AbstractExtendedFormControlComponent {

  constructor(@Optional() errors: GaeFormGroupErrorsDirective) {
    super(errors);
  }

}
