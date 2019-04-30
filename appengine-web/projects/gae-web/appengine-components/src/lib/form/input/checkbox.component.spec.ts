import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { By } from '@angular/platform-browser';
import { GaeFormComponentsModule } from '../form.module';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { GaeCheckboxFormControlComponent } from './checkbox.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractFormGroupComponent } from '../form.component';
import { FormBuilder, Validators } from '@angular/forms';

describe('GaeCheckboxFormControlComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestCheckboxFormControlViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeCheckboxFormControlComponent;
  let testComponent: TestCheckboxFormControlViewComponent;
  let fixture: ComponentFixture<TestCheckboxFormControlViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestCheckboxFormControlViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

  it('should not be checked', () => {
    expect(component.checked).toBeFalse();
  });

});

@Component({
  template: `
  <form [formGroup]="form" [gaeFormGroupErrors]="controlErrorsObs" novalidate>
    <gae-checkbox-form-control [form]="form" field="testField" required="true"></gae-checkbox-form-control>
  </form>
  `
})
class TestCheckboxFormControlViewComponent extends AbstractFormGroupComponent {

  @ViewChild(GaeCheckboxFormControlComponent)
  public component: GaeCheckboxFormControlComponent;

  validationMessages = {
    testField: {
      required: 'TestField is required.',
    }
  };

  constructor(private _formBuilder: FormBuilder) {
    super();

    this.setFormGroup(this._formBuilder.group({
      testField: [false, [Validators.required]]
    }));
  }

}
