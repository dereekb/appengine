import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractUpdateActionDirective } from './update.directive';
import { GaeModelComponentsModule } from '../model.module';
import { UpdateService, UpdateRequest, UpdateResponse, TestFoo } from '@gae-web/appengine-api';


describe('TestFooUpdateActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestFooUpdateActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestFooUpdateActionDirective;
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

export class TestFooUpdateService implements UpdateService<TestFoo> {

  update(request: UpdateRequest<TestFoo>): Observable<UpdateResponse<TestFoo>> {
    const templates = ValueUtility.normalizeArray(request.templates);

    const updateResponse: UpdateResponse<TestFoo> = {
      models: templates,
      missing: [],
      invalidTemplates: [],
      failed: []
    };

    return of(updateResponse);
  }

}

@Directive({
  selector: '[gaeTestFooUpdateAction]',
  exportAs: 'gaeTestFooUpdateAction'
})
export class TestFooUpdateActionDirective extends AbstractUpdateActionDirective<TestFoo> {

  constructor() {
    super(new TestFooUpdateService());
  }

}

@Component({
  template: `
    <ng-container gaeTestFooUpdateAction #action="gaeTestFooUpdateAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestFooUpdateActionDirective, {static: false})
  public directive: TestFooUpdateActionDirective;

}
