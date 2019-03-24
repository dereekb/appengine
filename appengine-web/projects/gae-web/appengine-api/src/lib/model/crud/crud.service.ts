import { ClientApiResponse } from '../client';
import { ClientAtomicOperationError } from './errors';

import { ClientModelServiceConfig, AbstractClientModelService, AbstractClientModelResponseImpl } from '../client.service';
import { ModelKey } from '@gae-web/appengine-utility/lib/model';

export type CrudServiceConfig<T, O>  = ClientModelServiceConfig<T, O>;

export abstract class AbstractCrudService<T, O> extends AbstractClientModelService<T, O> {

    constructor(config: ClientModelServiceConfig<T, O>) {
        super(config);
    }

    protected handleClientApiResponseError(response: ClientApiResponse): any | undefined {
        return ClientAtomicOperationError.tryMakeAtomicOperationError(response);
    }

}

export class AbstractCrudModelResponseImpl<T> extends AbstractClientModelResponseImpl<T> {

    private _missing: ModelKey[];

    constructor(response: ClientApiResponse, private _crudService: AbstractCrudService<T, {}>) {
        super(response, _crudService);
    }

    protected getSerializedMissingKeys() {
        if (!this._missing) {
            this._missing = ClientAtomicOperationError.serializeMissingResourceKeys(this.raw);
        }

        return this._missing;
    }

}
