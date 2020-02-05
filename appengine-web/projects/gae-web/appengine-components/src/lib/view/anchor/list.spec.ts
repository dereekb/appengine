import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Component, ViewChild, Input, Inject, forwardRef, ChangeDetectorRef } from '@angular/core';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { filter, flatMap, tap } from 'rxjs/operators';
import { TestFoo } from '@gae-web/appengine-api';
import { By } from '@angular/platform-browser';
import { ListViewSource } from '../../list/source';
import { GaeListViewWrapperComponent } from '../../list/list-view-wrapper.component';
import { AnchorListDelegate, AbstractAnchorListContentComponent, AbstractDelegatedAnchorListContentComponent } from './list-content.component';
import { ProvideListViewComponent } from '../../list/list-view.component';
import { AbstractDelegatedAnchorListViewComponent } from './list-view.component';
import { MatListModule, MatIconModule } from '@angular/material';
import { GaeListLoadMoreComponent } from '../../list/load-more.component';
import { GaeAnchorModule } from './anchor.module';
import { GaeListComponentsModule } from '../../list/list.module';
import { TestListViewSourceFactory } from '../../list/source.spec';

describe('GaeAnchorList components', () => {

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        MatIconModule,
        MatListModule,
        GaeAnchorModule,
        GaeListComponentsModule,
        NoopAnimationsModule
      ],
      declarations: [GaeTestFooAnchorListComponent, GaeTestFooAnchorListContentComponent, TestViewComponent]
    }).compileComponents();
  }));

  let component: GaeTestFooAnchorListComponent;
  let testComponent: TestViewComponent;
  let fixture: ComponentFixture<TestViewComponent>;

  beforeEach(async(() => {
    fixture = TestBed.createComponent(TestViewComponent);
    testComponent = fixture.componentInstance;
    component = testComponent.component;
    fixture.detectChanges();
  }));

  describe('with elements', () => {

    const modelKeys = [1, 2, 3];

    beforeEach(async(() => {
      testComponent.source = TestListViewSourceFactory.makeSource(modelKeys);
      fixture.detectChanges();
    }));

    it('should be displayed.', () => {

    });

  });

});

@Component({
  selector: 'gae-test-model-anchor-list-content',
  template: `
    <mat-nav-list class="gae-list-view-list">
      <gae-list-anchor [element]="anchor" *ngFor="let anchor of anchorElements | async">
        <mat-list-item>
          <p class="gae-list-item-detail" matLine>{{ anchor.element?.name }}</p>
          <span>
            <mat-icon>chevron_right</mat-icon>
          </span>
        </mat-list-item>
      </gae-list-anchor>
      <gae-list-load-more></gae-list-load-more>
    </mat-nav-list>
  `
})
export class GaeTestFooAnchorListContentComponent extends AbstractDelegatedAnchorListContentComponent<TestFoo> {

  @ViewChild(GaeListLoadMoreComponent, { static: true })
  public loadMoreButtonComponent: GaeListLoadMoreComponent;

  constructor(@Inject(forwardRef(() => GaeTestFooAnchorListComponent)) listView: any, cdRef: ChangeDetectorRef) {
    super(listView, cdRef);
  }

}

@Component({
  selector: 'gae-test-model-anchor-list',
  template: `
  <gae-list-view-wrapper>
    <ng-content controls select="[controls]"></ng-content>
    <gae-test-model-anchor-list-content list [anchorDelegate]="anchorDelegate"></gae-test-model-anchor-list-content>
    <ng-content empty select="[empty]"></ng-content>
  </gae-list-view-wrapper>
  `,
  providers: [ProvideListViewComponent(GaeTestFooAnchorListComponent)]
})
export class GaeTestFooAnchorListComponent extends AbstractDelegatedAnchorListViewComponent<TestFoo> {

  @ViewChild(GaeTestFooAnchorListContentComponent, { static: true })
  public contentComponent: GaeTestFooAnchorListContentComponent;

  @ViewChild(GaeListViewWrapperComponent, { static: true })
  public wrapperComponent: GaeListViewWrapperComponent<TestFoo>;

}

@Component({
  template: `
    <gae-test-model-anchor-list [source]="source" [anchorDelegate]="anchorDelegate"></gae-test-model-anchor-list>
  `
})
class TestViewComponent {

  @ViewChild(GaeTestFooAnchorListComponent, { static: true })
  public component?: GaeTestFooAnchorListComponent;

  public source: ListViewSource<TestFoo>;

  @Input()
  public anchorDelegate: AnchorListDelegate<TestFoo>;

}
