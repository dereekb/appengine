import { Observable, BehaviorSubject, Subscription } from 'rxjs';
import { Component, ViewEncapsulation, AfterContentInit, OnDestroy, Input } from '@angular/core';
import {
    SingleElementReadSource, ConversionSourceEvent,
    SourceState, ModelKey, SubscriptionObject
} from '@gae-web/appengine-utility';
import { LoadingContext, LoadingEvent } from '../../loading/loading';
import { map } from 'rxjs/operators';

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
     * No error occured, but loading the model failed.
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
    readonly state: ModelLoaderState;
    readonly model: T | undefined;
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

    private _optional = true;   // TODO: Update to throw an error when all requested elements are not returned (atomic failure).

    private _stream = new BehaviorSubject<ModelLoaderEvent<T>>({
        state: ModelLoaderState.Init
    });

    private _source: SingleElementReadSource<T>;
    private _sub = new SubscriptionObject();

    constructor(source?: SingleElementReadSource<T>) {
        if (source) {
            this.source = source;
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

    get model(): T | undefined {
        return this._stream.value.model;
    }

    get modelAsync(): Observable<T | undefined> {
        return this._stream.pipe(map(x => x.model));
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

            this._sub.subscription = this._source.stream.subscribe((event: ConversionSourceEvent<ModelKey, T>) => {
                const model = event.elements[0];
                const failed = event.failed[0];
                const sourceState: SourceState = event.state;

                if (model) {
                    this._stream.next({
                        state: ModelLoaderState.Data,
                        model
                    });
                } else {
                    let newState: ModelLoaderState;

                    if (failed) {
                        newState = ModelLoaderState.Failed;
                    } else {
                        switch (sourceState) {
                            case SourceState.Done:
                                newState = ModelLoaderState.Data;
                                break;
                            default:
                                newState = ModelLoaderState.Loading;
                                break;
                        }
                    }

                    this._stream.next({
                        state: newState
                    });
                }
            }, (error) => {
                this._stream.next({
                    state: ModelLoaderState.Error,
                    error
                });
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
