import { Component, OnDestroy, ViewEncapsulation, Inject, ChangeDetectorRef, ViewChild, AfterViewInit } from '@angular/core';

import { ListViewComponent } from './list-view.component';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { flatMap } from 'rxjs/operators';
import { ListViewSourceState } from './source';
import { SimpleLoadingContext, LoadingContext } from '../loading/loading';

/**
 * Wraps content within a ListViewComponent implementation.
 */
@Component({
  selector: 'gae-list-view-wrapper',
  templateUrl: './list-view-wrapper.component.html',
  encapsulation: ViewEncapsulation.None
})
export class GaeListViewWrapperComponent<T> implements AfterViewInit, OnDestroy {

  private _count: number;
  private _state: ListViewSourceState;

  private _context = new SimpleLoadingContext();
  private _sub = new SubscriptionObject();

  @ViewChild('toolbar', {static: true}) customToolbarContent;
  @ViewChild('empty', {static: true}) customEmptyContent;

  private _hasCustomToolbar;
  private _hasCustomEmpty;

  constructor(@Inject(ListViewComponent) private _listView: ListViewComponent<T>, private cdRef: ChangeDetectorRef) {
    this._sub.subscription = _listView.stream.pipe(
      flatMap(x => x.source)
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

  ngAfterViewInit() {
    this._hasCustomToolbar = Boolean(this.customToolbarContent);
    this._hasCustomEmpty = Boolean(this.customEmptyContent);
    this.cdRef.detectChanges();
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

  public get hasElements() {
    return this._count !== 0;
  }

  public get hasCustomToolbar() {
    return this._hasCustomToolbar;
  }

  public get hasCustomEmpty() {
    return this._hasCustomEmpty;
  }

}
