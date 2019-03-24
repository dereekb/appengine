import { Injectable } from '@angular/core';

import { CrudServiceConfig } from './crud.service';

import { ValueUtility, ModelKey } from '@gae-web/appengine-utility';

import { ClientRequestError } from './errors';
import { EditApiRequest } from './request';
import { ModelServiceResponse } from './response';
import { AbstractClientTemplateResponseImpl, TemplateResponse, AbstractTemplateCrudService } from './template.service';
import { ClientApiResponse, RawClientResponseAccessor } from '../client';

import { Observable } from 'rxjs';
import { ApiResponseJson } from '../../api';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';

// MARK: Generic Interfaces
export interface UpdateRequest<T> {

    readonly templates: T | T[];
    readonly options?: UpdateRequestOptions;

}

export interface UpdateRequestOptions {

    readonly atomic: boolean;

}

export interface UpdateResponse<T> extends TemplateResponse<T>, ModelServiceResponse<T> {

    readonly models: T[];

    readonly missing: ModelKey[];

}

export interface UpdateService<T> {

    update(request: UpdateRequest<T>): Observable<UpdateResponse<T>>;

}

// MARK: Client Interfaces
export interface ClientUpdateResponse<T> extends UpdateResponse<T>, RawClientResponseAccessor {

}

export interface ClientUpdateService<T> extends UpdateService<T> {

    update(request: UpdateRequest<T>): Observable<ClientUpdateResponse<T>>;

}

// MARK: Implementation
export const DEFAULT_UPDATE_OPTIONS: UpdateRequestOptions = {
    atomic: true
};

@Injectable()
export class ClientUpdateServiceImpl<T, O> extends AbstractTemplateCrudService<T, O> implements ClientUpdateService<T> {

    constructor(config: CrudServiceConfig<T, O>) {
        super(config);
    }

    // MARK: ClientUpdateService
    public update(request: UpdateRequest<T>): Observable<ClientUpdateResponse<T>> {
        const templates = ValueUtility.normalizeArray(request.templates);

        if (templates.length > 0) {
            const url = this.rootPath + '/' + this.type + '/update';

            const updateOptions: UpdateRequestOptions = { ...DEFAULT_UPDATE_OPTIONS, ...request.options };

            const atomic = updateOptions.atomic;
            const templateDtos = this._clientConfig.serializer.convertArrayToDto(templates);
            const apiRequest = new UpdateApiRequest<O>(atomic, templateDtos);

            const body = apiRequest;

            const obs = this.httpClient.put<ApiResponseJson>(url, body, {
                observe: 'response'
            });

            return this.handleUpdateResponse(request, obs);
        } else {
            return Observable.throw(new ClientRequestError('No templates were provided in the request.'));
        }
    }

    protected handleUpdateResponse(request: UpdateRequest<T>, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientUpdateResponse<T>> {
        return super.handleResponse(obs).pipe(
            map((response: ClientApiResponse) => {
                return this.buildUpdateResponse(request, response);
            })
        );
    }

    protected buildUpdateResponse(request: UpdateRequest<T>, response: ClientApiResponse): ClientUpdateResponse<T> {
        return new ClientUpdateResponseImpl<T>(request, response, this);
    }

}

export class ClientUpdateResponseImpl<T> extends AbstractClientTemplateResponseImpl<T> implements ClientUpdateResponse<T> {

    private _failed: ModelKey[];

    constructor(private _request: UpdateRequest<T>, response: ClientApiResponse, private _updateService: ClientUpdateServiceImpl<T, {}>) {
        super(response, _updateService);
    }

    get failed() {
        if (!this._failed) {
            const templateKeys = super.getInvalidTemplateKeys();
            return templateKeys.concat(this.missing);
        }

        return this._failed;
    }

    get missing() {
        return this.getSerializedMissingKeys();
    }

}

// MARK: Internal

/**
 * Atomic edit request.
 */
export class UpdateApiRequest<T> extends EditApiRequest<T> { }
