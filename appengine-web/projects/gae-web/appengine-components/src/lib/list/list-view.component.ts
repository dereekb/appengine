import { Component, Input, Output, OnDestroy, AfterContentInit, EventEmitter, Type, ChangeDetectorRef, Inject } from '@angular/core';
import { Observable, BehaviorSubject, Subscription, of } from 'rxjs';
import { Source, SourceState, ControllableSource, SubscriptionObject } from '@gae-web/appengine-utility';
import { UniqueModel } from '@gae-web/appengine-utility';
import { map, flatMap, share, startWith, tap, catchError } from 'rxjs/operators';
import { ListViewSourceEvent, ListViewSource, ListViewSourceState } from './source';

// MARK: List View Component
export enum ListViewState {
  Init = -1,
  WaitingForSource = 0,
  SourceAvailable = 1
}

export interface ListViewStreamEvent<T> {
  readonly state: ListViewState;
  readonly source: Observable<ListViewSourceEvent<T>>;
}

export abstract class ListViewComponent<T> {

  hideLoadMoreControls: boolean;
  disallowLoadMore: boolean;
  hideControls: boolean;

  // MARK: Accessors
  abstract get stream(): Observable<ListViewStreamEvent<T>>;

  abstract get state(): ListViewState;
  abstract get elements(): Observable<T[]>;

  abstract get canLoadMore(): boolean;
  abstract get selected(): T;

  source: ListViewSource<T>;

  // MARK: Functions
  abstract loadMore(): void;
  abstract select(selected: T): void;

}

export function ProvideListViewComponent<S extends ListViewComponent<any>>(listViewType: Type<S>) {
  return [{ provide: ListViewComponent, useExisting: listViewType }];
}

/**
 * Abstract list component that takes in a ListViewSource and and provides actions for interacting with that source.
 */
export abstract class AbstractListViewComponent<T> implements ListViewComponent<T>, OnDestroy, AfterContentInit {

  private static readonly INITIAL_SOURCE = of({
    state: ListViewSourceState.Init,
    elements: []
  });

  private _source: ListViewSource<T>;

  private _stream = new BehaviorSubject<ListViewStreamEvent<T>>({
    state: ListViewState.Init,
    source: AbstractListViewComponent.INITIAL_SOURCE
  });

  private _elementsStream = this._stream.pipe(flatMap((x) => {
    if (x.source) {
      return x.source.pipe(map(y => y.elements || []));
    } else {
      return [];
    }
  }), share());

  private _lastSourceState = ListViewSourceState.Init;
  private _selected?: T;

  @Input()
  public hideLoadMoreControls = false;

  @Input()
  public disallowLoadMore = false;

  @Input()
  public hideControls = false;

  @Output()
  public itemSelected = new EventEmitter<T>();

  constructor(@Inject(ChangeDetectorRef) private cdRef: ChangeDetectorRef) {}

  ngAfterContentInit() {
    this._initialize();
  }

  ngOnDestroy() {
    this._stream.complete();
  }

  get source() {
    return this._source;
  }

  @Input()
  set source(source: ListViewSource<T>) {
    this._source = source;

    if (this.state !== ListViewState.Init) {
      this._bindToSource();
    }
  }

  private _initialize(): void {
    if (this.state === ListViewState.Init) {
      this._bindToSource();
    }
  }

  private _bindToSource(): void {
    if (this._source) {
      const stream = this._source.stream.pipe(
        tap((x) => this._lastSourceState = x.state)
      );

      this.nextStreamEvent(ListViewState.SourceAvailable, stream);
    } else {
      this._lastSourceState = ListViewSourceState.Init;
      this.nextStreamEvent(ListViewState.WaitingForSource);
    }
  }

  protected nextStreamEvent(state: ListViewState, source: Observable<ListViewSourceEvent<T>> = AbstractListViewComponent.INITIAL_SOURCE) {
    this._stream.next({
      state,
      source
    });
    this.cdRef.detectChanges();
  }

  // MARK: Accessors
  get currentStreamEvent() {
    return this._stream.value;
  }

  get stream() {
    return this._stream.asObservable();
  }

  get state(): ListViewState {
    return this.currentStreamEvent.state;
  }

  get elements(): Observable<T[]> {
    return this._elementsStream;
  }

  get canLoadMore(): boolean {
    return !this.disallowLoadMore && this._lastSourceState === ListViewSourceState.Idle;
  }

  get selected() {
    return this._selected;
  }

  // MARK: Component Functions
  public select(selected: T) {
    this.itemSelected.emit(selected);
    this._selected = selected;
  }

  public loadMore() {
    if (!this.disallowLoadMore) {
      this._source.more();
    }
  }

  public refresh() {
    this._source.refresh();
  }

}
