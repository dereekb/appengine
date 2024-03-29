import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { GaeFormComponentsModule } from '../form.module';
import { GaeChipListFormControlComponent } from './chiplist.component';
import { AbstractFormGroupComponent } from '../form.component';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { Observable, of } from 'rxjs';
import { By } from '@angular/platform-browser';
import { MatChip, MatChipList } from '@angular/material';
import { filter } from 'rxjs/operators';

describe('GaeChipListFormControlComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestChipListFormControlViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeChipListFormControlComponent;
  let testComponent: TestChipListFormControlViewComponent;
  let fixture: ComponentFixture<TestChipListFormControlViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestChipListFormControlViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

  /*
  // TODO
  describe('Input', () => {

    it('should add a value via the text input', () => {

      const chipListElement = fixture.debugElement.query(By.directive(MatChipList));
      const chipListInput = chipListElement.query(By.css('input'));

      chipListInput.nativeElement.value = 'test';
      chipListInput.nativeElement.dispatchEvent(new Event('input'));
      fixture.detectChanges();

      chipListInput.nativeElement.dispatchEvent(new KeyboardEvent('keydown', { key: 'Enter' }));
    });

  });
  */

  describe('With Values', () => {

    const values = ['a', 'b', 'c'];

    beforeEach(() => {
      component.values = values;
      fixture.detectChanges();
    });

    it('the form should be valid', () => {
      expect(testComponent.isValid);
    });

    it('should have the chip values set', () => {
      expect(component.values).toBeArrayOfStrings();
      expect(component.values.length).toBe(3);
    });

    it('should show the set chip values', () => {
      const visibleChipsResults = fixture.debugElement.queryAll(By.directive(MatChip));
      expect(visibleChipsResults).not.toBeNull();
      expect(visibleChipsResults.length).toBe(3);
    });

    it('should remove a value when remove on an chip is clicked', () => {
      const visibleChipsResult = fixture.debugElement.query(By.directive(MatChip));
      const removeButton = visibleChipsResult.query(By.css('.mat-chip-remove'));

      expect(removeButton).not.toBeNull();
      removeButton.triggerEventHandler('click', null);
      fixture.detectChanges();

      const visibleChipsResults = fixture.debugElement.queryAll(By.directive(MatChip));
      expect(visibleChipsResults).not.toBeNull();
      expect(visibleChipsResults.length).toBe(2);
    });

  });

  describe('With Autocomplete', () => {

    const values = ['a', 'b', 'c'];

    beforeEach(() => {
      testComponent.autoCompleteOptions = of(values);
      fixture.detectChanges();
    });

    it('should have autocomplete values set.', () => {
      expect(testComponent.autoCompleteOptions).not.toBeNull();
      expect(component.autoCompleteOptions).not.toBeNull();
      expect(component.filteredAutoCompleteOptions).not.toBeNull();
    });

    describe('with input set', () => {

      beforeEach(() => {
        component.valueInputCtrl.setValue('a');
        fixture.detectChanges();
      });

      it('should filter based on the value', (done) => {
        component.filteredAutoCompleteOptions.pipe(
          filter(x => x.length === 1)
        ).subscribe((x) => {
          expect(x.length).toBe(1);
          done();
        });
      });

    });

    describe('with max and value at max set', () => {

      beforeEach(() => {
        testComponent.maxChips = values.length;
        component.values = values;
        fixture.detectChanges();
      });

      it('should have disabled the value input', () => {
        expect(component.valueInputCtrl.disabled).toBeTruthy();
      });

    });

  });

  /*
  describe('Without Autocomplete', () => {

    beforeEach(() => {
      testComponent.autoCompleteOptions = undefined;
      fixture.detectChanges();
    });

    // TODO: ?

  });
  */

});

@Component({
  template: `
  <form [formGroup]="form" [gaeFormGroupErrors]="controlErrorsObs" novalidate>
    <gae-chiplist-form-control [autoCompleteOptions]="autoCompleteOptions" [maxChips]="maxChips" [form]="form" field="testField" required="true"></gae-chiplist-form-control>
  </form>
  `
})
class TestChipListFormControlViewComponent extends AbstractFormGroupComponent {

  @Input()
  public autoCompleteOptions: Observable<any>;

  @Input()
  public maxChips: number;

  @ViewChild(GaeChipListFormControlComponent, { static: true })
  public component: GaeChipListFormControlComponent;

  validationMessages = {
    testField: {
      required: 'TestField is required.'
    }
  };

  buildFormGroup(formBuilder: FormBuilder) {
    return formBuilder.group({
      testField: [[], [Validators.required]]
    });
  }

}
