
import { CrudServiceConfig, AbstractCrudService, AbstractCrudModelResponseImpl } from './crud.service';
import { ClientKeyedInvalidAttributeError, KEYED_INVALID_ATTRIBUTE_ERROR_CODE, KeyedInvalidAttribute } from './errors';
import { KeyedInvalidAttributeImpl } from './errors';

import { ApiResponseError } from '../../api';
import { ClientApiResponse } from '../client';
import { ModelServiceModelsResponse } from './response';
import { ClientApiResponseErrorType } from '../client';
import { ModelKey } from '@gae-web/appengine-utility/lib/model';

export interface TemplateResponse<T> extends ModelServiceModelsResponse<T> {

    readonly invalidTemplates: KeyedInvalidAttribute[];

}

export abstract class AbstractTemplateCrudService<T, O> extends AbstractCrudService<T, O> {

    constructor(config: CrudServiceConfig<T, O>) {
        super(config);
    }

    protected handleClientApiResponseError(response: ClientApiResponse): any | undefined {
        return this.tryMakeKeyedInvalidAttributeError(response) || super.handleClientApiResponseError(response);
    }

    // MARK: Assertions
    protected tryMakeKeyedInvalidAttributeError(response: ClientApiResponse): Error | undefined {
        if (response.error.type === ClientApiResponseErrorType.BadResponse) {
            const invalidAttributes = this.serializeInvalidAttributes(response);

            if (invalidAttributes.length) {
                return new ClientKeyedInvalidAttributeError(response, invalidAttributes);
            }
        }

        return undefined;
    }

    // MARK: Serialization
    public serializeInvalidAttributes(response: ClientApiResponse): KeyedInvalidAttribute[] {
        const error = response.error.getError(KEYED_INVALID_ATTRIBUTE_ERROR_CODE);
        let result: KeyedInvalidAttribute[];

        if (error) {
            result = this.serializeInvalidAttributesFromError(error);
        } else {
            result = [];
        }

        return result;
    }

    public serializeInvalidAttributesFromError(error: ApiResponseError): KeyedInvalidAttribute[] {
        return this.serializeInvalidAttributesFromJson(error.data);
    }

    public serializeInvalidAttributesFromJson(json: any): KeyedInvalidAttribute[] {
        let attributes: KeyedInvalidAttribute[];

        if (Array.isArray(json)) {
            attributes = json.map(KeyedInvalidAttributeImpl.fromJson);
        } else {
            throw new Error('Invalid json passed. Expected array.');
        }

        return attributes;
    }

}

export class AbstractClientTemplateResponseImpl<T> extends AbstractCrudModelResponseImpl<T> implements TemplateResponse<T> {

    private _invalidTemplates: KeyedInvalidAttribute[];

    constructor(response: ClientApiResponse, private _templateService: AbstractTemplateCrudService<T, {}>) {
        super(response, _templateService);
    }

    get invalidTemplates() {
        return this.getInvalidTemplates();
    }

    protected getInvalidTemplateKeys(): ModelKey[] {
        return this.getInvalidTemplates().map((x) => x.key);
    }

    protected getInvalidTemplates() {
        if (!this._invalidTemplates) {
            this._invalidTemplates = this._templateService.serializeInvalidAttributes(this.raw);
        }

        return this._invalidTemplates;
    }

}
