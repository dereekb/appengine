import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, ContentChild, ContentChildren, ViewChildren } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { TestFooUpdateActionDirective } from '../action/update.directive.spec';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeUpdateModelFormControllerDirective } from './update.directive';
import { GaeModelComponentsModule } from '../model.module';
import { GaeTestFooModelFormComponent } from '../../form/model.component.spec';
import { GaeFormComponentsModule } from '../../form/form.module';
import { TestFoo } from '@gae-web/appengine-api';

describe('GaeUpdateModelFormControllerDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeFormComponentsModule,
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestFooUpdateActionDirective,
        GaeTestFooModelFormComponent,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: GaeUpdateModelFormControllerDirective<any>;
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
      <ng-container gaeTestFooUpdateAction #action="gaeTestFooUpdateAction"></ng-container>
      <gae-test-model-model-form #form></gae-test-model-model-form>
      <gae-submit-view #submit [hidden]="true"></gae-submit-view>
      <ng-container gaeUpdateModelFormController #control="gaeUpdateModelFormController" [action]="action" [form]="form" [submit]="submit"></ng-container>
    </div>
  `
})
class TestViewComponent {

  @ViewChild(GaeUpdateModelFormControllerDirective, {static: false})
  public action: GaeUpdateModelFormControllerDirective<TestFoo>;

  @ViewChild(GaeUpdateModelFormControllerDirective, {static: false})
  public directive: GaeUpdateModelFormControllerDirective<TestFoo>;

}
