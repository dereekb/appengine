import { Injectable } from '@angular/core';

import { CrudServiceConfig } from './crud.service';

import { ValueUtility, ModelKey } from '@gae-web/appengine-utility';

import { ClientRequestError } from './error';
import { EditApiRequest } from './request';
import { ModelServiceResponse } from './response';
import { AbstractClientTemplateResponse, TemplateResponse, AbstractTemplateCrudService } from './template.service';
import { ClientApiResponse, RawClientResponseAccessor } from '../client';

import { Observable, throwError } from 'rxjs';
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

export abstract class UpdateService<T> {
    abstract update(request: UpdateRequest<T>): Observable<UpdateResponse<T>>;
}

// MARK: Implementation
export const DEFAULT_UPDATE_OPTIONS: UpdateRequestOptions = {
    atomic: true
};

export class ClientUpdateService<T, O> extends AbstractTemplateCrudService<T, O> implements UpdateService<T> {

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
            return throwError(new ClientRequestError('No templates were provided in the request.'));
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
        return new ClientUpdateResponse<T>(request, response, this);
    }

}

export class ClientUpdateResponse<T> extends AbstractClientTemplateResponse<T> implements UpdateResponse<T> {

    private _failed: ModelKey[];

    constructor(_request: UpdateRequest<T>, response: ClientApiResponse, _updateService: ClientUpdateService<T, {}>) {
        super(response, _updateService);
    }

    get failed(): ModelKey[] {
        if (!this._failed) {
            const templateKeys = super.getInvalidTemplateKeys();
            return templateKeys.concat(this.missing);
        }

        return this._failed;
    }

    get missing(): ModelKey[] {
        return this.getSerializedMissingKeys();
    }

}

// MARK: Internal

/**
 * Atomic edit request.
 */
export class UpdateApiRequest<T> extends EditApiRequest<T> { }
