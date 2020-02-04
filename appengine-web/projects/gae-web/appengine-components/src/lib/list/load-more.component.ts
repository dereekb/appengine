import { Inject, Component, ChangeDetectorRef, Input, ViewRef, AfterViewInit, ViewChild, ElementRef, OnChanges } from '@angular/core';
import { ListViewComponent } from './list-view.component';
import { AbstractSubscriptionComponent } from '../shared/subscription';
import { GaeViewUtility } from '../shared/utility';

/**
 * Simple load-more list item.
 */
@Component({
  selector: 'gae-list-load-more',
  templateUrl: './load-more.component.html'
})
export class GaeListLoadMoreComponent extends AbstractSubscriptionComponent implements AfterViewInit {

  @Input()
  public icon = 'chevron_right';

  @Input()
  public text = 'Load More';

  @ViewChild('customContent', { static: false }) customContent: ElementRef;

  private _canLoadMore: boolean;

  constructor(@Inject(ListViewComponent) private readonly _listView: ListViewComponent<any>, private _cdRef: ChangeDetectorRef) {
    super();
  }

  ngAfterViewInit(): void {
    this.sub = this._listView.elements.subscribe(() => {
      this._refresh();
    });

    GaeViewUtility.safeDetectChanges(this._cdRef);
  }

  public get hasCustomContent() {
    return GaeViewUtility.checkNgContentWrapperHasContent(this.customContent);
  }

  public get canLoadMore() {
    return this._canLoadMore;
  }

  public loadMore() {
    this._listView.loadMore();
  }

  private _refresh() {
    const canLoadMore = this._listView.canLoadMore;
    if (this._canLoadMore !== canLoadMore) {
      this._canLoadMore = canLoadMore;

      GaeViewUtility.safeDetectChanges(this._cdRef);
    }
  }

}
