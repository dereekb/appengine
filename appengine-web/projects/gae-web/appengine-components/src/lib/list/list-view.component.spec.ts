import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef } from '@angular/core';
import { By } from '@angular/platform-browser';
import { Observable, of } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { TestModel } from '../model/resource/read.component.spec';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { AbstractListViewComponent, ProvideListViewComponent, ListViewState } from './list-view.component';
import { AbstractListContentComponent } from './list-content.component';
import { GaeListComponentsModule } from './list.module';
import { ListViewSource, ListViewSourceState } from './source';
import { GaeListViewWrapperComponent } from './list-view-wrapper.component';
import { TestListViewSourceFactory } from './source.spec';
import { filter, flatMap } from 'rxjs/operators';

fdescribe('ListViewComponent', () => {

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
export class GaeTestModelListContentComponent extends AbstractListContentComponent<TestModel> {

  constructor(@Inject(forwardRef(() => GaeTestModelListComponent)) listView: GaeTestModelListComponent) {
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
  providers: [ProvideListViewComponent(GaeTestModelListComponent)]
})
export class GaeTestModelListComponent extends AbstractListViewComponent<TestModel> {

  @ViewChild(GaeTestModelListContentComponent)
  public component: GaeTestModelListContentComponent;

  @ViewChild(GaeListViewWrapperComponent)
  public wrapperComponent: GaeListViewWrapperComponent<TestModel>;

}

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
