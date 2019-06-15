import { ClientApiResponse, ClientApiResponseErrorType, ClientApiResponseError } from '../client';
import { ClientModelUtility } from '../client.service';

import { BaseError } from 'make-error';
import { AlwaysKeyed, ModelKey } from '@gae-web/appengine-utility';

// MARK: Request Error
/**
 * Thrown internally when a request is rejected.
 */
export class ClientRequestError extends BaseError {

    constructor(message: string = 'The request was deemed invalid and rejected.') {
        super(message);
    }

}

/**
 * Thrown when an atomic request is too big.
 */
export class LargeAtomicRequestError extends BaseError {

    constructor(message: string = 'The atomic request is too large.') {
        super(message);
    }

}

// MARK: Atomic Error
export const MISSING_REQUIRED_RESOURCE_ERROR_CODE = 'MISSING_RESOURCE';

export class ClientAtomicOperationError extends ClientApiResponseError {

    // MARK: Assertions
    public static tryMakeAtomicOperationError(response: ClientApiResponse): Error | undefined {
        if (response.error.type === ClientApiResponseErrorType.BadResponse) {
            const missingKeys = this.serializeMissingResourceKeys(response);

            if (missingKeys.length) {
                return new ClientAtomicOperationError(response, missingKeys);
            }
        }

        return undefined;
    }

    // MARK: Serialization
    public static serializeMissingResourceKeys(response: ClientApiResponse): ModelKey[] {
        const error = response.error.getError(MISSING_REQUIRED_RESOURCE_ERROR_CODE);
        let result: ModelKey[];

        if (error) {
            result = ClientModelUtility.serializeKeysFromError(error);
        } else {
            result = [];
        }

        return result;
    }

    constructor(response: ClientApiResponse, public readonly failed: ModelKey[], message: string = 'Atomic Operation error due to one or more models failing the request.') {
        super(response, message);
    }

}

// MARK: Keyed Invalid Attribute Error
export const KEYED_INVALID_ATTRIBUTE_ERROR_CODE = 'INVALID_ATTRIBUTE';

export class ClientKeyedInvalidAttributeError extends ClientApiResponseError  {

    constructor(response: ClientApiResponse, public readonly attributes: KeyedInvalidAttribute[], message: string = 'One or more attributes on models were invalid.') {
        super(response, message);
    }

}

export class InvalidAttribute {

    private _attribute: string;
    private _value: string;
    private _detail: string;

    constructor({ attribute, value, detail = '' }: InvalidAttribute) {
        this._attribute = attribute;
        this._value = value;
        this._detail = detail;
    }

    get attribute(): string {
        return this._attribute;
    }

    get value(): string {
        return this._value;
    }

    get detail(): string {
        return this._detail;
    }

}

export class KeyedInvalidAttribute extends InvalidAttribute implements KeyedInvalidAttribute, AlwaysKeyed<ModelKey> {

    public static fromJson(json: any) {
        const key = json.key;
        const invalidAttribute = new InvalidAttribute(json);
        return new KeyedInvalidAttribute(key, invalidAttribute);
    }

    constructor(private _key: ModelKey, attr: InvalidAttribute) {
        super(attr);
    }

    get key() {
        return this._key;
    }

}
