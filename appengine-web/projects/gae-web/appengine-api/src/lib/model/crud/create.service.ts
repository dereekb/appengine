import { Injectable } from '@angular/core';

import { ValueUtility, NumberModelKey } from '@gae-web/appengine-utility';

import { ClientRequestError } from './error';
import { EditApiRequest } from './request';
import { AbstractClientTemplateResponse, TemplateResponse, AbstractTemplateCrudService } from './template.service';
import { ClientApiResponse, RawClientResponseAccessor } from '../client';
import { InvalidAttribute, KeyedInvalidAttribute } from './error';

import { Observable } from 'rxjs';
import { HttpResponse } from '@angular/common/http';
import { ApiResponseJson } from '../../api';
import { map } from 'rxjs/operators';
import { CrudServiceConfig } from './crud.service';

// MARK: Generic Interfaces
export interface CreateRequest<T> {
    readonly templates: T | T[];
    readonly options?: CreateRequestOptions;
}

export interface CreateRequestOptions {
    readonly atomic: boolean;
}

export interface CreateResponse<T> extends TemplateResponse<T> {
    readonly invalidTemplates: InvalidCreateTemplatePair<T>[];
}

export abstract class CreateService<T> {
    abstract create(request: CreateRequest<T>): Observable<CreateResponse<T>>;
}

// MARK: Implementation
export const DEFAULT_CREATE_OPTIONS: CreateRequestOptions = {
    atomic: true
};

@Injectable()
export class ClientCreateService<T, O> extends AbstractTemplateCrudService<T, O> implements CreateService<T> {

    constructor(config: CrudServiceConfig<T, O>) {
        super(config);
    }

    // MARK: ClientCreateService
    public create(request: CreateRequest<T>): Observable<ClientCreateResponse<T>> {
        const templates = ValueUtility.normalizeArray(request.templates);

        if (templates.length > 0) {
            const url = this.rootPath + '/' + this.type + '/create';

            const createOptions: CreateRequestOptions = { ...DEFAULT_CREATE_OPTIONS, ...request.options };

            const atomic = createOptions.atomic;
            const templateDtos = this._clientConfig.serializer.convertArrayToDto(templates);
            const apiRequest = new CreateApiRequest<O>(atomic, templateDtos);

            const body = apiRequest;
            const obs = this.httpClient.post<ApiResponseJson>(url, body, {
                observe: 'response'
            });

            return this.handleCreateResponse(request, obs);
        } else {
            return Observable.throw(new ClientRequestError('No templates were provided in the request.'));
        }
    }

    protected handleCreateResponse(request: CreateRequest<T>, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientCreateResponse<T>> {
        return super.handleResponse(obs).pipe(
            map((response: ClientApiResponse) => {
                return this.buildCreateResponse(request, response);
            })
        );
    }

    protected buildCreateResponse(request: CreateRequest<T>, response: ClientApiResponse): ClientCreateResponse<T> {
        return new ClientCreateResponse<T>(request, response, this);
    }

}

export class ClientCreateResponse<T> extends AbstractClientTemplateResponse<T> implements CreateResponse<T> {

    private _invalidTemplatePairs: InvalidCreateTemplatePair<T>[];

    constructor(private _request: CreateRequest<T>, response: ClientApiResponse, _createService: ClientCreateService<T, {}>) {
        super(response, _createService);
    }

    get invalidTemplates() {
        return this.getInvalidTemplatePairs();
    }

    protected getInvalidTemplatePairs() {
        if (!this._invalidTemplatePairs) {
            this._invalidTemplatePairs = this.buildInvalidTemplatePairs();
        }

        return this._invalidTemplatePairs;
    }

    protected buildInvalidTemplatePairs(): InvalidCreateTemplatePair<T>[] {
        const templates = this._request.templates;
        const invalidTemplates = super.getInvalidTemplates();

        return invalidTemplates.map((invalidTemplate) => {
            const index: NumberModelKey = Number(invalidTemplate.key);
            const template = templates[index];

            return new InvalidCreateTemplatePair<T>(template, invalidTemplate);
        });
    }

}

// MARK: Internal

/**
 * Atomic edit request.
 */
export class CreateApiRequest<T> extends EditApiRequest<T> { }

export class InvalidCreateTemplatePair<T> extends KeyedInvalidAttribute {

    constructor(private _template: T, attribute: KeyedInvalidAttribute) {
        super(attribute.key, attribute);
    }

    get template() {
        return this._template;
    }

}
