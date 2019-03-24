import { Injectable } from '@angular/core';

import { ClientRequestError, ClientAtomicOperationError } from './error';

import { ClientModelResponse } from '../client.service';
import { CrudServiceConfig, AbstractCrudService } from './crud.service';
import { ModelServiceResponse } from './response';
import { ClientApiResponse, RawClientResponseAccessor } from '../client';

import { Observable } from 'rxjs';
import { ModelUtility } from '@gae-web/appengine-utility';
import { ApiResponseJson } from '../../api';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { ModelKey } from '@gae-web/appengine-utility';

// MARK: Generic Interfaces
export interface ReadRequest {
    readonly atomic?: boolean;
    readonly modelKeys: ModelKey | ModelKey[];
}

export type ReadResponse<T> = ModelServiceResponse<T>;

export abstract class ReadService<T> {
    readonly type: string;
    abstract read(request: ReadRequest): Observable<ReadResponse<T>>;
}

// MARK: Implementation
@Injectable()
export class ClientReadService<T, O> extends AbstractCrudService<T, O> implements ReadService<T> {

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
            if (keysArray.length > ClientReadService.MAX_KEYS_ALLOWED_PER_REQUEST) {
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
        return new ClientReadResponse<T>(response, this);
    }

}

export class ClientReadResponse<T> extends ClientModelResponse<T> implements ReadResponse<T> {

    private _failed: ModelKey[];

    constructor(response: ClientApiResponse, _readService: ClientReadService<T, {}>) {
        super(response, _readService);
    }

    get failed() {
        if (!this._failed) {
            this._failed = ClientAtomicOperationError.serializeMissingResourceKeys(this.raw);
        }

        return this._failed;
    }

}
