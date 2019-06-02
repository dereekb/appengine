import { Inject, Component, ChangeDetectorRef, Input, ViewRef, AfterViewInit } from '@angular/core';
import { ListViewComponent } from './list-view.component';
import { AbstractSubscriptionComponent } from '../shared/subscription';

/**
 * Simple load-more list item.
 */
@Component({
  selector: 'gae-list-load-more',
  templateUrl: './load-more.component.html'
})
export class GaeListLoadMoreButtonComponent extends AbstractSubscriptionComponent implements AfterViewInit {

  @Input()
  public icon = 'chevron_right';

  @Input()
  public text = 'Load More';

  private _canLoadMore: boolean;

  constructor(@Inject(ListViewComponent) private readonly _listView: ListViewComponent<any>, private _cdRef: ChangeDetectorRef) {
    super();
  }

  ngAfterViewInit(): void {
    this.sub = this._listView.elements.subscribe(() => {
      this._refresh();
    });
  }

  get canLoadMore() {
    return this._canLoadMore;
  }

  loadMore() {
    this._listView.loadMore();
  }

  private _refresh() {
    const canLoadMore = this._listView.canLoadMore;
    if (this._canLoadMore !== canLoadMore) {
      this._canLoadMore = canLoadMore;

      if (!(this._cdRef as ViewRef).destroyed) {
        this._cdRef.detectChanges();
      }
    }
  }

}
