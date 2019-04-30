import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { By } from '@angular/platform-browser';
import { GaeFormComponentsModule } from '../form.module';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { GaeSelectFormControlComponent } from './select.component';
import { AbstractFormGroupComponent } from '../form.component';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('Select Component', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestSelectFormControlViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeSelectFormControlComponent;
  let testComponent: TestSelectFormControlViewComponent;
  let fixture: ComponentFixture<TestSelectFormControlViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestSelectFormControlViewComponent);
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
    <gae-select-form-control [form]="form" field="testField" [hint]="hint" required="true"></gae-select-form-control>
  </form>
  `
})
class TestSelectFormControlViewComponent extends AbstractFormGroupComponent {

  @ViewChild(GaeSelectFormControlComponent)
  public component: GaeSelectFormControlComponent;

  validationMessages = {
    testField: {
      required: 'TestField is required.',
    }
  };

  constructor(private _formBuilder: FormBuilder) {
    super();

    this.setFormGroup(this._formBuilder.group({
      testField: [null, [Validators.required]]
    }));
  }

}
