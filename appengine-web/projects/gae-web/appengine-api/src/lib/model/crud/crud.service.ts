import { ClientApiResponse } from '../client';
import { ClientAtomicOperationError } from './error';

import { ClientModelServiceConfig, AbstractClientModelService, ClientModelResponse } from '../client.service';
import { ModelKey } from '@gae-web/appengine-utility';

export abstract class CrudServiceConfig<T, O> extends ClientModelServiceConfig<T, O> {}

export abstract class AbstractCrudService<T, O> extends AbstractClientModelService<T, O> {

    constructor(config: ClientModelServiceConfig<T, O>) {
        super(config);
    }

    protected handleClientApiResponseError(response: ClientApiResponse): any | undefined {
        return ClientAtomicOperationError.tryMakeAtomicOperationError(response);
    }

}

export class CrudModelResponse<T> extends ClientModelResponse<T> {

    private _missing: ModelKey[];

    constructor(response: ClientApiResponse, _crudService: AbstractCrudService<T, {}>) {
        super(response, _crudService);
    }

    protected getSerializedMissingKeys() {
        if (!this._missing) {
            this._missing = ClientAtomicOperationError.serializeMissingResourceKeys(this.raw);
        }

        return this._missing;
    }

}
