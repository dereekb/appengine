import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractDeleteActionDirective } from './delete.directive';
import { TestModel } from '../resource/read.component.spec';
import { GaeModelComponentsModule } from '../model.module';
import { DeleteService, DeleteRequest, DeleteResponse } from '@gae-web/appengine-api/public-api';


describe('TestModelDeleteActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestModelDeleteActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestModelDeleteActionDirective;
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

export class TestModelDeleteService implements DeleteService<TestModel> {

  delete(request: DeleteRequest): Observable<DeleteResponse<TestModel>> {
    const keys = ValueUtility.normalizeArray(request.modelKeys);

    const deleteResponse: DeleteResponse<TestModel> = {
      keys,
      isModelsResponse: request.shouldReturnModels,
      failed: [],
      models: (request.shouldReturnModels) ? keys.map(x => new TestModel(x)) : []
    };

    return of(deleteResponse);
  }

}

@Directive({
  selector: '[gaeTestModelDeleteAction]',
  exportAs: 'gaeTestModelDeleteAction'
})
export class TestModelDeleteActionDirective extends AbstractDeleteActionDirective<TestModel> {

  constructor() {
    super(new TestModelDeleteService());
  }

}

@Component({
  template: `
    <ng-container gaeTestModelDeleteAction #action="gaeTestModelDeleteAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestModelDeleteActionDirective)
  public directive: TestModelDeleteActionDirective;

}
