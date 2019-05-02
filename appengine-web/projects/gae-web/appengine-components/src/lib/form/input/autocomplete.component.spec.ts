import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input } from '@angular/core';
import { GaeFormComponentsModule } from '../form.module';
import { GaeAutoCompleteFormControlComponent } from './autocomplete.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractFormGroupComponent } from '../form.component';
import { FormBuilder, Validators } from '@angular/forms';

describe('GaeAutoCompleteFormControlComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestAutoCompleteViewComponent
      ]
    }).compileComponents();
  }));

  let component: GaeAutoCompleteFormControlComponent;
  let testComponent: TestAutoCompleteViewComponent;
  let fixture: ComponentFixture<TestAutoCompleteViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestAutoCompleteViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

  it('should be linked with the gaeFormGroupErrors.', () => {
    expect(component.isListeningToErrors).toBeTrue();
  });

});

@Component({
  template: `
  <form [formGroup]="form" [gaeFormGroupErrors]="controlErrorsObs" novalidate>
    <gae-auto-complete-form-control [form]="form" field="testField" required="true"></gae-auto-complete-form-control>
  </form>
  `
})
class TestAutoCompleteViewComponent extends AbstractFormGroupComponent {

  @ViewChild(GaeAutoCompleteFormControlComponent)
  public component: GaeAutoCompleteFormControlComponent;

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
