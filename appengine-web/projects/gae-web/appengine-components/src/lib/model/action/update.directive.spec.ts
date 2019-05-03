import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractUpdateActionDirective } from './update.directive';
import { TestModel } from '../resource/read.component.spec';
import { GaeModelComponentsModule } from '../model.module';
import { UpdateService, UpdateRequest, UpdateResponse } from '@gae-web/appengine-api/public-api';


describe('TestModelUpdateActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestModelUpdateActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestModelUpdateActionDirective;
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

export class TestModelUpdateService implements UpdateService<TestModel> {

  update(request: UpdateRequest<TestModel>): Observable<UpdateResponse<TestModel>> {
    const templates = ValueUtility.normalizeArray(request.templates);

    const updateResponse: UpdateResponse<TestModel> = {
      models: templates,
      missing: [],
      invalidTemplates: [],
      failed: []
    };

    return of(updateResponse);
  }

}

@Directive({
  selector: '[gaeTestModelUpdateAction]',
  exportAs: 'gaeTestModelUpdateAction'
})
export class TestModelUpdateActionDirective extends AbstractUpdateActionDirective<TestModel> {

  constructor() {
    super(new TestModelUpdateService());
  }

}

@Component({
  template: `
    <ng-container gaeTestModelUpdateAction #action="gaeTestModelUpdateAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestModelUpdateActionDirective)
  public directive: TestModelUpdateActionDirective;

}
