import { Component, ViewEncapsulation, Input, Optional, ElementRef, ViewChild, OnChanges, AfterViewInit, AfterContentInit } from '@angular/core';
import { AbstractExtendedFormControlComponent, GaeFormGroupErrorsDirective } from '../control.component';
import { Observable, combineLatest } from 'rxjs';
import { MatChipInputEvent } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { MatAutocompleteSelectedEvent, MatAutocomplete } from '@angular/material/autocomplete';
import { FormControl } from '@angular/forms';
import { COMMA, ENTER } from '@angular/cdk/keycodes';
import { map, startWith, shareReplay } from 'rxjs/operators';
import { SubscriptionObject } from '@gae-web/appengine-utility';

@Component({
  template: `
  <mat-form-field class="gae-form-view-control" [formGroup]="form" [color]="color">
      <mat-chip-list #chipList>
        <mat-chip *ngFor="let value of values" [removable]="removable" (removed)="remove(value)">
          {{ value }}
          <mat-icon matChipRemove *ngIf="removable && !disabled">cancel</mat-icon>
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
    <mat-autocomplete #autoCompleteView="matAutocomplete" (optionSelected)="selected($event)" [panelWidth]="autoCompletePanelWidth">
        <mat-option *ngFor="let option of filteredAutoCompleteOptions | async" [value]="option">{{ option }}</mat-option>
    </mat-autocomplete>
    <mat-hint *ngIf="hintMsg">{{ hintMsg }}</mat-hint>
    <mat-error *ngIf="hasError">{{error}}</mat-error>
  </mat-form-field>
  `,
  selector: 'gae-chiplist-form-control',
  encapsulation: ViewEncapsulation.None
})
export class GaeChipListFormControlComponent extends AbstractExtendedFormControlComponent implements AfterContentInit, OnChanges {

  @Input()
  public maxChips: number;

  @Input()
  public removable = true;

  @Input()
  public addOnBlur = true;

  @Input()
  public separatorKeysCodes: number[] = [ENTER, COMMA];

  @Input()
  public autoCompletePanelWidth: string | number;

  // TODO: Add option to restrict values from autocomplete.

  private _autoCompleteOptions: Observable<string[]>;
  private _filteredAutoCompleteOptions: Observable<string[]>;

  private _formSub = new SubscriptionObject();

  public readonly valueInputCtrl = new FormControl();

  @ViewChild('textInput')
  public readonly textInput: ElementRef<HTMLInputElement>;

  @ViewChild('autoCompleteView')
  public readonly matAutocomplete: MatAutocomplete;

  constructor(@Optional() errors: GaeFormGroupErrorsDirective) {
    super(errors);
  }

  get values(): string[] {
    return this.formControl.value || [];
  }

  set values(values: string[]) {
    this.formControl.setValue(values || []);
  }

  public get autoCompleteOptions() {
    return this._autoCompleteOptions;
  }

  @Input()
  public set autoCompleteOptions(autoCompleteOptions: Observable<string[]>) {
    this._autoCompleteOptions = autoCompleteOptions;

    let filteredOptions: Observable<string[]> = null;

    if (autoCompleteOptions != null) {
      filteredOptions = combineLatest([
        this.valueInputCtrl.valueChanges.pipe(
          startWith(this.valueInputCtrl.value)
        ),
        autoCompleteOptions
      ]).pipe(
        map(([value, allOptions]: [string, string[]]) => {
          if (value) {
            const inputValue = value.toLowerCase();
            return allOptions.filter(x => x.toLowerCase().indexOf(inputValue) === 0);
          } else {
            return allOptions;
          }
        }),
        shareReplay(1)
      );
    }

    this._filteredAutoCompleteOptions = filteredOptions;
  }

  public get filteredAutoCompleteOptions() {
    return this._filteredAutoCompleteOptions;
  }

  public get isAtMaxChips() {
    return this.maxChips && this.values.length >= this.maxChips;
  }

  ngAfterContentInit() {
    super.ngAfterContentInit();
    this._formSub.subscription = this.formControl.valueChanges.subscribe(() => {
      this.refreshDisabledInput();
    });
  }

  ngOnChanges(): void {
    this.refreshDisabledInput();
  }

  protected refreshDisabledInput() {
    const disableInput = (disable) => {
      if (disable) {
        this.textInput.nativeElement.value = '';
        this.valueInputCtrl.disable();
      } else {
        this.valueInputCtrl.enable();
      }
    };

    if (this.isAtMaxChips) {
      // Disable input if we're at the max or over the max number of values.
      disableInput(true);
    } else if (this.valueInputCtrl.disabled !== this.disabled) {
      // Propogate disabled to value input
      disableInput(this.disabled);
    }
  }

  // MARK: Input
  add(event: MatChipInputEvent): void {
    // Add value only when MatAutocomplete is not open to
    // make sure this does not conflict with OptionSelected Event
    if (!this.matAutocomplete.isOpen) {
      const input = event.input;
      const value = (event.value || '').trim();

      if (value) {
        this.addValue(value);
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
    this.addValue(event.option.viewValue);
    this.textInput.nativeElement.value = '';
    this.valueInputCtrl.setValue(null);
  }

  // MARK: Internal
  protected addValue(value: string) {
    if (!this.isAtMaxChips && value) {
      this.values = this.values.concat(value);
    }
  }

}
