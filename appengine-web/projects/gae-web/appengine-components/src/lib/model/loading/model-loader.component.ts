import { Observable, BehaviorSubject, Subscription } from 'rxjs';
import { Component, ViewEncapsulation, AfterContentInit, OnDestroy, Input } from '@angular/core';
import {
    SingleElementReadSource, ConversionSourceEvent,
    SourceState, ModelKey, SubscriptionObject, CodedError, ErrorCode, ErrorMessage
} from '@gae-web/appengine-utility';
import { LoadingContext, LoadingEvent } from '../../loading/loading';
import { map } from 'rxjs/operators';
import { BaseError } from 'make-error';

export enum ModelLoaderState {

    /**
     * Loader is initialized.
     */
    Init = -1,

    /**
     * Model is loading.
     */
    Loading = 0,

    /**
     * The model finished loading.
     */
    Data = 1,

    /**
     * No error occured, but loading one or more models failed.
     */
    Failed = 2,

    /**
     * Error
     */
    Error = 3

}

export interface ModelLoaderEvent<T> {
    state: ModelLoaderState;
    model?: T;
    error?: any;
}

export abstract class ModelLoader<T> {
    /**
     * Whether or not this loader should have a success state without a model if the model fails to load.
     */
    optional: boolean;
    readonly state: ModelLoaderState;
    readonly model: T | undefined;
    readonly error: any;
    readonly stream: Observable<ModelLoaderEvent<T>>;
}

export abstract class AbstractModelLoaderStateComponent {

    abstract get state(): ModelLoaderState;

    get loading(): boolean {
        return this.state === ModelLoaderState.Loading;
    }

    get done(): boolean {
        return this.state === ModelLoaderState.Data;
    }

    get failed(): boolean {
        return this.state >= ModelLoaderState.Failed;
    }
}

/**
 * Component that reads a single element from a SingleElementReadSource.
 */
@Component({
    template: '',
    selector: 'gae-model-loader'
})
export class GaeModelLoaderComponent<T> extends AbstractModelLoaderStateComponent implements ModelLoader<T>, AfterContentInit, OnDestroy {

    private _modelSourceWrapper = new ModelLoaderSourceWrapper<T>();

    ngOnDestroy() {
        this._modelSourceWrapper.destroy();
    }

    ngAfterContentInit() {
        this._modelSourceWrapper.initialize();
    }

    // MARK: Accessors
    get state(): ModelLoaderState {
        return this._modelSourceWrapper.state;
    }

    get m() {
        return this.model;
    }

    get model(): T | undefined {
        return this._modelSourceWrapper.model;
    }

    get stream(): Observable<ModelLoaderEvent<T>> {
        return this._modelSourceWrapper.stream;
    }

    get error() {
        return this._modelSourceWrapper.error;
    }

    public get optional(): boolean {
        return this._modelSourceWrapper.optional;
    }

    @Input()
    public set optional(optional: boolean) {
        this._modelSourceWrapper.optional = optional;
    }

    // MARK: Source
    @Input()
    public set source(source: SingleElementReadSource<T>) {
        this._modelSourceWrapper.source = source;
    }

}

// MARK: Detail Component
export class ModelLoaderSourceWrapper<T> implements ModelLoader<T> {

    private _optional = false;   // TODO: Update to throw an error when all requested elements are not returned (atomic failure).

    private _stream = new BehaviorSubject<ModelLoaderEvent<T>>({
        state: ModelLoaderState.Init
    });

    private _source: SingleElementReadSource<T>;
    private _sub = new SubscriptionObject();

    constructor(source?: SingleElementReadSource<T>) {
        if (source) {
            this.source = source;
            this.initialize();
        }
    }

    destroy() {
        this._sub.destroy();
        this._stream.complete();
    }

    get stream(): Observable<ModelLoaderEvent<T>> {
        return this._stream.asObservable();
    }

    get state(): ModelLoaderState {
        return this._stream.value.state;
    }

    get error() {
        return this._stream.value.error;
    }

    get model(): T | undefined {
        return this._stream.value.model;
    }

    get modelAsync(): Observable<T | undefined> {
        return this._stream.pipe(map(x => x.model));
    }

    get optional() {
        return this._optional;
    }

    set optional(optional: boolean) {
        this._optional = optional;
    }

    /**
     * Model Source. Provided by the parent or resolved via the router.
     */
    set source(source: SingleElementReadSource<T>) {
        this._source = source;
        if (this.state !== ModelLoaderState.Init) {
            this._bindToSource();
        }
    }

    public initialize() {
        if (this.state === ModelLoaderState.Init) {
            this._bindToSource();
        }
    }

    private _bindToSource() {
        if (this._source) {
            this._stream.next({
                state: ModelLoaderState.Loading
            });

            this._sub.subscription = this._source.stream.subscribe({
                next: (event: ConversionSourceEvent<ModelKey, T>) => {
                    const model = event.elements[0];
                    const failed = event.failed;
                    let error = event.error;
                    const sourceState: SourceState = event.state;

                    if (model) {
                        this._stream.next({
                            state: ModelLoaderState.Data,
                            model
                        });
                    } else {
                        let newState: ModelLoaderState;

                        if (sourceState === SourceState.Error) {
                            this._stream.next({
                                state: ModelLoaderState.Error,
                                error
                            });
                        } else {
                            switch (sourceState) {
                                case SourceState.Done:
                                    if (failed.length > 0) {
                                        newState = ModelLoaderState.Failed;

                                        if (!this.optional) {
                                            error = new ModelLoaderFailedLoadingError();
                                        }
                                    } else {
                                        newState = ModelLoaderState.Data;
                                    }

                                    break;
                                default:
                                    newState = ModelLoaderState.Loading;
                                    break;
                            }

                            this._stream.next({
                                state: newState,
                                error
                            });
                        }
                    }
                },
                error: (error) => {
                    this._stream.next({
                        state: ModelLoaderState.Error,
                        error
                    });
                }
            });
        }
    }

    refresh() {
        if (this._source) {
            this._source.refresh();
        }
    }

}

// MARK: Context
export class ModelLoaderLoadingContext implements LoadingContext {

    constructor(private _loader: ModelLoader<any>) { }

    get isLoading() {
        return this._loader.state === ModelLoaderState.Loading;
    }

    get stream(): Observable<LoadingEvent> {
        return this._loader.stream.pipe(
            map((x: ModelLoaderEvent<any>) => {
                return {
                    isLoading: x.state === ModelLoaderState.Loading,
                    error: x.error
                };
            })
        );
    }

}

// MARK: Error
export class ModelLoaderFailedLoadingError extends BaseError implements CodedError {
    public code: ErrorCode = 'FAILED_LOADING';
    public message: ErrorMessage = 'The model failed to load.';
}
