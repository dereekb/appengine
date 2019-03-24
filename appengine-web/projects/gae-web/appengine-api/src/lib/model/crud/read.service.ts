import { Injectable } from '@angular/core';

import { ClientRequestError, ClientAtomicOperationError } from './errors';

import { AbstractClientModelResponseImpl } from '../client.service';
import { CrudServiceConfig, AbstractCrudService } from './crud.service';
import { ModelServiceResponse } from './response';
import { ClientApiResponse, RawClientResponseAccessor } from '../client';

import { Observable } from 'rxjs';
import { ModelUtility } from 'projects/gae-web/appengine-utility/src/lib/model';
import { ApiResponseJson } from '../../api';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { ModelKey } from '@gae-web/appengine-utility/lib/model';

// MARK: Generic Interfaces
export interface ReadRequest {

    readonly atomic?: boolean;
    readonly modelKeys: ModelKey | ModelKey[];

}

export type ReadResponse<T> = ModelServiceResponse<T>;

export interface ReadService<T> {

    readonly type: string;

    read(request: ReadRequest): Observable<ReadResponse<T>>;

}

// MARK: Client Interfaces
export interface ClientReadResponse<T> extends ReadResponse<T>, RawClientResponseAccessor {

}

export interface ClientReadService<T> extends ReadService<T> {

    read(request: ReadRequest): Observable<ClientReadResponse<T>>;

}

// MARK: Implementation
@Injectable()
export class ClientReadServiceImpl<T, O> extends AbstractCrudService<T, O> implements ClientReadService<T> {

    static readonly MAX_KEYS_ALLOWED_PER_REQUEST = 40;

    constructor(config: CrudServiceConfig<T, O>) {
        super(config);
    }

    // MARK: ClientReadService
    public get type() {
        return this._clientConfig.type;
    }

    public read(request: ReadRequest): Observable<ClientReadResponse<T>> {
        const keysArray: string[] = ModelUtility.makeStringModelKeysArray(request.modelKeys);

        if (keysArray.length) {
            if (keysArray.length > ClientReadServiceImpl.MAX_KEYS_ALLOWED_PER_REQUEST) {
                return Observable.throw(new ClientRequestError('Too many keys requested.'));
            }

            const keysParam = ModelUtility.makeModelKeysParameterWithStringArray(keysArray);

            const params = {
                keys: keysParam,
                atomic: request.atomic ? String(request.atomic) : undefined
            };

            const url = this.rootPath + '/' + this.type;
            const obs = this.httpClient.get<ApiResponseJson>(url, {
                observe: 'response',
                params
            });

            return this.handleReadResponse(obs);
        } else {
            return Observable.throw(new ClientRequestError('No usable keys were passed in the request.'));
        }
    }

    protected handleReadResponse(obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientReadResponse<T>> {
        return super.handleResponse(obs).pipe(
            map((response: ClientApiResponse) => {
                return this.buildReadResponse(response);
            })
        );
    }

    protected buildReadResponse(response: ClientApiResponse): ClientReadResponse<T> {
        return new ClientReadResponseImpl<T>(response, this);
    }

}

class ClientReadResponseImpl<T> extends AbstractClientModelResponseImpl<T> implements ClientReadResponse<T> {

    private _failed: ModelKey[];

    constructor(response: ClientApiResponse, private _readService: ClientReadServiceImpl<T, {}>) {
        super(response, _readService);
    }

    get failed() {
        if (!this._failed) {
            this._failed = ClientAtomicOperationError.serializeMissingResourceKeys(this.raw);
        }

        return this._failed;
    }

}
