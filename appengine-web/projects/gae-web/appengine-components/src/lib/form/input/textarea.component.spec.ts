import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { By } from '@angular/platform-browser';
import { GaeFormComponentsModule } from '../form.module';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { GaeTextareaFormControlComponent } from './textarea.component';
import { AbstractFormGroupComponent } from '../form.component';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('GaeTextareaFormControlComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestTextAreaFormControlViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeTextareaFormControlComponent;
  let testComponent: TestTextAreaFormControlViewComponent;
  let fixture: ComponentFixture<TestTextAreaFormControlViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestTextAreaFormControlViewComponent);
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
    <gae-textarea-form-control [form]="form" field="testField" required="true"></gae-textarea-form-control>
  </form>
  `
})
class TestTextAreaFormControlViewComponent extends AbstractFormGroupComponent {

  @ViewChild(GaeTextareaFormControlComponent, { static: true })
  public component: GaeTextareaFormControlComponent;

  validationMessages = {
    testField: {
      required: 'TestField is required.',
    }
  };

  buildFormGroup(formBuilder: FormBuilder) {
    return formBuilder.group({
      testField: ['', [Validators.required]]
    });
  }

}
