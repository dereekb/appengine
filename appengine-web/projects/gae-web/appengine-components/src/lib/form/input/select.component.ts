import { Component, ViewEncapsulation, Input, ViewChild, Optional } from '@angular/core';
import { Observable } from 'rxjs';
import { AbstractExtendedFormControlComponent, GaeFormGroupErrorsDirective } from '../control.component';
import { MatSelect } from '@angular/material/select';

export interface SelectOption {
  readonly label: string;
  readonly value: any;
}

@Component({
  template: `
  <div class="mat-form-field select-form-control">
      <div class="mat-input-wrapper" [formGroup]="form" [ngClass]="{ warning: hasError, required: required }">
          <mat-select class="form-view-control" [formControlName]="field" [placeholder]="placeholder">
              <mat-option *ngFor="let option of options | async" [value]="option.value">{{ option.label }}</mat-option>
          </mat-select>
      </div>
  </div>
  `,
  selector: 'gae-select-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeSelectFormControlComponent extends AbstractExtendedFormControlComponent {

  @ViewChild(MatSelect, {static: true})
  private _select: MatSelect;

  @Input()
  public options: Observable<SelectOption>;

  constructor(@Optional() errors: GaeFormGroupErrorsDirective) {
    super(errors);
  }

  get matSelect(): MatSelect {
    return this._select;
  }

}
