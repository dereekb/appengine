import { Type } from '@angular/core';
import { BaseError } from 'make-error';
import { Observable, Subscription, BehaviorSubject, of } from 'rxjs';
import { map, flatMap, debounceTime } from 'rxjs/operators';

export interface SingleElementSource<T> {

  /**
   * Observes the first item from elements.
   */
  readonly first: Observable<T | undefined>;

}

/**
 * ReadOnlySource that has a stream to the first item if it exists.
 */
export interface SingleElementReadSource<T> extends SingleElementSource<T>, ReadOnlySource<T> { }

// MARK: Conversion
/**
 * ReadOnlySource that has a stream to the first item if it exists.
 */
export interface SingleElementConversionSource<I, T> extends SingleElementSource<T>, ReadOnlyConversionSource<I, T> {

  /**
   * Observes the first item from elements.
   *
   * If there is only a single input element, and the corresponding model cannot be loaded,
   * a ConversionSourceFirstError will be thrown.
   */
  readonly first: Observable<T | undefined>;

}

export enum SourceState {

    Reset = 0,      // State was reset.

    Idle = 1,       // Not loading more elements. (can load more when requested.)

    Loading = 2,    // Loading more elements.

    Done = 3,        // Expect no more elements (no more will load when requested.)

    Error = 4,       // Encountered an error.

    Stopped = 5      // Source has been stopped permenantly.

}

// MARK: Source Classes
export class SourceEvent<T> {

    constructor(public readonly state: SourceState = SourceState.Reset, public readonly elements: T[] = []) { }

}

export abstract class SourceFactory<T> {

    abstract makeSource(): Source<T>;

}

export abstract class ReadOnlySource<T> {

    readonly state: SourceState;

    readonly stream: Observable<SourceEvent<T>>;

    /**
     * Refreshes the current data, if applicable for the source.
     */
    abstract refresh(): void;

}

/**
 * Source that provides additional pre-configured accessors.
 */
export abstract class Source<T> extends ReadOnlySource<T> {

    /**
     * Stops and destroys the source.
     */
    abstract destroy(): void;

    /**
     * Resets the source back to it's initial state,
     * or refreshes the input depending on the implementation.
     */
    abstract reset(): void;

}

export function ProvideSource<S extends Source<any>>(type: Type<S>) {
    return [{ provide: Source, useExisting: type }];
}

/**
 * Is not an iterator itself, so much as a control for the Source object to load more items.
 */
export abstract class ControllableSource<T> extends Source<T> {

    /**
     * Call to load more elements from the query if idle.
     */
    abstract hasNext(): boolean;

    /**
     * Call to load more elements from the query if idle.
     *
     * Additional next calls will be ignored until this completes.
     */
    abstract next(): Promise<{}>;

}

export function ProvideControllableSource<S extends Source<any>>(type: Type<S>) {
    return [...ProvideSource(type), { provide: ControllableSource, useExisting: type }];
}

export abstract class IterableSource<T> extends ControllableSource<T> {

    /**
     * Next returns the requested type.
     */
    abstract next(): Promise<T[]>;

}

export function ProvideIterableSource<S extends Source<any>>(type: Type<S>) {
    return [...ProvideControllableSource(type), { provide: IterableSource, useExisting: type }];
}

// MARK: Conversion Source Classes
export class ConversionSourceEvent<I, T> extends SourceEvent<T> {

    constructor(state?: SourceState, elements?: T[], public readonly failed: I[] = []) {
        super(state, elements);
    }

}

export abstract class ReadOnlyConversionSource<I, T> extends Source<T> {

    readonly stream: Observable<ConversionSourceEvent<I, T>>;

}

export abstract class ConversionSource<I, T> extends ReadOnlyConversionSource<I, T> {

    input: Observable<I | I[]> | undefined;

}

export function ProvideConversionSource<S extends ConversionSource<any, any>>(type: Type<S>) {
    return [...ProvideSource(type), { provide: ProvideConversionSource, useExisting: type }];
}

// MARK: Errors
export class ConversionSourceFirstError<I> extends BaseError {

    constructor(public readonly failed: I, message?: string) {
        super(message);
    }

}

/**
 * Thrown when there are so more elements in this source.
 */
export class EndOfSourceError extends BaseError {

    constructor(message?: string) {
        super(message);
    }

}

export abstract class AbstractCustomSource<T, E extends SourceEvent<T>> implements Source<T>, SingleElementReadSource<T> {

    protected readonly _id = Math.random() * 100000;

    protected readonly _stream: BehaviorSubject<E>;

    protected readonly _streamObs: Observable<E>;
    protected readonly _firstObs: Observable<T | undefined>;
    protected readonly _elementsObs: Observable<T[]>;

    constructor() {
        this._stream = this.makeStream();

        this._streamObs = this.makeStreamObs();
        this._firstObs = this.makeFirstObs();
        this._elementsObs = this.makeElementsObs();
    }

    // MARK: Init
    protected makeStream() {
        return new BehaviorSubject<E>({
            state: SourceState.Reset,
            elements: []
        } as E);
    }

    protected makeStreamObs() {
        return this._stream.asObservable();
    }

