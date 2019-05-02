import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input } from '@angular/core';
import { By } from '@angular/platform-browser';
import { GaeFormComponentsModule } from './form.module';
import { Observable, of } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { GaeCheckboxFormControlComponent } from './input/checkbox.component';
import { TestModel } from '../model/resource/read.component.spec';
import { AbstractModelFormComponent } from './model.component';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';

describe('AbstractModelFormComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [GaeTestModelModelFormComponent, TestViewComponent]
    }).compileComponents();
  }));

  let component: GaeTestModelModelFormComponent;
  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

});

@Component({
  selector: 'gae-test-model-model-form',
  template: `
  <form class="form-view" [formGroup]="form" [gaeFormGroupErrors]="controlErrorsObs" fxLayout="column" novalidate>
    <gae-input-form-control [form]="form" field="name" placeholder="Name" [required]="nameRequired" fxFlex="100%"></gae-input-form-control>
  </form>
  `
})
export class GaeTestModelModelFormComponent extends AbstractModelFormComponent<TestModel> {

  public nameRequired = false;

  validationMessages = {
    name: {
      required: 'Name is required.',
    }
  };

  constructor(formBuilder: FormBuilder) {
    super(formBuilder);
  }

  // MARK: Internal
  protected makeNewFormGroup(): FormGroup {
    return this._formBuilder.group({
      key: [0],
      name: ['', [Validators.maxLength(24)]]
    });
  }

  protected get newModelData(): any {
    return {
      name: ''
    };
  }

  protected convertToFormData(model: TestModel): any {
    const data: any = {};

    data.key = model.key;
    data.name = model.name;

    return data;
  }

  protected makeForSubmission(value: any): TestModel {
    const model = new TestModel();

    model.modelKey = value.key;
    model.name = value.name;

    return model;
  }

}

@Component({
  template: `
    <gae-test-model-model-form></gae-test-model-model-form>
  `
})
class TestViewComponent {

  @ViewChild(GaeTestModelModelFormComponent)
  public component: GaeTestModelModelFormComponent;

}
