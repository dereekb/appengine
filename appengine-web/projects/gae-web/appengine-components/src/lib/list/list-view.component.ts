import { Component, Input, Output, OnDestroy, AfterContentInit, EventEmitter, Type } from '@angular/core';
import { Observable, BehaviorSubject, Subscription, of } from 'rxjs';
import { Source, SourceState, ControllableSource } from '@gae-web/appengine-utility';
import { UniqueModel } from '@gae-web/appengine-utility';
import { ReadSource, KeyQuerySource, MergedReadQuerySource } from '@gae-web/appengine-client';
import { map, flatMap, share, startWith, tap, catchError } from 'rxjs/operators';

export interface ListViewSourceEvent<T> {
  readonly state: ListViewSourceState;
  readonly elements: T[];
}

export interface ListViewSource<T> {

  readonly stream: Observable<ListViewSourceEvent<T>>;

  more(): void;

  refresh(): void;

}

// MARK: List Source
export enum ListViewSourceState {
  Init = -1,
  WaitingForSource = 0,
  Idle = 1,
  Loading = 2,
  Done = 3,
  Error = 4
}

// MARK: Source Conversion Source
export abstract class AbstractWrappedConversionListViewSource<I, O> implements ListViewSource<O> {

  public readonly stream: Observable<ListViewSourceEvent<O>>;

  constructor(protected readonly _inputSource: ListViewSource<I>) {
    this.stream = this.buildConversionStream(_inputSource);
  }

  more(): void {
    this._inputSource.more();
  }

  refresh(): void {
    this._inputSource.refresh();
  }

  abstract buildConversionStream(inputSource: ListViewSource<I>);

}

export class ConversionListViewSourceImpl<I, O> extends AbstractWrappedConversionListViewSource<I, O> {

  constructor(inputSource: ListViewSource<I>, private _convertFn: ((i: I[]) => O[])) {
    super(inputSource);
  }

  buildConversionStream(inputSource: ListViewSource<I>) {
    return inputSource.stream.pipe(
      map((x) => {
        return {
          elements: this._convertFn(x.elements),
          state: x.state
        };
      })
    );
  }

}

// MARK: Abstract Source
export abstract class AbstractListViewSource<T> implements OnDestroy {

  private _stream = new BehaviorSubject<ListViewSourceEvent<T>>({
    state: ListViewSourceState.Idle,
    elements: []
  });

  private _inputSource: Source<T>;
  private _sourceSub?: Subscription;

  constructor() { }

  public ngOnDestroy() {
    this._stream.complete();
    this.clearSourceSub();
  }

  // MARK: ListViewSource
  public get stream(): Observable<ListViewSourceEvent<T>> {
    return this._stream;
  }

  public abstract more(): void;

  public abstract refresh(): void;

  // MARK: Source
  protected setSource(source: Source<T> | undefined) {
    this.clearSourceSub();

    if (!source) {
      this.setWaitingForSource();
    } else if (source !== this._inputSource) {


      this._inputSource = source;
      this._sourceSub = source.stream.subscribe((next) => {
        let state: ListViewSourceState = ListViewSourceState.Idle;

        switch (next.state) {
          case SourceState.Reset:
          case SourceState.Idle:
            state = ListViewSourceState.Idle;
            break;
          case SourceState.Loading:
            state = ListViewSourceState.Loading;
            break;
          case SourceState.Done:
          case SourceState.Stopped:
            state = ListViewSourceState.Done;
            break;
          case SourceState.Error:
            state = ListViewSourceState.Error;
            break;
        }

        this.setStreamUpdate(state, next.elements);
      }, (error) => {
        this.setStreamError();
      });
    }
  }

  protected setWaitingForSource() {
    this._stream.next({
      state: ListViewSourceState.Idle,
      elements: []
    });
  }

  protected setStreamUpdate(state: ListViewSourceState, elements: T[]) {
    this._stream.next({
      state,
      elements
    });
  }

  protected setStreamError() {
    this._stream.next({
      state: ListViewSourceState.Error,
      elements: []
    });
  }

  protected clearSourceSub() {
    if (this._sourceSub) {
      this._sourceSub.unsubscribe();
      this._sourceSub = undefined;
    }
  }

}

/**
 * Implementation for a read source.
 */
