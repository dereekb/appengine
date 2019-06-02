import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { Component, ViewChild, Input, Directive } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel, ValueUtility } from '@gae-web/appengine-utility';
import { FormBuilder, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractLinkActionDirective } from './link.directive';
import { GaeModelComponentsModule } from '../model.module';
import { LinkService, LinkRequest, LinkResponse, TestFoo } from '@gae-web/appengine-api';


describe('TestFooLinkActionDirective', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeModelComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [
        TestFooLinkActionDirective,
        TestViewComponent
      ]
    }).compileComponents();
  }));

  let directive: TestFooLinkActionDirective;
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

export class TestFooLinkService implements LinkService {

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
  selector: '[gaeTestFooLinkAction]',
  exportAs: 'gaeTestFooLinkAction'
})
export class TestFooLinkActionDirective extends AbstractLinkActionDirective {

  constructor() {
    super(new TestFooLinkService());
  }

}

@Component({
  template: `
    <ng-container gaeTestFooLinkAction #action="gaeTestFooLinkAction"></ng-container>
  `
})
class TestViewComponent {

  @ViewChild(TestFooLinkActionDirective, {static: false})
  public directive: TestFooLinkActionDirective;

}
