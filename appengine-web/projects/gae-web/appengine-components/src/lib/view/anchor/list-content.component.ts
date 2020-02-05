import { AbstractListContentComponent } from '../../list/list-content.component';
import { ClickableAnchor } from './anchor.component';
import { Observable, BehaviorSubject, combineLatest } from 'rxjs';
import { map, shareReplay, tap } from 'rxjs/operators';
import { Input, ChangeDetectorRef } from '@angular/core';
import { ListViewComponent } from '../../list/list-view.component';
import { GaeViewUtility } from '../../shared/utility';

export interface AnchorListElement<T> {
  element: T;
  anchor?: ClickableAnchor;
}

/**
 * AbstractListContentComponent extension that configures functions for building elements that are represented by anchors.
 */
export abstract class AbstractAnchorListContentComponent<T> extends AbstractListContentComponent<T> {

  public abstract get anchorElements(): Observable<AnchorListElement<T>[]>;

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
    tap(() => GaeViewUtility.safeDetectChanges(this._cdRef)),
    shareReplay(1)
  );

  constructor(listView: ListViewComponent<T>, private readonly _cdRef: ChangeDetectorRef) {
    super(listView);
  }

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

}
