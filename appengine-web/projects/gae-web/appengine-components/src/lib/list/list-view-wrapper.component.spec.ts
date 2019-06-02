import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, ListViewSourceState } from './source';
import { GaeTestFooListComponent, GaeTestFooListContentComponent } from './list-view.component.spec';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { TestFoo } from '@gae-web/appengine-api';
import { TestListViewSourceFactory } from './source.spec';
import { filter } from 'rxjs/operators';
import { GaeErrorComponent } from '../loading/error.component';

describe('ListViewWrapperComponent', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        GaeListComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [GaeTestFooListComponent, GaeTestFooListContentComponent, TestViewComponent]
    }).compileComponents();
  }));

  let component: GaeTestFooListComponent;
  let wrapper: GaeListViewWrapperComponent<TestFoo>;
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

  describe('it should show the toolbar', () => {

  });

  describe('with hide content', () => {

    describe('it should hide the toolbar', () => {

    });

    describe('it should hide the toolbar', () => {

    });

  });

  describe('complete', () => {

    describe('with elements', () => {

      const modelKeys = [1, 2, 3];

      beforeEach(async(() => {
        testComponent.source = TestListViewSourceFactory.makeSource(modelKeys);
        fixture.detectChanges();
      }));

      it('canLoadMore() on the component should be false', (done) => {
        wrapper.loadingContext.stream.pipe(
          filter((x) => x.isLoading === false)
        ).subscribe((x) => {
          expect(component.canLoadMore).toBe(false);
          done();
        });
      });

      it('should have elements', (done) => {
        wrapper.loadingContext.stream.pipe(
          filter((x) => x.isLoading === false)
        ).subscribe((x) => {
          expect(wrapper.count).toBe(modelKeys.length);
          expect(wrapper.state).toBe(ListViewSourceState.Done);
          done();
        });
      });

      it('should display the list.', (done) => {
        wrapper.loadingContext.stream.pipe(
          filter((x) => x.isLoading === false)
        ).subscribe((x) => {
          const listComponentQueryResult = fixture.debugElement.query(By.directive(GaeTestFooListContentComponent));
          expect(listComponentQueryResult).not.toBeNull();
          done();
        });
      });

      it('should display the list done content.', (done) => {
        wrapper.loadingContext.stream.pipe(
          filter((x) => x.isLoading === false)
        ).subscribe((x) => {
          const listComponentQueryResult = fixture.debugElement.query(By.css('.gae-list-view-done'));
          expect(listComponentQueryResult).not.toBeNull();
          done();
        });
      });

    });

    describe('with no elements', () => {

      beforeEach(async(() => {
        testComponent.source = TestListViewSourceFactory.makeEmptySource();
        fixture.detectChanges();
      }));

      it('should have no elements', (done) => {
        wrapper.loadingContext.stream.pipe(
          filter((x) => x.isLoading === false)
        ).subscribe((x) => {
          expect(wrapper.count).toBe(0);
          expect(wrapper.state).toBe(ListViewSourceState.Done);
          done();
        });
      });

      // TODO: It should show no elements.

    });

  });

  describe('incomplete', () => {

    // TODO: it should show load more.

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

    it('should display the error.', (done) => {
      wrapper.loadingContext.stream.subscribe((x) => {
        const errorComponentQueryResult = fixture.debugElement.query(By.directive(GaeErrorComponent));
        expect(errorComponentQueryResult).not.toBeNull();
        done();
      });
    });

    // TODO: If refresh is pressed again while it an error it should show the error again.

  });

});


const CUSTOM_TOOLBAR_ID = 'custom-toolbar';
const CUSTOM_EMPTY_ID = 'custom-empty';

@Component({
  template: `
    <gae-test-model-list [source]="source">
      <div toolbar>
        <p id="${CUSTOM_TOOLBAR_ID}"></p>
      </div>
      <div empty>
        <p id="${CUSTOM_EMPTY_ID}"></p>
      </div>
    </gae-test-model-list>
  `
})
class TestViewComponent {

  @ViewChild(GaeTestFooListComponent, {static: true})
  public component: GaeTestFooListComponent;

  public source: ListViewSource<TestFoo>;

}
