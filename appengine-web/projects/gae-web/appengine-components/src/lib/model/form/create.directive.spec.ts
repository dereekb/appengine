import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, ContentChild, ContentChildren, ViewChildren } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { TestModelCreateActionDirective } from '../action/create.directive.spec';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeCreateModelFormControllerDirective } from './create.directive';
import { TestModel } from '../resource/read.component.spec';
import { GaeModelComponentsModule } from '../model.module';
import { GaeTestModelModelFormComponent } from '../../form/model.component.spec';
import { GaeFormComponentsModule } from '../../form/form.module';

describe('GaeCreateModelFormControllerDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestModelCreateActionDirective,
        GaeTestModelModelFormComponent,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: GaeCreateModelFormControllerDirective<any>;
  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    directive = testComponent.directive;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(directive).toBeDefined();
  });

  it('should be linked to the action', () => {
    expect(directive.action).toBeDefined();
  });

  it('should be linked to the form', () => {
    expect(directive.form).toBeDefined();
  });

});

@Component({
  template: `
    <div>
      <ng-container gaeTestModelCreateAction #action="gaeTestModelCreateAction"></ng-container>
      <gae-test-model-model-form #form></gae-test-model-model-form>
      <gae-submit-view #submit [hidden]="true"></gae-submit-view>
      <ng-container gaeCreateModelFormController #control="gaeCreateModelFormController" [action]="action" [form]="form" [submit]="submit"></ng-container>
    </div>
  `
})
class TestViewComponent {

  @ViewChild(GaeCreateModelFormControllerDirective)
  public action: GaeCreateModelFormControllerDirective<TestModel>;

  @ViewChild(GaeCreateModelFormControllerDirective)
  public directive: GaeCreateModelFormControllerDirective<TestModel>;

}
