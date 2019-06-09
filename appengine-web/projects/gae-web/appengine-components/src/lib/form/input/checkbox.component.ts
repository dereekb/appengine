import { Component, ViewEncapsulation, Input, ViewChild, Optional } from '@angular/core';
import { AbstractExtendedFormControlComponent, GaeFormGroupErrorsDirective } from '../control.component';
import { MatCheckbox } from '@angular/material/checkbox';

@Component({
  template: `
  <div class="gae-form-view-control gae-form-view-checkbox-control" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }">
      <mat-checkbox class="gae-form-view-checkbox" [formControlName]="field" [labelPosition]="labelPosition" [color]="color">
      {{ placeholder }}
      </mat-checkbox>
      <p class="gae-form-view-hint" *ngIf="hintMsg">{{ hintMsg }}</p>
  </div>
  `,
  selector: 'gae-checkbox-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeCheckboxFormControlComponent extends AbstractExtendedFormControlComponent {

  @ViewChild(MatCheckbox, {static: true})
  private _checkbox: MatCheckbox;

  @Input()
  public labelPosition: 'before' | 'after' = 'before';

  constructor(@Optional() errors: GaeFormGroupErrorsDirective) {
      super(errors);
  }

  public get checked() {
      return this._checkbox.checked;
  }

  public set checked(checked) {
      this._checkbox.checked = checked;
  }

}
