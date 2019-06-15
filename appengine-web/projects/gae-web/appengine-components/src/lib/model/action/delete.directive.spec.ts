import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility, NumberModelKey } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractDeleteActionDirective } from './delete.directive';
import { GaeModelComponentsModule } from '../model.module';
import { DeleteService, DeleteRequest, DeleteResponse, TestFoo } from '@gae-web/appengine-api';


describe('TestFooDeleteActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestFooDeleteActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestFooDeleteActionDirective;
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

export class TestFooDeleteService implements DeleteService<TestFoo> {

  delete(request: DeleteRequest): Observable<DeleteResponse<TestFoo>> {
    const keys = ValueUtility.normalizeArray(request.modelKeys);

    const deleteResponse: DeleteResponse<TestFoo> = {
      keys,
      isModelsResponse: request.shouldReturnModels,
      failed: [],
      models: (request.shouldReturnModels) ? keys.map(x => new TestFoo(x as NumberModelKey)) : []
    };

    return of(deleteResponse);
  }

}

@Directive({
  selector: '[gaeTestFooDeleteAction]',
  exportAs: 'gaeTestFooDeleteAction'
})
export class TestFooDeleteActionDirective extends AbstractDeleteActionDirective<TestFoo> {

  constructor() {
    super(new TestFooDeleteService());
  }

}

@Component({
  template: `
    <ng-container gaeTestFooDeleteAction #action="gaeTestFooDeleteAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestFooDeleteActionDirective, {static: true})
  public directive: TestFooDeleteActionDirective;

}
