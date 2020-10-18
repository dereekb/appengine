import { Component, OnDestroy, ViewEncapsulation, Inject, ChangeDetectorRef, ViewChild, AfterViewInit, ElementRef } from '@angular/core';

import { ListViewComponent } from './list-view.component';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { mergeMap } from 'rxjs/operators';
import { ListViewSourceState } from './source';
import { SimpleLoadingContext, LoadingContext } from '../loading/loading';
import { GaeViewUtility } from '../shared/utility';

/**
 * Wraps content within a ListViewComponent implementation.
 */
@Component({
  selector: 'gae-list-view-wrapper',
  templateUrl: './list-view-wrapper.component.html',
  encapsulation: ViewEncapsulation.None
})
export class GaeListViewWrapperComponent<T> implements OnDestroy, AfterViewInit {

  @ViewChild('customControls') customControlsContent: ElementRef;

  private _count: number;
  private _state: ListViewSourceState;

  private _context = new SimpleLoadingContext();
  private _sub = new SubscriptionObject();

  constructor(@Inject(ListViewComponent) private _listView: ListViewComponent<T>, private _cdRef: ChangeDetectorRef) {
    this._sub.subscription = _listView.stream.pipe(
      mergeMap(x => x.source)
    ).subscribe((event) => {
      let showLoading: boolean;

      switch (event.state) {
        case ListViewSourceState.Init:
        case ListViewSourceState.WaitingForSource:
        case ListViewSourceState.Loading:
          showLoading = true;
          break;
        case ListViewSourceState.Done:
          showLoading = false;
          break;
        case ListViewSourceState.Error:
          showLoading = false;
          break;
      }

      this._state = event.state;
      this._count = event.elements.length;
      this._context.setError(event.error, showLoading);
    });
  }

  ngAfterViewInit(): void {
    // Detect changes to hasControls.
    GaeViewUtility.safeDetectChanges(this._cdRef);
  }

  ngOnDestroy() {
    this._sub.destroy();
  }

  public get state() {
    return this._state;
  }

  public get count() {
    return this._count;
  }

  public get loadingContext(): LoadingContext {
    return this._context;
  }

  public get showControls() {
    return !this._listView.hideControls;
  }

  public get hasControls() {
    return GaeViewUtility.checkNgContentWrapperHasContent(this.customControlsContent);
  }

  public get hasElements() {
    return this._count !== 0;
  }

}
