import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, ContentChild, ContentChildren, ViewChildren } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { TestFooCreateActionDirective } from '../action/create.directive.spec';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeCreateModelFormControllerDirective } from './create.directive';
import { GaeModelComponentsModule } from '../model.module';
import { GaeTestFooModelFormComponent } from '../../form/model.component.spec';
import { GaeFormComponentsModule } from '../../form/form.module';
import { TestFoo } from '@gae-web/appengine-api';

describe('GaeCreateModelFormControllerDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestFooCreateActionDirective,
        GaeTestFooModelFormComponent,
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
      <ng-container gaeTestFooCreateAction #action="gaeTestFooCreateAction"></ng-container>
      <gae-test-model-model-form #form></gae-test-model-model-form>
      <gae-submit-view #submit [hidden]="true"></gae-submit-view>
      <ng-container gaeCreateModelFormController #control="gaeCreateModelFormController" [action]="action" [form]="form" [submit]="submit"></ng-container>
    </div>
  `
})
class TestViewComponent {

  @ViewChild(GaeCreateModelFormControllerDirective, {static: false})
  public action: GaeCreateModelFormControllerDirective<TestFoo>;

  @ViewChild(GaeCreateModelFormControllerDirective, {static: false})
  public directive: GaeCreateModelFormControllerDirective<TestFoo>;

}
