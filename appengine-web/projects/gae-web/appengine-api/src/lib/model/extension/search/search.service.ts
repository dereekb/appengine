import { ClientModelServiceConfig, AbstractClientModelService } from '../../client.service';

import { ClientApiResponse, RawClientResponseAccessor } from '../../client';

import { ValueUtility, ModelKey, UniqueModel, ModelUtility } from '@gae-web/appengine-utility';

import { BaseError } from 'make-error';

export type SearchCursor = string;

export type SearchParameter = string;

export interface SearchParametersSet {
    [key: string]: SearchParameter;
}

export interface SearchParameters {
    readonly parameters: SearchParametersSet;
}

// MARK: Generic Interfaces
export interface SearchOptions {
    readonly cursor?: SearchCursor;
    readonly limit?: number;
    readonly offset?: number;
}

export interface LimitedSearchRequest extends SearchOptions {
    readonly isKeysOnly?: boolean;
}

export interface SearchRequest extends LimitedSearchRequest {
    readonly parameters?: SearchParameters;
}

export interface ModelSearchResponse<T> extends SearchResponse {
    readonly isKeysOnlyResponse: boolean;
    readonly modelResults?: T[];
}

export interface SearchResponse {
    readonly cursor: SearchCursor;
    readonly keyResults: ModelKey[];
}

// MARK: Service
export abstract class AbstractSearchService<T extends UniqueModel, O> extends AbstractClientModelService<T, O> {

    constructor(config: ClientModelServiceConfig<T, O>) {
        super(config);
    }

    protected buildUrlSearchParams(request: SearchRequest): object {
        const params = {} as any;

        if (request.parameters) {
            ValueUtility.copyObjectProperties(request.parameters.parameters, params);
        }

        if (request.isKeysOnly !== undefined) {
            params.keysOnly = String(request.isKeysOnly);
        }

        if (request.cursor) {
            params.cursor = request.cursor;
        }

        if (request.offset) {
            params.offset = String(request.offset);
        }

        if (request.limit) {
            params.limit = String(request.limit);
        }

        return params;
    }

}

export class AbstractSearchServiceResponse<T extends UniqueModel> extends RawClientResponseAccessor implements ModelSearchResponse<T> {

    private _keys: ModelKey[];
    private _models: T[];

    private _serializer: SearchResponseDataSerializer<T>;

    constructor(response: ClientApiResponse, protected _request: SearchRequest, protected _searchService: AbstractSearchService<T, {}>) {
        super(response);
    }

    // MARK: ModelSearchResponse
    get modelResults() {
        return this.getModelResults();
    }

    protected getModelResults() {
        if (!this._models) {
            if (this.isKeysOnlyResponse) {
                throw new KeysOnlySearchError();
            } else {
                this._models = this.serializer.serializeModels();
            }
        }

        return this._models;
    }

    get isKeysOnlyResponse() {
        return this._request.isKeysOnly;
    }

    // MARK: SearchResponse
    get cursor() {
        return this.serializer.cursor;
    }

    get keyResults() {
        return this.getKeyResults();
    }

    protected getKeyResults() {
        if (!this._keys) {
            if (this.isKeysOnlyResponse) {
                this._keys = this.serializer.serializeKeys();
            } else {
                this._keys = ModelUtility.readModelKeys(this.modelResults);
            }
        }

        return this._keys;
    }

    // MARK: Internal
    protected get serializer(): SearchResponseDataSerializer<T> {
        if (!this._serializer) {
            const data = this.raw.response.data;
            const searchResultsJson = data.data as SearchResultsJson;
            this._serializer = new SearchResponseDataSerializer(this._searchService, searchResultsJson);
        }

        return this._serializer;
    }

}

interface SearchResultsJson {
    modelType: string;
    data: any;
    cursor: string;
}

class SearchResponseDataSerializer<T extends UniqueModel> {

    constructor(private _searchService: AbstractSearchService<T, {}>, private _json: SearchResultsJson) { }

    public serializeModels(): T[] {
        const data = this._json.data;
        return this._searchService.serializedModels(data);
    }

    public serializeKeys(): ModelKey[] {
        const data = this._json.data;
        return this._searchService.serializeKeys(data);
    }

    get cursor(): SearchCursor {
        return this._json.cursor;
    }

}

// MARK: Exception
export class KeysOnlySearchError extends BaseError {

    constructor(message: string = 'Keys only result returned.') {
        super(message);
    }

}
