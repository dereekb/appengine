import { ClientModelServiceConfig } from '../../../client.service';
import { ClientApiResponse } from '../../../client';

import { AbstractSearchService, AbstractSearchServiceResponse, ModelSearchResponse, SearchRequest } from '../search.service';

import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { ApiResponseJson } from '../../../../api';
import { HttpResponse } from '@angular/common/http';
import { map } from 'rxjs/operators';

// MARK: Generic Interfaces
export type QueryRequest = SearchRequest;

export type QueryResponse<T extends UniqueModel> = ModelSearchResponse<T>;

export interface QueryService<T extends UniqueModel> {

    query(request: QueryRequest): Observable<QueryResponse<T>>;

}

// MARK: Client Interfaces
export class ClientQueryService<T extends UniqueModel, O> extends AbstractSearchService<T, O> implements QueryService<T> {

    constructor(config: ClientModelServiceConfig<T, O>) {
        super(config);
    }

    // MARK: ClientQueryService
    public query(request: QueryRequest): Observable<ClientQueryResponse<T>> {
        const url = this.rootPath + '/' + this.type + '/query';

        const params: {} = this.buildUrlSearchParams(request);
        const obs = this.httpClient.get<ApiResponseJson>(url, {
            observe: 'response',
            params
        });

        return this.handleQueryResponse(request, obs);
    }

    protected handleQueryResponse(request: QueryRequest, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientQueryResponse<T>> {
        return super.handleResponse(obs).pipe(
            map((response: ClientApiResponse) => {
                return this.buildQueryResponse(request, response);
            })
        );
    }

    protected buildQueryResponse(request: QueryRequest, response: ClientApiResponse): ClientQueryResponse<T> {
        return new ClientQueryResponse<T>(response, request, this);
    }

}

// MARK: Internal
class ClientQueryResponse<T extends UniqueModel> extends AbstractSearchServiceResponse<T> { }
