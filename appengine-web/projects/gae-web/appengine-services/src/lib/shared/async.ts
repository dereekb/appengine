import { Observable, from } from 'rxjs';
import { ServiceSetupError, ServiceLoadingError } from './error';

/**
 * Used for loading external components, such as the Facebook API.
 */
export abstract class AbstractAsyncLoadedService<T> {

    private _service: T;
    private _loading: Promise<T>;

    constructor(private _windowKey: string, private _callbackKey?: string, private _serviceName = _windowKey, preload = true) {
        if (preload) {
            // Begin loading the service immediately.
            this.loadService();
        }
    }

    protected syncGetService(): T | undefined {
        return this._service;
    }

    protected getServiceObs(): Observable<T> {
        return from(this.getService());
    }

    protected getService(): Promise<T> {
        if (this._service) {
            return Promise.resolve(this._service);
        } else {
            return this.loadService();
        }
    }

    protected loadService(): Promise<T> {
        if (!this._loading) {
            this._loading = new Promise((resolve, reject) => {
                let loadTry = 0;

                const tryLoad = () => {
                    const windowRef = (window as any);

                    // Loaded before the promise.
                    if (windowRef[this._windowKey]) {
                        // Not yet finished loading async. Intercept the function.
                        console.log('Window key.');
                        return resolve(this.completeLoadingService());
                    } else if (this._callbackKey && windowRef[this._callbackKey]) {
                        console.log('Callback key.');
                        windowRef[this._callbackKey] = () => resolve(this.completeLoadingService());
                    } else if (loadTry < 10) {
                        loadTry += 1;
                        console.log('Try reload...');
                        setTimeout(() => tryLoad(), 1000);
                    } else {
                        reject(new ServiceSetupError(`Project is not setup properly for service "${this._serviceName}"`));
                    }
                };

                tryLoad();
            });
        }

        return this._loading;
    }

    private completeLoadingService(): Promise<T> {
        return this.prepareCompleteLoadingService().then(() => {
            this._service = window[this._windowKey];

            if (!this._service) {
                throw new ServiceLoadingError(`Service "${this._serviceName}" failed loading.`);
            }

            // Init the API
            return this.initService(this._service).then((service) => {
                this._service = service || this._service;
                // console.log('Finished loading service "' + this._serviceName + '".');
                return this._service;
            });
        });
    }

    protected prepareCompleteLoadingService(): Promise<any> {
        return Promise.resolve();
    }

    protected initService(service: T): Promise<T> {
        return Promise.resolve(service);
    }

}

