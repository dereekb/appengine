import { Component, OnDestroy, ViewEncapsulation } from '@angular/core';

import { ListViewComponent, ListViewSourceState } from './list-view.component';
import { Subscription } from 'rxjs';

/**
 * Abstract list content that displays a list of items.
 */
@Component({
  selector: 'gae-list-view-wrapper',
  templateUrl: './list-view-wrapper.component.html',
  encapsulation: ViewEncapsulation.None
})
export class ListViewWrapperComponent<T> implements OnDestroy {

  private _count: number;
  private _state: ListViewSourceState;

  private _showLoading = false;
  private _showDone = false;

  private _countSubs: Subscription[] = [];
  private _stateSub: Subscription;

  constructor(private _listView: ListViewComponent<T>) {
    this._countSubs[0] = this._listView.elementsObs.subscribe((obs) => {
      if (this._countSubs[1]) {
        this._countSubs[1].unsubscribe();
      }

      this._countSubs[1] = obs.subscribe((x) => {
        this._count = x.length;
      });
    });

    this._stateSub = this._listView.state.subscribe((state) => {
      let showDone = false;
      let showLoading = false;

      switch (state) {
        case ListViewSourceState.Init:
        case ListViewSourceState.WaitingForSource:
        case ListViewSourceState.Loading:
          showLoading = true;
          break;
        case ListViewSourceState.Done:
          showDone = true;
          break;
      }

      this._showDone = showDone;
      this._showLoading = showLoading;
    });
  }

  ngOnDestroy() {
    this._countSubs.forEach((x) => x.unsubscribe());
    this._stateSub.unsubscribe();

    delete this._countSubs;
    delete this._stateSub;
  }

  public get count() {
    return this._count;
  }

  public get showControls() {
    return !this._listView.hideControls;
  }

  public get showLoading() {
    return this._showLoading;
  }

  public get showDone() {
    return this._showDone;
  }

  public get hasElements() {
    return this._count !== 0;
  }

}
