import { Directive, Inject, Input, Output, OnDestroy, SkipSelf, AfterViewInit, Type, forwardRef } from '@angular/core';

import { BaseError } from 'make-error';
import { Source, ReadOnlySource, SourceState, ConversionSource, ConversionSourceEvent, ControllableSource, IterableSource, SourceEvent } from '@gae-web/appengine-utility';
import { Observable, of, throwError } from 'rxjs';
import { map, flatMap, catchError, share, shareReplay, first } from 'rxjs/operators';

// Declarations
export abstract class ReadOnlySourceComponent<T> extends ReadOnlySource<T> { }

export abstract class SourceComponent<T> extends ReadOnlySourceComponent<T> implements Source<T> {

  abstract destroy();

  abstract reset();

}

export function ProvideSourceComponent<S extends SourceComponent<any>>(sourceType: Type<S>) {
  return [{ provide: SourceComponent, useExisting: sourceType }];
}

// MARK: Source Component
/**
 * Abstract component that provides elements.
 *
 * Uses a Source.
 */
export abstract class AbstractSourceComponent<T> implements SourceComponent<T>, OnDestroy {

  private readonly _elements: Observable<T[]>;
  private readonly _first: Observable<T | undefined>;

  constructor(protected readonly _source: Source<T>) {
    this._elements = this.makeElementsObs();
    this._first = this.makeFirstObs();
  }

  protected get source() {
    return this._source;
  }

  protected makeFirstObs() {
    return this._source.stream.pipe(map((event) => event.elements[0]));
  }

  protected makeElementsObs() {
    return this._source.stream.pipe(map(((event) => event.elements)));
  }

  // MARK: Angular
  public ngOnDestroy() {
    this._source.destroy();
  }

  get stream() {
    return this._source.stream;
  }

  get first(): Observable<T | undefined> {
    return this._first;
  }

  get elements(): Observable<T[]> {
    return this._elements;
  }

  /** Deprecated. Use elements instead. */
  get values(): Observable<T[]> {
    return this._elements;
  }

  get isIdle() {
    return this.state === SourceState.Idle;
  }

  get isLoading() {
    return this.state === SourceState.Loading;
  }

  get hasError() {
    return this.state === SourceState.Error;
  }

  get state() {
    return this._source.state;
  }

  // MARK Source
  public destroy(): void {
    // Ignored. Only destroy on ngOnDestroy()
  }

  public reset(): void {
    this.source.reset();
  }

  public refresh(): void {
    this.source.refresh();
  }

}

export abstract class AbstractConversionSourceComponent<I, T> extends AbstractSourceComponent<T> {

  constructor(source: ConversionSource<I, T>) {
    super(source);
  }

  protected get source() {
    return this._source as ConversionSource<I, T>;
  }

  protected setSourceInput(input: Observable<I | I[]> | undefined) {
    this.source.input = input;
  }

  get stream() {
    return this._source.stream as Observable<ConversionSourceEvent<I, T>>;
  }

}

// MARK: ControllableSourceComponent
export abstract class ControllableSourceComponent<T> extends SourceComponent<T> implements ControllableSource<T> {

  abstract hasNext(): boolean;

  abstract next(): Promise<{}>;

}

export function ProvideControllableSourceComponent<S extends ControllableSourceComponent<any>>(sourceType: Type<S>) {
  return [...ProvideSourceComponent(sourceType), { provide: ControllableSourceComponent, useExisting: sourceType }];
}

/**
 * Basic component used to wrap a KeyQuerySource or similar iterable source.
 *
 * Automatically calls next when initialized.
 *
 * Use AbstractConfigurableKeyQuerySourceComponent for queries that must be configured.
 */
export abstract class AbstractControllableSourceComponent<T>
  extends AbstractSourceComponent<T> implements ControllableSourceComponent<T>, AfterViewInit {

  constructor(source: ControllableSource<T>, private _callNextOnReset = true) {
    super(source);
  }

  protected get source(): ControllableSource<T> {
    return this._source as ControllableSource<T>;
  }

  public ngAfterViewInit() {
    if (this._callNextOnReset) {
      this.next();
    }
  }

  public reset(callNext: boolean = this._callNextOnReset) {
    this.source.reset();

    if (callNext) {
      this.next();
    }
  }

  // MARK: Controllable Source
  public hasNext(): boolean {
    return this.source.hasNext();
  }

  public next(): Promise<any> {
    return this.source.next().catch(() => undefined);
  }

}

