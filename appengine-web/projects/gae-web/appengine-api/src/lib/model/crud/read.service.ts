import { Injectable } from '@angular/core';

import { ClientRequestError, ClientAtomicOperationError, LargeAtomicRequestError } from './error';

import { ClientModelResponse } from '../client.service';
import { CrudServiceConfig, AbstractCrudService } from './crud.service';
import { ModelServiceResponse } from './response';
import { ClientApiResponse, RawClientResponseAccessor } from '../client';

import { Observable, of, from, forkJoin, throwError } from 'rxjs';
import { ModelUtility, ValueUtility } from '@gae-web/appengine-utility';
import { ApiResponseJson } from '../../api';
import { HttpResponse } from '@angular/common/http';
import { map, flatMap, concatMapTo, toArray } from 'rxjs/operators';
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

    public static readonly MAX_KEYS_ALLOWED_PER_REQUEST = 40;

    constructor(config: CrudServiceConfig<T, O>) {
        super(config);
    }

    // MARK: ClientReadService
    public get type() {
        return this._clientConfig.type;
    }

    public read(request: ReadRequest): Observable<ClientReadResponse<T>> {
        const keysArray: ModelKey[] = ModelUtility.makeStringModelKeysArray(request.modelKeys);
        const keysBatches = ValueUtility.batch(keysArray, ClientReadService.MAX_KEYS_ALLOWED_PER_REQUEST);

        if (keysArray.length === 0) {
            return throwError(new ClientRequestError('No usable keys were passed in the request.'));
        } else if (keysBatches.length > 1 && request.atomic) {
            throw new LargeAtomicRequestError('Too many keys requested.');
        }

        if (keysBatches.length <= 1) {
            return this._read({
                ...request,
                modelKeys: keysBatches[0]
            });
        } else {
            const requestBatches = keysBatches.map((modelKeys) => {
                return {
                    ...request,
                    modelKeys
                };
            });

            const parallelObs = from(requestBatches).pipe(
                flatMap((x: ReadRequest) => this._read(x))
            );

            return parallelObs.pipe(
                toArray(),
                map((responses: ClientReadResponse<T>[]) => {
                    return new MultiClientReadResponse(responses, this);
                })
            );
        }
    }

    protected _read(request: ReadRequest): Observable<ClientReadResponse<T>> {
        const modelKeys = request.modelKeys as string[];
        const keysParam = ModelUtility.makeModelKeysParameterWithStringArray(modelKeys);

        const params: any = {
            keys: keysParam
        };

        if (request.atomic) {
            params.atomic = String(request.atomic);
        }

        const url = this.rootPath + '/' + this.type;
        const obs = this.httpClient.get<ApiResponseJson>(url, {
            observe: 'response',
            params
        });

        return this.handleReadResponse(obs);
    }

    protected handleReadResponse(obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientReadResponse<T>> {
        return super.handleResponse(obs).pipe(
            map((response: ClientApiResponse) => {
                return this.buildReadResponse(response);
            })
        );
    }

    protected buildReadResponse(response: ClientApiResponse): ClientReadResponse<T> {
        return new ClientReadServiceResponse<T>(response, this);
    }

}

export interface ClientReadResponse<T> extends ReadResponse<T> {

}

export class ClientReadServiceResponse<T> extends ClientModelResponse<T> implements ReadResponse<T> {

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

export class MultiClientReadResponse<T> implements ClientReadResponse<T> {

    private _models: T[];
    private _failed: ModelKey[];

    constructor(private readonly _responses: ClientReadResponse<T>[], private readonly _service: ClientReadService<T, {}>) { }

    get models() {
        if (!this._models) {
            this._models = ValueUtility.reduceArray(this._responses.map((x) => x.models));
        }

        return this._models;
    }

    get failed() {
        if (!this._failed) {
            this._failed = ValueUtility.reduceArray(this._responses.map((x) => x.failed));
        }

        return this._failed;
    }

}
