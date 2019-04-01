import { Observable, from } from 'rxjs';
import { ServiceSetupError, ServiceLoadingError } from './error';

/**
 * Used for loading external components, such as the Facebook API.
 */
export abstract class AbstractAsyncLoadedService<T> {

    private _service: T;
    private _loading: Promise<T>;

    constructor(private _windowKey: string, private _callbackKey: string, private _serviceName = _windowKey) { }

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
                const windowRef = (window as any);

                // Loaded before the promise.
                if (windowRef[this._windowKey]) {
                    // Not yet finished loading async. Intercept the function.
                    return resolve(this.completeLoadingService());
                } else if (windowRef[this._callbackKey]) {
                    windowRef[this._callbackKey] = () => resolve(this.completeLoadingService());
                } else {
                    throw new ServiceSetupError(`Project is not setup properly for service "${ this._serviceName}"`);
                }
            });
        }

        return this._loading;
    }

    private completeLoadingService(): Promise<T> {
        this._service = window[this._windowKey];

        if (!this._service) {
            throw new ServiceLoadingError(`Service "${ this._serviceName}" failed loading.`);
        }

        // Init the API
        return this.initService(this._service).then((service) => {
            this._service = service || this._service;

            // console.log('Finished loading service "' + this._serviceName + '".');

            return this._service;
        });
    }

    protected initService(service: T): Promise<T> {
        return Promise.resolve(service);
    }

}

