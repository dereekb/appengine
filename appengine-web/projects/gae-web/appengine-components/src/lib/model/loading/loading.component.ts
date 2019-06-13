import { Output, Input, Component, ViewEncapsulation, ViewChild } from '@angular/core';
import { ModelLoader, ModelLoaderLoadingContext, ModelLoaderSourceWrapper } from './model-loader.component';
import { LoadingContext } from '../../loading/loading';
import { SingleElementReadSource } from '@gae-web/appengine-utility/lib/source';

/**
 * Component that drives a GaeLoadingComponent using a source or a loader.
 */
@Component({
    selector: 'gae-model-loading-view',
    templateUrl: './loading.component.html'
})
export class GaeModelLoadingViewComponent {

    private _context: ModelLoaderLoadingContext;

    public get context(): ModelLoaderLoadingContext {
        return this._context;
    }

    @Input()
    public set source(source: SingleElementReadSource<any>) {
        let loader: ModelLoader<any>;

        if (source) {
            loader = new ModelLoaderSourceWrapper(source);
        }

        this.loader = loader;
    }

    @Input()
    public set loader(loader: ModelLoader<any>) {
        let context: ModelLoaderLoadingContext;

        if (loader) {
            context = new ModelLoaderLoadingContext(loader);
        }

        this._context = context;
    }

}