    protected makeFirstObs() {
        return this._streamObs.pipe(map((event) => event.elements[0]));
    }

    protected makeElementsObs() {
        return this._streamObs.pipe(map((event) => event.elements));
    }

    // MARK: Accessors
    get state(): SourceState {
        return this._stream.value.state;
    }

    get stream(): Observable<E> {
        return this._streamObs;
    }

    get first(): Observable<T | undefined> {
        return this._firstObs;
    }

    get elements(): Observable<T[]> {
        return this._elementsObs;
    }

    get currentEvent(): E {
        return this._stream.value;
    }

    protected getStream() {
        return this._streamObs;
    }

    protected getCurrentEvent() {
        return this._stream.value;
    }

    protected get currentElements(): T[] {
        return this._stream.value.elements;
    }

    protected get initialElements(): T[] {
        return [];
    }

    // MARK: Reset
    public refresh() {
        // Do nothing by default.
    }

    public reset() {
        this.setElements(this.initialElements, SourceState.Reset);
    }

    // MARK: Elements and State
    /**
     * Adds new elements to the front of the elements list.
     */
    protected prependElements(elements: T[], newState: SourceState) {
        const allElements = elements.concat(this.currentElements);
        this.setElements(allElements, newState);
    }

    protected addElements(elements: T[], newState: SourceState) {
        const allElements = this.currentElements.concat(elements);
        this.setElements(allElements, newState);
    }

    protected setElements(elements: T[], newState: SourceState) {
        this.updateStream(newState, elements);
    }

    // MARK: Stop
    public destroy() {
        this.stop();
        this._stream.complete();
    }

    protected stop() {
        if (!this.isStopped()) {
            this.setState(SourceState.Stopped);
        }
    }

    public isStopped() {
        return this.state === SourceState.Stopped;
    }

    // MARK: Internal
    /**
     * Sets a new state and updates the stream.
     */
    protected setState(state: SourceState) {
        this.updateStream(state);
    }

    // MARK: Stream
    protected updateStream(state: SourceState = this.state, elements: T[] = this.currentElements) {
        this.nextStreamUpdate({
            state,
            elements
        } as E);
    }

    protected nextStreamUpdate(event: E) {
        if (!this.isStopped()) {
            // console.log('Sending NEXT: ' + event + ' count: ' + event.elements.length + ' State: ' + SourceState[event.state]);
            this._stream.next(event);
        }
    }

}

export abstract class AbstractSource<T> extends AbstractCustomSource<T, SourceEvent<T>> { }

/**
 * Abstract implementation of ConversionSource that handles the input sub.
 */
export abstract class AbstractConversionSource<I, T> extends AbstractCustomSource<T, ConversionSourceEvent<I, T>>
    implements ConversionSource<I, T>, SingleElementConversionSource<I, T> {

    /**
     * New debounce values will not be used until the input changes.
     */
    protected debounce = 200;  // 200ms debounce

    // Used optionally by extending classes.
    private _sourceSub?: Subscription;

    private _input?: Observable<I | I[]>;
    private _inputSub?: Subscription;

    private _refreshData: I[];

    private _failedStream: Observable<I[]>;

    constructor() {
        super();
        this._failedStream = this.stream.pipe(map((event) => event.failed));
    }

    protected makeStream() {
        return new BehaviorSubject<ConversionSourceEvent<I, T>>({
            state: SourceState.Reset,
            elements: [],
            failed: []
        });
    }

    protected makeStreamObs() {
        return this._stream.asObservable();
    }

    protected makeFirstObs() {
        return this._streamObs.pipe(flatMap((event: ConversionSourceEvent<I, T>) => {
            const first = event.elements[0];

            if (!first) {
                const failed = event.failed[0];

                if (failed) {
                    return Observable.throw(new ConversionSourceFirstError(failed, 'Failed loading model.'));
                }
            }

            return of(first);
        }));
    }

    // MARK: Accessors
    public get failedStream(): Observable<I[]> {
        return this._failedStream;
    }

    public get currentFailed() {
        return this.currentEvent.failed;
    }

    // MARK: Input
    public get input() {
        return this._input;
    }

    public set input(input: Observable<I | I[]> | undefined) {
        this.setInput(input);
    }

    protected setInput(input: Observable<I | I[]> | undefined) {
        this._input = input;
        this.update(input);
    }

    protected update(input: Observable<I | I[]> | undefined) {
        this.clearAllInputSubs();

        if (input) {
            this._inputSub = this.subToInput(input);
        } else {
            this.updateWithNothing();
        }
    }

    // MARK: Internal
    protected subToInput(input: Observable<I | I[]>): Subscription {
        this.setElements([], SourceState.Loading, []);
        // debounce to prevent too many conversion requests too fast.
        return input.pipe(debounceTime(this.debounce)).subscribe((data) => {
            // console.log('Data: ' + data);

            if (Array.isArray(data)) {
                if (data.length === 0) {
                    this.updateWithNothing();
                    return;
                }
            } else {
                data = [data];
            }

            // this._refreshData = data;   // Set refresh Data???
            this.updateWithInput(data);
        }, (error) => {
            this.updateWithError();
        }, () => {
            this.setState(SourceState.Done);    // No more elements from this source.
        });
    }

    protected abstract updateWithInput(data: I[]);

    protected updateWithError(failed?: I[]) {
        this.setElements([], SourceState.Error, failed);
    }

    protected updateWithNothing() {
        this.setElements([], SourceState.Done, []);
    }

    // MARK: Elements and State
    protected prependElements(elements: T[], newState: SourceState, failed?: I[]) {
        const allElements = elements.concat(this.currentElements);
        this.setElements(allElements, newState, failed);
    }

    protected addElements(elements: T[], newState: SourceState, failed?: I[]) {
        const allElements = this.currentElements.concat(elements);
        this.setElements(allElements, newState, failed);
    }

    protected setConvertedElements(elements: T[], failed?: I[]) {
        this.setElements(elements, SourceState.Done, failed);
    }

    protected setElements(elements: T[], newState: SourceState, failed?: I[]) {
        this.updateStream(newState, elements, failed);
    }

    public refresh() {
        if (this._refreshData) {
            this.updateWithInput(this._refreshData);
        }
    }

    protected stop() {
        if (!this.isStopped()) {
            this.clearAllInputSubs();
            super.stop();
        }
    }

    public reset() {
        // Do nothing.
    }

    // MARK: Stream
    protected updateStream(state: SourceState = this.state, elements: T[] = this.currentElements, failed: I[] = this.currentFailed) {
        this.nextStreamUpdate({
            state,
            elements,
            failed
        } as any);
    }

    // MARK: Internal Input Sub
    protected clearAllInputSubs() {
        this.clearInputSub();
        this.clearSourceSub();
    }

    protected clearInputSub() {
        if (this._inputSub) {
            this._inputSub.unsubscribe();
            this._inputSub = undefined;
        }
    }

    // MARK: Internal Source Sub
    protected setSourceSub(sub: Subscription) {
        this.clearSourceSub();
        this._sourceSub = sub;
    }

    protected clearSourceSub() {
        if (this._sourceSub) {
            this._sourceSub.unsubscribe();
            this._sourceSub = undefined;
        }
    }

}