@Component({
  selector: 'gae-list-view-read-source',
  template: ''
})
export class ListViewReadSourceComponent<T extends UniqueModel> extends AbstractListViewSource<T> implements ListViewSource<T> {

  private _source?: ReadSource<T>;

  @Input()
  public set readSource(source: ReadSource<T> | undefined) {
    this._source = source;
    super.setSource(this._source);
  }

  // MARK: ListViewSource
  public more(): void {
    // Do nothing.
  }

  public refresh(): void {
    // Do nothing.
  }

}

/**
 * Implementation for a query source.
 */
@Component({
  selector: 'gae-list-view-key-query-source',
  template: ''
})
export class ListViewKeyQuerySourceComponent<T extends UniqueModel> extends AbstractListViewSource<T> implements ListViewSource<T> {

  private _source: ControllableSource<T>;

  private _readSource?: ReadSource<T>;
  private _querySource?: KeyQuerySource<T>;

  @Input()
  public set readSource(source: ReadSource<T> | undefined) {
    this._readSource = source;
    this._update();
  }

  @Input()
  public set querySource(source: KeyQuerySource<T> | undefined) {
    this._querySource = source;
    this._update();
  }

  // MARK: Update
  private _update() {
    if (this._readSource && this._querySource) {
      this._source = new MergedReadQuerySource<T>(this._readSource, this._querySource);
      super.setSource(this._source);
    }
  }

  // MARK: ListViewSource
  public more(): void {
    if (this._source) {
      this._source.next();
    }
  }

  public refresh(): void {
    if (this._source) {
      this._source.reset();
    }
  }

}

// MARK: List View Component
export abstract class ListViewComponent<T> {

  hideLoadMoreControls: boolean;

  disallowLoadMore: boolean;

  hideControls: boolean;

  // MARK: Accessors
  abstract get state(): Observable<ListViewSourceState>;

  abstract get elementsObs(): Observable<Observable<T[]>>;

  abstract get elements(): Observable<T[]>;

  abstract get canLoadMore(): boolean;

  abstract get selected(): T;

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

  private _source: ListViewSource<T>;

  private _elements = new BehaviorSubject<Observable<T[]>>(of([]));
  private _state = new BehaviorSubject<ListViewSourceState>(ListViewSourceState.Init);

  private _elementsMapped = this._elements.pipe(flatMap((x) => x), share());

  private _selected?: T;

  @Input()
  public hideLoadMoreControls = false;

  @Input()
  public disallowLoadMore = false;

  @Input()
  public hideControls = false;

  @Output()
  public selectionEvent = new EventEmitter<T>();

  ngAfterContentInit() {
    this._initialize();
  }

  ngOnDestroy() {
    this._elements.complete();
    this._state.complete();
  }

  @Input()
  set source(source: ListViewSource<T>) {
    this._source = source;

    if (this._state.value !== ListViewSourceState.Init) {
      this._bindToSource();
    }
  }

  private _initialize(): void {
    if (this._state.value === ListViewSourceState.Init) {
      this._bindToSource();
    }
  }

  private _bindToSource(): void {
    if (this._source) {
      const nextElements = this._source.stream.pipe(
        catchError((error) => {
          this.setState(ListViewSourceState.Error);
          return Observable.throw(error);
        }),
        tap((event: ListViewSourceEvent<T>) => {
          this.setState(event.state);
        }),
        map((event) => event.elements || []),
        startWith([]),
        share() // Share updates so map/state changes occur only once due to children subs.
      );

      this.setState(ListViewSourceState.Loading);
      this.setElements(nextElements);
    } else {
      this.setState(ListViewSourceState.WaitingForSource);
      this.setElements(of([]));
    }
  }

  protected setElements(elements: Observable<T[]>) {
    this._elements.next(elements);
  }

  private setState(state: ListViewSourceState): void {
    this._state.next(state);
  }

  // MARK: Accessors
  get state(): Observable<ListViewSourceState> {
    return this._state;
  }

  get elementsObs(): Observable<Observable<T[]>> {
    return this._elements;
  }

  get elements(): Observable<T[]> {
    return this._elementsMapped;
  }

  get canLoadMore(): boolean {
    return !this.disallowLoadMore && this._state.value === ListViewSourceState.Idle;
  }

  get selected() {
    return this._selected;
  }

  // MARK: Component Functions
  public select(selected: T) {
    this.selectionEvent.emit(selected);
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
