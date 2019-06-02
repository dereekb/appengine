import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { GaeFormComponentsModule } from '../form.module';
import { GaeInputFormControlComponent } from './input.component';
import { AbstractFormGroupComponent } from '../form.component';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('GaeInputFormControlComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestInputFormControlViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeInputFormControlComponent;
  let testComponent: TestInputFormControlViewComponent;
  let fixture: ComponentFixture<TestInputFormControlViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestInputFormControlViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

});

@Component({
  template: `
  <form [formGroup]="form" [gaeFormGroupErrors]="controlErrorsObs" novalidate>
    <gae-input-form-control [form]="form" field="testField" required="true"></gae-input-form-control>
  </form>
  `
})
class TestInputFormControlViewComponent extends AbstractFormGroupComponent {

  @ViewChild(GaeInputFormControlComponent, {static: false})
  public component: GaeInputFormControlComponent;

  validationMessages = {
    testField: {
      required: 'TestField is required.',
    }
  };

  constructor(private _formBuilder: FormBuilder) {
    super();

    this.setFormGroup(this._formBuilder.group({
      testField: ['', [Validators.required]]
    }));
  }

}
