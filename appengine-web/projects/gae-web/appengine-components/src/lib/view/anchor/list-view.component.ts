import { AbstractListContentComponent } from '../../list/list-content.component';
import { ClickableAnchor } from './anchor.component';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Input } from '@angular/core';

export interface AnchorListElement<T> {
  element: T;
  anchor?: ClickableAnchor;
}

/**
 * AbstractListContentComponent extension that configures functions for building elements that are represented by anchors.
 */
export abstract class AbstractAnchorListContentComponent<T> extends AbstractListContentComponent<T> {

  public get anchorElements(): Observable<AnchorListElement<T>[]> {
    return this.elements.pipe(
      map((elements: T[]) => {
        return elements.map((element) => ({
          element,
          anchor: this.anchorForElement(element)
        }));
      })
    );
  }

  protected abstract anchorForElement(element: T): ClickableAnchor | undefined;

}

// MARK: AbstractDelegatedAnchorListContentComponent
export interface AnchorListDelegate<T> {
  anchorForElement(element: T): ClickableAnchor | undefined;
}

/**
 * AbstractAnchorListContentComponent<T> extension that provides anchors for elements using a delegate.
 */
export abstract class AbstractDelegatedAnchorListContentComponent<T> extends AbstractAnchorListContentComponent<T> {

  private static readonly DEFAULT_DELEGATE: AnchorListDelegate<any> = {
    anchorForElement: () => undefined
  };

  private _delegate: AnchorListDelegate<T> = AbstractDelegatedAnchorListContentComponent.DEFAULT_DELEGATE;

  @Input()
  get anchorDelegate(): AnchorListDelegate<T> {
    return this._delegate;
  }

  set anchorDelegate(anchorDelegate: AnchorListDelegate<T>) {
    this._delegate = anchorDelegate || AbstractDelegatedAnchorListContentComponent.DEFAULT_DELEGATE;
  }

  // MARK: AbstractAnchorListContentComponent
  protected anchorForElement(element: T): ClickableAnchor | undefined {
    return this._delegate.anchorForElement(element);
  }

}
