import { ListViewComponent, ListViewState } from './list-view.component';
import { Observable } from 'rxjs';
import { Directive } from "@angular/core";

/**
 * Abstract list content that displays a list of items.
 *
 * TODO: These really aren't all that useful anymore and I think can be removed.
 *
 * All ListViews would render their own content.
 *
 * That said, I think these still can be useful as a step towards alternate content components.
 */
@Directive()
export abstract class AbstractListContentComponent<T> {

  constructor(protected readonly _listView: ListViewComponent<T>) { }

  protected get listView(): ListViewComponent<T> {
    return this._listView;
  }

  public get elements(): Observable<T[]> {
    return this._listView.elements;
  }

  public get canLoadMore(): boolean {
    return this._listView.canLoadMore;
  }

  public get state(): ListViewState {
    return this._listView.state;
  }

  loadMore() {
    this._listView.loadMore();
  }

  select(selected: T, event: MouseEvent | undefined) {
    this._listView.select(selected, event);
  }

}