// MARK: Wrappers
/**
 * Abstract source implementation that delays requests until an internal source is set.
 */
export abstract class WrappedSource<T> implements Source<T> {

    private _destroyed = false;
    private _stream = new BehaviorSubject<SourceEvent<T>>({ state: SourceState.Reset, elements: [] });

    private _source: Source<T>;
    private _sourceSub: Subscription;

    private _getSource = () => SourceState.Reset;

    private _refreshSource = () => { };
    private _resetSource = () => { };
    private _destroySource = () => { };

    constructor() { }

    protected getSource(): Source<T> | undefined {
        return this._source;
    }

    protected setSource(source: Source<T>): void {
        if (this._source) {
            throw new Error('Source was already set.');
        }

        if (this._destroyed) {
            // Maybe just do nothing here instead...

            throw new Error('Wrapper Source was already marked destroyed.');
        }

        this._source = source;

        this._getSource = () => this._source.state;
        this._refreshSource = () => this._source.refresh();
        this._resetSource = () => this._source.reset();
        this._destroySource = () => this._source.destroy();

        this._sourceSub = this._source.stream.subscribe((x) => this._stream.next(x));
    }

    // MARK: Forwarded Accessors
    get state(): SourceState {
        return this._getSource();
    }

    get stream(): Observable<SourceEvent<T>> {
        return this._stream.asObservable();
    }

    public refresh() {
        this._refreshSource();
    }

    public reset() {
        this._refreshSource();
    }

    public destroy() {
        if (!this._destroyed) {
            this._destroySource();
            this.clearSourceSub();
            this._destroyed = true;
        }
    }

    private clearSourceSub() {
        if (this._sourceSub) {
            this._sourceSub.unsubscribe();
            this._sourceSub = undefined;
        }
    }

}

/**
 * AbstractWrappedSource extension for IterableSource.
 */
export class WrappedIterableSource<T> extends WrappedSource<T> implements IterableSource<T> {

    private _hasSource = false;

    private _stopWaiting: () => void;
    private _waiting: Promise<{}>;

    private _hasNext = () => true;

    constructor() {
        super();

        this._waiting = new Promise((stopWaiting) => {
            this._stopWaiting = stopWaiting;
        });
    }

    public hasSource(): boolean {
        return this._hasSource;
    }

    protected get source(): IterableSource<T> | undefined {
        return super.getSource() as IterableSource<T>;
    }

    public setSource(source: IterableSource<T>): void {
        super.setSource(source);

        this._hasSource = true;

        this._hasNext = () => source.hasNext();
        this._stopWaiting();
    }

    // MARK: Forwarded Accessor
    public hasNext(): boolean {
        return this._hasNext();
    }

    public next(): Promise<T[]> {
        return this._waiting.then(() => {
            if (this._hasSource) {
                return this.source.next();
            } else {
                return [];  // Return empty array for clearing...
            }
        });
    }

    public destroy() {
        super.destroy();
        this._stopWaiting();

        delete this._waiting;
        delete this._hasNext;
    }

}
