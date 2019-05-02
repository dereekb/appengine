import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractCreateActionDirective } from './create.directive';
import { TestModel } from '../resource/read.component.spec';
import { GaeModelComponentsModule } from '../model.module';
import { CreateService, CreateRequest, CreateResponse } from '@gae-web/appengine-api/public-api';


describe('TestModelCreateActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestModelCreateActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestModelCreateActionDirective;
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

});

export class TestModelCreateService implements CreateService<TestModel> {

  create(request: CreateRequest<TestModel>): Observable<CreateResponse<TestModel>> {
    const createResponse: CreateResponse<TestModel> = {
      models: ValueUtility.normalizeArray(request.templates),
      invalidTemplates: []
    };

    return of(createResponse);
  }

}

@Directive({
  selector: '[gaeTestModelCreateAction]',
  exportAs: 'gaeTestModelCreateAction'
})
export class TestModelCreateActionDirective extends AbstractCreateActionDirective<TestModel> {

  constructor() {
    super(new TestModelCreateService());
  }

}

@Component({
  template: `
    <ng-container gaeTestModelCreateAction #action="gaeTestModelCreateAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestModelCreateActionDirective)
  public directive: TestModelCreateActionDirective;

}
