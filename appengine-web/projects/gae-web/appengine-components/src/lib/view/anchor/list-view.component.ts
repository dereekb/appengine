import { Input } from '@angular/core';
import { AbstractListViewComponent } from '../../list/list-view.component';
import { AnchorListDelegate } from './list-content.component';

/**
 * AbstractListViewComponent<T> extension for use with a child AbstractDelegatedAnchorListContentComponent<T>.
 */
export abstract class AbstractDelegatedAnchorListViewComponent<T> extends AbstractListViewComponent<T> {

  @Input()
  public anchorDelegate: AnchorListDelegate<T>;

}
