import { Injectable } from '@angular/core';

import { AbstractCrudService, CrudModelResponse, CrudServiceConfig } from './crud.service';
import { ClientRequestError } from './error';
import { ModelServiceResponse } from './response';
import { ClientApiResponse, RawClientResponseAccessor } from '../client';

import { Observable } from 'rxjs';
import { ModelKey, UniqueModel, ModelUtility } from '@gae-web/appengine-utility';
import { ApiResponseJson } from '../../api';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';

// MARK: Generic Interfaces
export interface DeleteRequest {
    readonly keys: ModelKey | ModelKey[];
    readonly shouldReturnModels?: boolean;
    readonly options?: DeleteRequestOptions;
}

export interface DeleteRequestOptions {
    readonly atomic: boolean;
}

export interface DeleteResponse<T> extends ModelServiceResponse<T> {
    readonly keys: ModelKey[];
    readonly isModelsResponse: boolean;
}

export interface DeleteService<T> {
    delete(request: DeleteRequest): Observable<DeleteResponse<T>>;
}

// MARK: Implementation
export const DEFAULT_DELETE_OPTIONS: DeleteRequestOptions = {
    atomic: true
};

@Injectable()
export class ClientDeleteService<T extends UniqueModel, O> extends AbstractCrudService<T, O> implements DeleteService<T> {

    static readonly MAX_KEYS_ALLOWED_PER_REQUEST = 25;

    constructor(config: CrudServiceConfig<T, O>) {
        super(config);
    }

    // MARK: ClientDeleteService
    public delete(request: DeleteRequest): Observable<ClientDeleteResponse<T>> {
        const keysArray = ModelUtility.makeStringModelKeysArray(request.keys);

        if (keysArray.length > 0) {
            if (keysArray.length > ClientDeleteService.MAX_KEYS_ALLOWED_PER_REQUEST) {
                return Observable.throw(new ClientRequestError('Too many keys for delete.'));
            }

            const deleteOptions: DeleteRequestOptions = { ...DEFAULT_DELETE_OPTIONS, ...request.options };
            const atomic = deleteOptions.atomic;
            const keysParam = ModelUtility.makeModelKeysParameterWithStringArray(keysArray);

            const params = {
                keys: keysParam,
                atomic: String(atomic || false),
                returnModels: String(request.shouldReturnModels || false)
            };

            const url = this.rootPath + '/' + this.type + '/delete';
            const obs = this.httpClient.delete<ApiResponseJson>(url, {
                observe: 'response',
                params
            });

            return this.handleDeleteResponse(request, obs);
        } else {
            return Observable.throw(new ClientRequestError('No templates were provided in the request.'));
        }
    }

    protected handleDeleteResponse(request: DeleteRequest, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientDeleteResponse<T>> {
        return super.handleResponse(obs).pipe(
            map((response: ClientApiResponse) => {
                return this.buildDeleteResponse(request, response);
            })
        );
    }

    protected buildDeleteResponse(request: DeleteRequest, response: ClientApiResponse): ClientDeleteResponse<T> {
        return new ClientDeleteResponse<T>(request, response, this);
    }

}

export class ClientDeleteResponse<T extends UniqueModel> extends CrudModelResponse<T> implements DeleteResponse<T> {

    private _keys: ModelKey[];

    constructor(private _request: DeleteRequest, response: ClientApiResponse, _deleteService: ClientDeleteService<T, {}>) {
        super(response, _deleteService);
    }

    get isModelsResponse(): boolean {
        return Boolean(this._request.shouldReturnModels);
    }

    get models(): T[] {
        if (this.isModelsResponse) {
            return this.getModels();
        } else {
            return [];
        }
    }

    get keys(): ModelKey[] {
        if (!this._keys) {
            if (this.isModelsResponse) {
                this._keys = ModelUtility.readModelKeys(this.models);
            } else {
                this._keys = this.serializeKeysResponse();
            }
        }

        return this._keys;
    }

    get failed() {
        return this.getSerializedMissingKeys();
    }

}
