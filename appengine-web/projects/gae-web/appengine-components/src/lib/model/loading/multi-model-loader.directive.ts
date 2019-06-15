import { ModelLoaderState, ModelLoader, AbstractModelLoaderStateComponent } from './model-loader.component';
import { Directive, OnDestroy, Input } from '@angular/core';
import { BehaviorSubject, merge } from 'rxjs';
import { SubscriptionObject } from '@gae-web/appengine-utility';

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
export class GaeMultiModelLoaderDirective extends AbstractModelLoaderStateComponent implements OnDestroy {

    private _loaders: ModelLoader<{}>[] = [];
    private _sub = new SubscriptionObject();
    private _state = new BehaviorSubject<ModelLoaderState>(ModelLoaderState.Init);

    ngOnDestroy() {
        this._sub.destroy();
        this._state.complete();

        delete this._loaders;
        delete this._state;
    }

    public get state() {
        return this._state.value;
    }

    public get stateObs() {
        return this._state.asObservable();
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
        const state = this._loaders
            .map((x) => x.state)
            .reduce((x, y) => mostImportantState(x, y), ModelLoaderState.Data);
        this._state.next(state);
    }

}
