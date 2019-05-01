import { Output, Input, Component, ViewEncapsulation, ViewChild } from '@angular/core';
import { UniqueModel } from '@gae-web/appengine-utility';
import { ModelLoader, ModelLoaderLoadingContext } from './model-loader.component';
import { SimpleLoadingContext } from '../../loading/loading';
import { LoadingContext } from '@gae-web/appengine-components/public-api';

/**
 * Component that reads a single element from a SingleElementConversionSource.
 */
@Component({
    selector: 'gae-model-loading-view',
    templateUrl: './loading.component.html'
})
export class GaeModelLoadingViewComponent {

    private _context: LoadingContext;

    public get context() {
        return this._context;
    }

    @Input()
    public set loader(loader: ModelLoader<any>) {
        let context: LoadingContext;

        if (loader) {
            context = new ModelLoaderLoadingContext(loader);
        }

        this._context = context;
    }

}
