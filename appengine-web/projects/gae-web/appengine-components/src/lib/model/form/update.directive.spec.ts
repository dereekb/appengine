import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, ContentChild, ContentChildren, ViewChildren } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { TestFooUpdateActionDirective } from '../action/update.directive.spec';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeUpdateModelFormControllerDirective } from './update.directive';
import { GaeModelComponentsModule } from '../model.module';
import { GaeTestFooModelFormComponent } from '../../form/model.component.spec';
import { GaeFormComponentsModule } from '../../form/form.module';
import { TestFoo } from '@gae-web/appengine-api';
import { Foo } from 'src/app/secure/shared/api/model/foo/foo';
import { ActionState } from '../../shared/action';
import { filter } from 'rxjs/operators';

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

  it('should have a model', () => {
    expect(directive.form.model).toBeDefined();
  });

  describe('controllerStream', () => {

    it('should be in a reset state initially', () => {
      expect(directive.lastControllerEvent.state).toBe(ActionState.Reset);
    });

    it('should enter a working state when submitted.', (done) => {
      directive.controllerStream.pipe(
        filter(x => x.state === ActionState.Working)
      ).subscribe({
        next: (x) => {
          expect(directive.lastControllerEvent.state).toBe(ActionState.Working);
          done();
        }
      });

      directive.submit.submit();
    });


    it('should enter a complete when completed.', (done) => {
      directive.controllerStream.pipe(
        filter(x => x.state === ActionState.Complete)
      ).subscribe({
        next: (x) => {
          expect(directive.lastControllerEvent.state).toBe(ActionState.Complete);
          done();
        }
      });

      directive.submit.submit();
    });

  });

});

@Component({
  template: `
    <div class="gae-form-container-form-view" gaeUpdateModelFormController #control="gaeUpdateModelFormController" [model]="modelObs"
    [action]="action" [form]="form" [submit]="submit">
      <ng-container gaeTestFooUpdateAction #action="gaeTestFooUpdateAction"></ng-container>
      <gae-test-model-model-form #form></gae-test-model-model-form>
      <gae-submit-button #submit [hidden]="true"></gae-submit-button>
    </div>
  `
})
class TestViewComponent {

  @ViewChild(GaeUpdateModelFormControllerDirective, { static: true })
  public action: GaeUpdateModelFormControllerDirective<TestFoo>;

  @ViewChild(GaeUpdateModelFormControllerDirective, { static: true })
  public directive: GaeUpdateModelFormControllerDirective<TestFoo>;

  public modelObs: Observable<Foo> = of(new Foo());

}
