import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractLinkActionDirective } from './link.directive';
import { TestModel } from '../resource/read.component.spec';
import { GaeModelComponentsModule } from '../model.module';
import { LinkService, LinkRequest, LinkResponse } from '@gae-web/appengine-api/public-api';


describe('TestModelLinkActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestModelLinkActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestModelLinkActionDirective;
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

export class TestModelLinkService implements LinkService {

  updateLinks(request: LinkRequest): Observable<LinkResponse> {
    const linkResponse: LinkResponse = {
      successful: request.changes.map(x => x.id),
      missing: [],
      failed: []
    };

    return of(linkResponse);
  }

}

@Directive({
  selector: '[gaeTestModelLinkAction]',
  exportAs: 'gaeTestModelLinkAction'
})
export class TestModelLinkActionDirective extends AbstractLinkActionDirective {

  constructor() {
    super(new TestModelLinkService());
  }

}

@Component({
  template: `
    <ng-container gaeTestModelLinkAction #action="gaeTestModelLinkAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestModelLinkActionDirective)
  public directive: TestModelLinkActionDirective;

}
