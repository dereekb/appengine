import { ModelLoaderState, ModelLoader, AbstractModelLoaderStateComponent, ModelLoaderEvent } from './model-loader.component';
import { Directive, OnDestroy, Input } from '@angular/core';
import { BehaviorSubject, merge, Observable } from 'rxjs';
import { SubscriptionObject } from '@gae-web/appengine-utility';
import { map } from 'rxjs/operators';

export function aggregateEvents(a: ModelLoaderEvent<any>, b: ModelLoaderEvent<any>): ModelLoaderEvent<any> {
    return {
        state: mostImportantState(a.state, b.state),
        error: a.error || b.error
    };
}

export function mostImportantState(a: ModelLoaderState, b: ModelLoaderState): ModelLoaderState {
    switch (a) {
        case ModelLoaderState.Loading:
            return (b <= ModelLoaderState.Data) ? a : b;    // Return Loading if b is Init, Loading, or Data.
        case ModelLoaderState.Init:
        case ModelLoaderState.Data:
            return b; // Always return b.
        case ModelLoaderState.Failed:
        case ModelLoaderState.Error:
            return a; // Failed and error always take priority;
    }
}

/**
 * Used to watch multiple ModelLoader values and sets the loader state based on the state of all loading objects.
 */
@Directive({
    selector: '[gaeMultiModelLoader]',
    exportAs: 'gaeMultiModelLoader'
})
export class GaeMultiModelLoaderDirective extends AbstractModelLoaderStateComponent implements ModelLoader<any>, OnDestroy {

    // Does nothing.
    public optional;

    private _loaders: ModelLoader<{}>[] = [];
    private _sub = new SubscriptionObject();
    private _stream = new BehaviorSubject<ModelLoaderEvent<any>>({
        state: ModelLoaderState.Init
    });

    ngOnDestroy() {
        this._sub.destroy();
        this._stream.complete();

        delete this._loaders;
        delete this._stream;
    }

    public get state() {
        return this._stream.value.state;
    }

    public get model() {
        return this.models;
    }

    public get error() {
        return this._stream.value.error;
    }

    public get stream(): Observable<ModelLoaderEvent<any>> {
        return this._stream.asObservable();
    }

    public get stateObs(): Observable<ModelLoaderState> {
        return this._stream.pipe(
            map(x => x.state)
        );
    }

    get models(): any[] {
        return this._loaders.map((x) => x.model);
    }

    @Input()
    public set gaeMultiModelLoader(loaders: ModelLoader<{}>[]) {
        this.setLoaders(loaders);
    }

    protected setLoaders(loaders: ModelLoader<{}>[] = []) {
        if (this._loaders === loaders) {
            return;
        }

        this._loaders = loaders;

        const observables = loaders.map((loader) => loader.stream);
        this._sub.subscription = merge(...observables)
            .subscribe(() => this._update());
    }

    private _update() {
        const event = this._loaders
            .reduce((x, y) => aggregateEvents(x, y), { state: ModelLoaderState.Data });
        this._stream.next(event);
    }

}
