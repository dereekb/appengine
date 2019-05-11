import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { TestModel } from '../model/resource/read.component.spec';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, ListViewSourceState } from './source';
import { GaeTestModelListComponent, GaeTestModelListContentComponent } from './list-view.component.spec';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { GaeListViewReadSourceDirective } from './source.directive';
import { ReadSource } from '@gae-web/appengine-client';
import { ReadService, ReadRequest, ReadResponse } from '@gae-web/appengine-api';
import { Observable, throwError, of } from 'rxjs';
import { TestListViewSourceFactory } from './source.spec';

fdescribe('ListViewWrapperComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeListComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [GaeTestModelListComponent, GaeTestModelListContentComponent, TestViewComponent]
    }).compileComponents();
  }));

  let component: GaeTestModelListComponent;
  let wrapper: GaeListViewWrapperComponent<TestModel>;
  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    wrapper = component.wrapperComponent;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(wrapper).toBeDefined();
  });

  describe('in error state', () => {

    beforeEach(async(() => {
      testComponent.source = TestListViewSourceFactory.makeErrorSource();
      fixture.detectChanges();
    }));

    it('should have an error.', (done) => {
      wrapper.loadingContext.stream.subscribe((x) => {
        expect(x.error).toBeDefined();
        done();
      });
    });

    it('should shown the error when the source has an error.', () => {
      // TODO: Show
    });

    // TODO: If refresh is pressed again while it an error it should show the error again.

  });

});

@Component({
  template: `
    <gae-test-model-list [source]="source"></gae-test-model-list>
  `
})
class TestViewComponent {

  @ViewChild(GaeTestModelListComponent)
  public component: GaeTestModelListComponent;

  public source: ListViewSource<TestModel>;

}
