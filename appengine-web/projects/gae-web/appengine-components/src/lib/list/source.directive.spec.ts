import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, ListViewSourceState } from './source';
import { GaeTestFooListComponent, GaeTestFooListContentComponent } from './list-view.component.spec';
import { GaeListViewKeyQuerySourceDirective } from './source.directive';
import { ReadSource, TestFooReadSourceFactory, TestFooTestReadSourceFactory, TestFooTestKeyQuerySource } from '@gae-web/appengine-client';
import { TestFoo, TestFooReadService } from '@gae-web/appengine-api';
import { first, filter } from 'rxjs/operators';
import { SourceState } from '@gae-web/appengine-utility';

describe('GaeListViewReadSourceDirectives', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeListComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [GaeTestFooListComponent, GaeTestFooListContentComponent, GaeListViewReadSourceDirectiveTestViewComponent]
    }).compileComponents();
  }));

  let component: GaeTestFooListComponent;
  let testComponent: GaeListViewReadSourceDirectiveTestViewComponent;
  let fixture: ComponentFixture<GaeListViewReadSourceDirectiveTestViewComponent>;
  let sourceDirective: GaeListViewKeyQuerySourceDirective<TestFoo>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(GaeListViewReadSourceDirectiveTestViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    sourceDirective = testComponent.sourceDirective;
    fixture.detectChanges();
  }));

  describe('with query and read source', () => {

    let testReadSource: ReadSource<TestFoo>;
    let testQuerySource: TestFooTestKeyQuerySource;

    beforeEach(() => {
      testReadSource = TestFooTestReadSourceFactory.makeReadSource();
      testQuerySource = new TestFooTestKeyQuerySource();
      sourceDirective.querySource = testQuerySource;
      sourceDirective.readSource = testReadSource;
    });

    describe('with no query results', () => {
      const testKeyResults = [];

      beforeEach(() => {
        testQuerySource.testQueryService.keyResults = testKeyResults;
      });

      it('should hit done state when the query returns nothing.', (done) => {

        // Load next elements.
        sourceDirective.more();

        // Wait for the stream to complete.
        sourceDirective.stream.pipe(
          filter((x) => {
            return x.state === ListViewSourceState.Done;
          }),
          first()
        ).subscribe((x) => {
          expect(x.elements.length).toBe(0);
          done();
        });
      });

    });

    describe('with query results', () => {
      const testKeyResults = [1, 2, 3, 4];

      beforeEach(() => {
        testQuerySource.testQueryService.keyResults = testKeyResults;
      });

    });

  });

});


@Component({
  template: `
    <gae-test-model-list gaeListViewKeyQuerySource></gae-test-model-list>
  `
})
class GaeListViewReadSourceDirectiveTestViewComponent {

  @ViewChild(GaeTestFooListComponent)
  public component: GaeTestFooListComponent;

  @ViewChild(GaeListViewKeyQuerySourceDirective)
  public sourceDirective: GaeListViewKeyQuerySourceDirective<TestFoo>;

  public source: ListViewSource<TestFoo>;

}