// MARK: IterableSourceComponent
export abstract class IterableSourceComponent<T> extends ControllableSourceComponent<T> implements IterableSource<T> {

  abstract initial(): Promise<T[]>;

  abstract next(): Promise<T[]>;

}

export function ProvideIterableSourceComponent<S extends IterableSourceComponent<any>>(sourceType: Type<S>) {
  return [...ProvideControllableSourceComponent(sourceType), { provide: IterableSourceComponent, useExisting: sourceType }];
}

/**
 * AbstractControllableSourceComponent implementation.
 */
export abstract class AbstractIterableSourceComponent<T> extends AbstractControllableSourceComponent<T> implements IterableSourceComponent<T> {

  constructor(source: IterableSource<T>, callNextOnReset?: boolean) {
    super(source, callNextOnReset);
  }

  protected get source(): IterableSource<T> {
    return this._source as IterableSource<T>;
  }

  public initial(): Promise<T[]> {
    return this.source.initial().catch(() => undefined);
  }

  public next(): Promise<T[]> {
    return super.next();
  }

}

// MARK: Support Components
export type UnknownSourceComponent<T> = SourceComponent<T> | ControllableSourceComponent<T> | IterableSourceComponent<T>;

export class AbstractSourceComponentWrapperSourceUsageException extends BaseError {

  constructor(message: string = 'Incompatable type. Cannot use next() with the wrapped component.') {
    super(message);
  }

}

// MARK: Wrapper
/**
 * SourceComponent implementation that wraps another SourceComponent.
 */
export abstract class AbstractSourceComponentWrapper<T> extends IterableSourceComponent<T> {

  private _hasNext: () => boolean;
  private _next: () => Promise<any>;
  private _initial: () => Promise<any>;

  constructor(protected readonly _source: UnknownSourceComponent<T>) {
    super();

    if ((_source as any).next) {
      this._hasNext = () => {
        return (this._source as ControllableSourceComponent<T>).hasNext();
      };
      this._next = () => {
        return (this._source as ControllableSourceComponent<T>).next();
      };
      this._initial = () => {
        if (this.state === SourceState.Reset) {
          return this.next();
        } else {
          return this.elements.pipe(first()).toPromise();
        }
      };
    } else {
      this._hasNext = () => {
        throw new AbstractSourceComponentWrapperSourceUsageException();
      };
      this._next = () => {
        return Promise.reject(new AbstractSourceComponentWrapperSourceUsageException());
      };
      this._initial = () => {
        return Promise.reject(new AbstractSourceComponentWrapperSourceUsageException());
      };
    }
  }

  protected get source() {
    return this._source;
  }

  // MARK: Source
  get state() {
    return this._source.state;
  }

  get stream() {
    return this._source.stream;
  }

  refresh() {
    return this._source.refresh();
  }

  destroy() {
    this._source.destroy();
  }

  reset() {
    this._source.reset();
  }

  // MARK: Controllable Source
  public hasNext(): boolean {
    return this._hasNext();
  }

  public initial(): Promise<any> {
    return this._initial();
  }

  public next(): Promise<any> {
    return this._next();
  }

}

// MARK: Transform
/**
 * Wraps one source and performs a transformation on the stream.
 */
export abstract class AbstractTransformationSourceComponent<I, O> extends AbstractSourceComponentWrapper<O> {

  private readonly _conversionObs: Observable<SourceEvent<O>>;

  constructor(source: UnknownSourceComponent<I>) {
    super(source as any);
    this._conversionObs = this._makeConversionObservable(source);
  }

  get stream(): Observable<SourceEvent<O>> {
    return this._conversionObs;
  }

  protected _makeConversionObservable(source: SourceComponent<I>): Observable<SourceEvent<O>> {
    return source.stream.pipe(
      flatMap((event: SourceEvent<I>) => {
        return this.transformEvent(event);
      })
    );
  }

