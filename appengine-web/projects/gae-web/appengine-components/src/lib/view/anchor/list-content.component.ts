import { AbstractListContentComponent } from '../../list/list-content.component';
import { ClickableAnchor } from './anchor.component';
import { Observable, BehaviorSubject, combineLatest } from 'rxjs';
import { map, shareReplay } from 'rxjs/operators';
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
    anchorForElement() {
      return undefined;
    }
  };

  private readonly _delegate = new BehaviorSubject<AnchorListDelegate<T>>(AbstractDelegatedAnchorListContentComponent.DEFAULT_DELEGATE);

  /**
   * Uses combineLatest to make sure the anchorElements pipe updates properly.
   */
  private readonly _anchorElements: Observable<AnchorListElement<T>[]> = combineLatest([this.elements, this._delegate]).pipe(
    map(([elements, delegate]) => {
      return elements.map((element) => ({
        element,
        anchor: delegate.anchorForElement(element)
      }));
    }),
    shareReplay(1)
  );

  @Input()
  get anchorDelegate(): AnchorListDelegate<T> {
    return this._delegate.value;
  }

  set anchorDelegate(anchorDelegate: AnchorListDelegate<T>) {
    this._delegate.next(anchorDelegate || AbstractDelegatedAnchorListContentComponent.DEFAULT_DELEGATE);
  }

  // MARK: AbstractAnchorListContentComponent
  public get anchorElements(): Observable<AnchorListElement<T>[]> {
    return this._anchorElements;
  }

  // MARK: Unused
  protected anchorForElement(element: T): ClickableAnchor | undefined {
    return this.anchorDelegate.anchorForElement(element);
  }

}
