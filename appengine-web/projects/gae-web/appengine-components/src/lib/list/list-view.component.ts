import { Component, Input, Output, OnDestroy, AfterContentInit, EventEmitter, Type, ChangeDetectorRef, Inject, Directive } from '@angular/core';
import { Observable, BehaviorSubject, Subscription, of } from 'rxjs';
import { map, mergeMap, share, startWith, tap, catchError, shareReplay } from 'rxjs/operators';
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
  abstract select(selected: T, event: MouseEvent | undefined): void;

}

export function ProvideListViewComponent<S extends ListViewComponent<any>>(listViewType: Type<S>) {
  return [{ provide: ListViewComponent, useExisting: listViewType }];
}

export interface ListViewItemClickedEvent<T> {
  selected: T;
  event: MouseEvent;
}

/**
 * Abstract list component that takes in a ListViewSource and and provides actions for interacting with that source.
 */
@Directive()
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

  private _elementsStream = this._stream.pipe(mergeMap((x) => {
    if (x.source) {
      return x.source.pipe(map(y => y.elements || []));
    } else {
      return [];
    }
  }), shareReplay(1));

  private _selected?: T;

  @Input()
  public hideLoadMoreControls = false;

  @Input()
  public disallowLoadMore = false;

  @Input()
  public hideControls = false;

  @Output()
  public itemSelected = new EventEmitter<T>();

  @Output()
  public itemClicked = new EventEmitter<ListViewItemClickedEvent<T>>();

  constructor(@Inject(ChangeDetectorRef) private cdRef: ChangeDetectorRef) { }

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
      const stream = this._source.stream;
      this.nextStreamEvent(ListViewState.SourceAvailable, stream);
    } else {
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
    return !this.disallowLoadMore && ((this._source) ? this._source.canLoadMore() : false);
  }

  get selected() {
    return this._selected;
  }

  // MARK: Component Functions
  public select(selected: T, event: MouseEvent | undefined) {
    this.itemSelected.emit(selected);

    if (event) {
      this.itemClicked.emit({
        selected,
        event
      });
    }

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
