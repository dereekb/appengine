import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef, ChangeDetectorRef } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent, ListViewState, ListViewItemClickedEvent } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, ListViewSourceState } from './source';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { TestListViewSourceFactory } from './source.spec';
import { filter, mergeMap, tap } from 'rxjs/operators';
import { TestFoo } from '@gae-web/appengine-api';
import { GaeListLoadMoreComponent } from './load-more.component';
import { By } from '@angular/platform-browser';

describe('ListViewComponent', () => {

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
  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  it('should be created', () => {
    expect(component).toBeDefined();
  });

  describe('complete', () => {

    describe('with elements', () => {

      const modelKeys = [1, 2, 3];

      beforeEach(async(() => {
        testComponent.source = TestListViewSourceFactory.makeSource(modelKeys);
        fixture.detectChanges();
      }));

      it('should load the elements.', (done) => {
        component.elements.pipe(
          tap((x) => {
            console.log(x);
          }),
          filter((x) => x && x.length > 0)
        ).subscribe((elements) => {
          expect(elements).toBeArray();
          expect(elements.length).toBe(modelKeys.length);
          done();
        });
      });

      describe('on click', () => {

        function doClick() {
          const element = fixture.debugElement.query(By.css(`#${LIST_VIEW_CONTENT_TEST_BUTTON_ID}`));
          element.triggerEventHandler('click', {} as MouseEvent);
          return fixture.whenStable();
        }

        it('should have called onItemSelected() callback.', (done) => {
          let clicked = false;

          testComponent.onItemSelected = (x) => {
            expect(x).toBeDefined();
            clicked = true;
          };

          doClick().then(() => {
            expect(clicked).toBeTrue();
            done();
          });
        });

        it('should have called onItemClicked() callback.', (done) => {
          let clicked = false;

          testComponent.onItemClicked = (x) => {
            expect(x.selected).toBeDefined();
            expect(x.event).toBeDefined();
            clicked = true;
          };

          doClick().then(() => {
            expect(clicked).toBeTrue();
            done();
          });
        });

      });

      describe('and cannot load more', () => {

        it('canLoadMore() on the component should be false', (done) => {
          component.stream.pipe(
            mergeMap((x) => x.source),
            filter((x) => x.state === ListViewSourceState.Done)
          ).subscribe((x) => {
            expect(component.canLoadMore).toBe(false);
            done();
          });
        });

        describe('GaeListLoadMoreButton', () => {

          it('should not be able to load more.', () => {
            expect(component.contentComponent.loadMoreButtonComponent.canLoadMore).toBe(false);
          });

        });

      });

    });

  });

  // TODO: Test when the source fails, etc.

});

export const LIST_VIEW_CONTENT_TEST_BUTTON_ID = 'test-button';

@Component({
  selector: 'gae-test-model-list-content',
  template: `
  <div>
    <div>LIST</div>
    <button id="${LIST_VIEW_CONTENT_TEST_BUTTON_ID}" (click)="testItemClicked($event)"></button>
    <gae-list-load-more></gae-list-load-more>
  </div>
  `
})
export class GaeTestFooListContentComponent extends AbstractListContentComponent<TestFoo> {

  @ViewChild(GaeListLoadMoreComponent, { static: true })
  public loadMoreButtonComponent: GaeListLoadMoreComponent;

  constructor(@Inject(forwardRef(() => GaeTestFooListComponent)) listView: any) {
    super(listView);
  }

  testItemClicked(event: MouseEvent) {
    this.select(new TestFoo(), event);
  }

}

@Component({
  selector: 'gae-test-model-list',
  template: `
  <gae-list-view-wrapper>
    <ng-content controls select="[controls]"></ng-content>
    <gae-test-model-list-content list></gae-test-model-list-content>
    <ng-content empty select="[empty]"></ng-content>
  </gae-list-view-wrapper>
  `,
  providers: [ProvideListViewComponent(GaeTestFooListComponent)]
})
export class GaeTestFooListComponent extends AbstractListViewComponent<TestFoo> {

  @ViewChild(GaeTestFooListContentComponent, { static: true })
  public contentComponent: GaeTestFooListContentComponent;

  @ViewChild(GaeListViewWrapperComponent, { static: true })
  public wrapperComponent: GaeListViewWrapperComponent<TestFoo>;

}

@Component({
  template: `
    <gae-test-model-list [source]="source" (itemClicked)="onItemClicked($event)" (itemSelected)="onItemSelected($event)"></gae-test-model-list>
  `
})
class TestViewComponent {

  @ViewChild(GaeTestFooListComponent, { static: true })
  public component?: GaeTestFooListComponent;

  public source: ListViewSource<TestFoo>;

  public onItemSelected = (item: TestFoo) => undefined;

  public onItemClicked = (item: ListViewItemClickedEvent<TestFoo>) => undefined;

}
