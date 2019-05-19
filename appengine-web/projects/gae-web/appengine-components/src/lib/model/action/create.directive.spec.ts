import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractCreateActionDirective } from './create.directive';
import { GaeModelComponentsModule } from '../model.module';
import { CreateService, CreateRequest, CreateResponse, TestFoo } from '@gae-web/appengine-api';


describe('TestFooCreateActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestFooCreateActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestFooCreateActionDirective;
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

export class TestFooCreateService implements CreateService<TestFoo> {

  create(request: CreateRequest<TestFoo>): Observable<CreateResponse<TestFoo>> {
    const createResponse: CreateResponse<TestFoo> = {
      models: ValueUtility.normalizeArray(request.templates),
      invalidTemplates: []
    };

    return of(createResponse);
  }

}

@Directive({
  selector: '[gaeTestFooCreateAction]',
  exportAs: 'gaeTestFooCreateAction'
})
export class TestFooCreateActionDirective extends AbstractCreateActionDirective<TestFoo> {

  constructor() {
    super(new TestFooCreateService());
  }

}

@Component({
  template: `
    <ng-container gaeTestFooCreateAction #action="gaeTestFooCreateAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestFooCreateActionDirective)
  public directive: TestFooCreateActionDirective;

}
