import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef, ChangeDetectorRef } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent, ListViewState } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, ListViewSourceState } from './source';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { TestListViewSourceFactory } from './source.spec';
import { filter, flatMap } from 'rxjs/operators';
import { TestFoo } from '@gae-web/appengine-api';

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
          filter((x) => x && x.length > 0)
        ).subscribe((elements) => {
          expect(elements).toBeArray();
          expect(elements.length).toBe(modelKeys.length);
          done();
        });
      });

      it('canLoadMore() on the component should be false', (done) => {
        component.stream.pipe(
          flatMap((x) => x.source),
          filter((x) => x.state === ListViewSourceState.Done)
        ).subscribe((x) => {
          expect(component.canLoadMore).toBe(false);
          done();
        });
      });

    });

  });

  // TODO: Test when the source fails, etc.

});

@Component({
  selector: 'gae-test-model-list-content',
  template: `
    <div>TODO</div>
  `
})
export class GaeTestFooListContentComponent extends AbstractListContentComponent<TestFoo> {

  constructor(@Inject(forwardRef(() => GaeTestFooListComponent)) listView: GaeTestFooListComponent) {
    super(listView);
  }

}

@Component({
  selector: 'gae-test-model-list',
  template: `
  <gae-list-view-wrapper>
    <ng-content toolbar select="[toolbar]"></ng-content>
    <gae-test-model-list-content list></gae-test-model-list-content>
    <ng-content empty select="[empty]"></ng-content>
  </gae-list-view-wrapper>
  `,
  providers: [ProvideListViewComponent(GaeTestFooListComponent)]
})
export class GaeTestFooListComponent extends AbstractListViewComponent<TestFoo> {

  @ViewChild(GaeTestFooListContentComponent, {static: false})
  public component: GaeTestFooListContentComponent;

  @ViewChild(GaeListViewWrapperComponent, {static: false})
  public wrapperComponent: GaeListViewWrapperComponent<TestFoo>;

}

@Component({
  template: `
    <gae-test-model-list [source]="source"></gae-test-model-list>
  `
})
class TestViewComponent {

  @ViewChild(GaeTestFooListComponent, {static: false})
  public component: GaeTestFooListComponent;

  public source: ListViewSource<TestFoo>;

}