  protected transformEvent(event: SourceEvent<I>): Observable<SourceEvent<O>> {
    return this.transformElements(event.elements, event).pipe(
      map((transformed) => {
        return {
          ...event,
          elements: transformed
        };
      })
    );
  }

  protected abstract transformElements(elements: I[], event: any): Observable<O[]>;

}

// MARK: Transformation Source Directive
/**
 * Base TransformationSourceComponent that has a delegate function.
 */
export type TransformationSourceTransformElementFn<I, O> = (elements: I[], event: any) => Observable<O[]>;
export type TransformationSourceTransformEventFn<I, O> = (event: SourceEvent<I>) => Observable<SourceEvent<O>>;

@Directive({
  selector: '[gaeTransformationSource]',
  exportAs: 'gaeTransformationSource'
})
export class GaeTransformationSourceDirective<I, O> extends AbstractTransformationSourceComponent<I, O> implements OnDestroy {

  private _transformElementsFn: TransformationSourceTransformElementFn<I, O>;
  private _transformEventFn: TransformationSourceTransformEventFn<I, O>;

  constructor(@Inject(SourceComponent) source: SourceComponent<I>) {
    super(source);
  }

  ngOnDestroy() {
    delete this._transformElementsFn;
    delete this._transformEventFn;

    this._transformEventFn = () => {
      throw new Error('Source Transformation has ended.');
    };
  }

  @Input()
  public set transformEventFn(fn: TransformationSourceTransformEventFn<I, O> | undefined) {
    if (fn) {
      this._transformEventFn = fn;
    } else {
      this._transformEventFn = this.transformEvent;
    }

    this._updateForTransformFn();
  }

  @Input()
  public set transformElementsFn(fn: TransformationSourceTransformElementFn<I, O> | undefined) {
    if (fn) {
      this._transformElementsFn = fn;
    } else {
      this._transformElementsFn = this.transformElements;
    }

    this._transformEventFn = this.transformEvent;
    this._updateForTransformFn();
  }

  protected _updateForTransformFn() {
    switch (this.state) {
      case SourceState.Reset:
      case SourceState.Stopped:
        // Do nothing
        break;
      default:
        this.reset(); // Reset
        break;
    }
  }

  protected transformEvent(event: SourceEvent<I>): Observable<SourceEvent<O>> {
    return this._transformEventFn(event);
  }

  protected transformElements(elements: I[], event: any): Observable<O[]> {
    return this._transformElementsFn(elements, event);
  }

  protected _makeConversionObservable(source: SourceComponent<I>): Observable<SourceEvent<O>> {
    return source.stream.pipe(
      flatMap((event: SourceEvent<I>) => {
        return this.transformElements(event.elements, event).pipe(
          map((transformed) => {
            return {
              ...event,
              elements: transformed
            };
          })
        );
      }),
      catchError((x) => {
        console.error('Transformation error: ' + x);
        return throwError(x);
      }),
      shareReplay(1)
    );
  }

}

// MARK: Delegates
/**
 * Delegate for a GaeTransformationSourceDirective that requires the input source.
 */
export abstract class AnstractSourceDependentTransformationSourceDelegate<S, I, O> {

  constructor(private _transformationSource: GaeTransformationSourceDirective<I, O>) { }

  protected setDependentSource(source: Source<S>) {
    if (source) {
      this._transformationSource.transformElementsFn = (elements: I[]) => {
        if (elements.length) {
          return source.stream.pipe(
            map((dependentEvent) => {
              return this.filterElementsWithDependentSourceEvent(elements, dependentEvent);
            }),
            catchError((x) => {
              // console.error('Filter error: ' + x);
              return throwError(x);
            })
          );
        } else {
          // No elements to filter.
          return of([]);
        }
      };
    } else {
      this._transformationSource.transformElementsFn = undefined;
    }
  }

  protected abstract filterElementsWithDependentSourceEvent(elements: I[], dependentEvent: SourceEvent<S>);

}

/**
 * Simplified declaration of AnstractSourceDependentTransformationSourceDelegate that only has two type arguments.
 */
export abstract class AnstractSourceDependentTransformationFilter<S, T> extends AnstractSourceDependentTransformationSourceDelegate<S, T, T> { }
