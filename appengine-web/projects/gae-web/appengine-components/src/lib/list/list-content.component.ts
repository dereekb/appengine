import { ListViewComponent } from './list-view.component';
import { Observable } from 'rxjs';

/**
 * Abstract list content that displays a list of items.
 *
 * TODO: These really aren't all that useful anymore and I think can be removed.
 *
 * All ListViews would render their own content.
 *
 * That said, I think these still can be useful as a step towards alternate content components.
 */
export abstract class AbstractListContentComponent<T> {

  constructor(protected readonly _listView: ListViewComponent<T>) { }

  protected get listView() {
    return this._listView;
  }

  public get elements(): Observable<T[]> {
    return this._listView.elements;
  }

  public get canLoadMore() {
    return this._listView.canLoadMore;
  }

  loadMore() {
    this._listView.loadMore();
  }

  select(selected: T) {
    this._listView.select(selected);
  }

}
