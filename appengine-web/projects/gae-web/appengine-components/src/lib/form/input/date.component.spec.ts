import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { By } from '@angular/platform-browser';
import { GaeFormComponentsModule } from '../form.module';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { GaeDateFormControlComponent } from './date.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractFormGroupComponent } from '../form.component';
import { FormBuilder, Validators } from '@angular/forms';

describe('GaeDateFormControlComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestDateFormControlViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeDateFormControlComponent;
  let testComponent: TestDateFormControlViewComponent;
  let fixture: ComponentFixture<TestDateFormControlViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestDateFormControlViewComponent);
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
    <gae-date-form-control [form]="form" field="testField" required="true"></gae-date-form-control>
  </form>
  `
})
class TestDateFormControlViewComponent extends AbstractFormGroupComponent {

  @ViewChild(GaeDateFormControlComponent)
  public component: GaeDateFormControlComponent;

  validationMessages = {
    testField: {
      required: 'TestField is required.',
    }
  };

  constructor(private _formBuilder: FormBuilder) {
    super();

    this.setFormGroup(this._formBuilder.group({
      testField: [new Date(), [Validators.required]]
    }));
  }

}
