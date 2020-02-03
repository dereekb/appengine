import { ClientModelServiceConfig } from '../../../client.service';
import { ClientApiResponse } from '../../../client';

import { AbstractSearchService, AbstractSearchServiceResponse, ModelSearchResponse, SearchRequest } from '../search.service';

import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { ApiResponseJson } from '../../../../api';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';

// MARK: Generic Interfaces
export type SearchIndex = string;

export interface TypedModelSearchRequest extends SearchRequest {
    index?: SearchIndex;
    query?: string;
}

export type TypedModelSearchResponse<T extends UniqueModel> = ModelSearchResponse<T>;

export abstract class TypedModelSearchService<T extends UniqueModel> {
    abstract search(request: TypedModelSearchRequest): Observable<TypedModelSearchResponse<T>>;
}

// MARK: Client Interfaces
export class ClientTypedModelSearchService<T extends UniqueModel, O> extends AbstractSearchService<T, O> implements TypedModelSearchService<T> {

    constructor(config: ClientModelServiceConfig<T, O>) {
        super(config);
    }

    // MARK: ClientTypedModelSearchService
    public search(request: TypedModelSearchRequest): Observable<ClientTypedModelSearchResponse<T>> {
        const url = this.rootPath + '/' + this.type + '/search';

        const params: {} = this.buildUrlSearchParams(request);
        const obs = this.httpClient.get<ApiResponseJson>(url, {
            observe: 'response',
            params
        });

        return this.handleTypedModelSearchResponse(request, obs);
    }

    protected buildUrlSearchParams(request: TypedModelSearchRequest): object {
        const params: any = super.buildUrlSearchParams(request);

        if (request.index) {
            params.index = request.index;
        }

        if (request.query) {
            params.query = request.query;
        }

        return params;
    }

    protected handleTypedModelSearchResponse(request: TypedModelSearchRequest, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientTypedModelSearchResponse<T>> {
        return super.handleResponse(obs).pipe(
            map((response: ClientApiResponse) => {
                return this.buildTypedModelSearchResponse(request, response);
            })
        );
    }

    protected buildTypedModelSearchResponse(request: TypedModelSearchRequest, response: ClientApiResponse): ClientTypedModelSearchResponse<T> {
        return new ClientTypedModelSearchResponse<T>(response, request, this);
    }

}

// MARK: Internal
class ClientTypedModelSearchResponse<T extends UniqueModel> extends AbstractSearchServiceResponse<T> { }
