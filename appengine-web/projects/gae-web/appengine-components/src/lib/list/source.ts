import { Observable, BehaviorSubject, Subscription } from 'rxjs';
import { OnDestroy, Directive } from '@angular/core';
import { Source, SourceState } from '@gae-web/appengine-utility';
import { map } from 'rxjs/operators';

export interface ListViewSourceEvent<T> {
  readonly state: ListViewSourceState;
  readonly elements: T[];
  readonly error?: any;
}

export abstract class ListViewSource<T> {
  readonly stream: Observable<ListViewSourceEvent<T>>;
  abstract canLoadMore(): boolean;
  abstract more(): void;
  abstract refresh(): void;
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

// MARK: Abstract Source
@Directive()
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

  public abstract canLoadMore(): boolean;

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

        this.setStreamUpdate(state, next.elements, next.error);
      }, (error) => {
        this.setStreamError(error);
      });
    }
  }

  protected setWaitingForSource() {
    this._stream.next({
      state: ListViewSourceState.Idle,
      elements: []
    });
  }

  protected setStreamUpdate(state: ListViewSourceState, elements: T[], error?: any) {
    this._stream.next({
      state,
      elements,
      error
    });
  }

  protected setStreamError(error) {
    this._stream.next({
      state: ListViewSourceState.Error,
      elements: [],
      error
    });
  }

  protected clearSourceSub() {
    if (this._sourceSub) {
      this._sourceSub.unsubscribe();
      this._sourceSub = undefined;
    }
  }

}

// MARK: Source Conversion Source
export abstract class AbstractWrappedConversionListViewSource<I, O> implements ListViewSource<O> {

  public readonly stream: Observable<ListViewSourceEvent<O>>;

  constructor(protected readonly _inputSource: ListViewSource<I>) {
    this.stream = this.buildConversionStream(_inputSource);
  }

  canLoadMore(): boolean {
    return this._inputSource.canLoadMore();
  }

  more(): void {
    this._inputSource.more();
  }

  refresh(): void {
    this._inputSource.refresh();
  }

  abstract buildConversionStream(inputSource: ListViewSource<I>);

}

export class ConversionListViewSource<I, O> extends AbstractWrappedConversionListViewSource<I, O> {

  constructor(inputSource: ListViewSource<I>, private _convertFn: ((i: I[]) => O[])) {
    super(inputSource);
  }

  buildConversionStream(inputSource: ListViewSource<I>) {
    return inputSource.stream.pipe(
      map((x) => {
        return {
          elements: this._convertFn(x.elements),
          state: x.state,
          error: x.error
        };
      })
    );
  }

}
