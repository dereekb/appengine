import { Injectable } from '@angular/core';

import { ClientAtomicOperationError } from '../../crud/error';
import { ClientLinkServiceChangeError } from './error';
import { AbstractClientService, ClientServiceConfig } from '../../client.service';
import { ClientApiResponse, ClientApiResponseError, RawClientResponseAccessor } from '../../client';

import { Observable, throwError } from 'rxjs';
import { ApiResponseJson } from '../../../api';
import { ModelUtility, ModelKey } from '@gae-web/appengine-utility';
import { catchError, map } from 'rxjs/operators';
import { HttpResponse } from '@angular/common/http';
import { LinkRequest, LinkChange, LinkResponse } from './link';

export abstract class LinkService {
    abstract updateLinks(request: LinkRequest): Observable<LinkResponse>;
}

// MARK: Client
@Injectable()
export class ClientLinkService extends AbstractClientService implements LinkService {

    constructor(config: ClientServiceConfig) {
        super(config);
    }

    // MARK: ClientLinkService
    public updateLinks(request: LinkRequest): Observable<ClientLinkResponse> {
        if (this.isValidLinkRequest(request)) {
            const url = this.rootPath + '/' + request.type + '/link';

            const body = this.makeRequestData(request);

            const obs = this.httpClient.put<ApiResponseJson>(url, body, {
                observe: 'response'
            });

            return this.handleLinkResponse(request, obs);
        } else {
            return throwError(new Error('Invalid changes were requested.'));
        }
    }

    protected isValidLinkRequest(request: LinkRequest) {
        if (!request.changes || request.changes.length === 0) {
            return false;   // Must have atleast 1 change provided.
        }

        // Validate all changes
        const invalidChanges = request.changes.filter((x) => !this.isValidLinkChange(x));
        return invalidChanges.length === 0;
    }

    protected isValidLinkChange(change: LinkChange) {

        // Must have a primary key.
        if (!ModelUtility.isInitializedModelKey(change.primaryKey)) {
            return false;
        }

        switch (change.action) {
            case 'add':
            case 'remove':
            case 'set':
                // Must have target keys. Use clear otherwise..?
                return change.targetKeys.length > 0;
            case 'clear':
                return true;
            default:
                return false;
        }
    }

    protected makeRequestData(request: LinkRequest): {} {
        const atomic = (request.isAtomic === undefined) ? true : request.isAtomic;
        const data = request.changes.map((change: LinkChange) => {
            return {
                action: change.action,
                primaryKey: change.primaryKey,
                linkName: change.linkName,
                targetKeys: change.targetKeys
            };
        });

        return {
            atomic,
            data
        };
    }

    protected handleLinkResponse(request: LinkRequest, obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientLinkResponse> {
        return obs.pipe(
            catchError((e) => this.catchHttpResponseError(e)),
            catchError((e) => this.catchLinkClientApiResponseError(request, e)),
            map((response: ClientApiResponse) => {
                return this.buildLinkResponse(request, response);
            })
        );
    }

    protected catchLinkClientApiResponseError(request: LinkRequest, error: ClientApiResponseError | any) {
        if (error instanceof ClientApiResponseError) {
            const response = error.response;
            const newError = this.handleLinkClientApiResponseError(request, response);
            return throwError(newError || error);
        } else {
            return throwError(error);
        }
    }

    protected handleLinkClientApiResponseError(request: LinkRequest, response: ClientApiResponse): any | undefined {
        return ClientAtomicOperationError.tryMakeAtomicOperationError(response) || ClientLinkServiceChangeError.tryMakeClientLinkSystemChangeError(request.type, response);
    }

    protected buildLinkResponse(request: LinkRequest, response: ClientApiResponse): ClientLinkResponse {
        return new ClientLinkResponse(request, response, this);
    }

}

// MARK: Internal
export class ClientLinkResponse extends RawClientResponseAccessor implements LinkResponse {

    private _missing: ModelKey[];

    private _successful: string[];
    private _failed: string[];

    constructor(private _request: LinkRequest, response: ClientApiResponse, private _service: ClientLinkService) {
        super(response);
    }

    public get successful(): string[] {
        if (!this._successful) {
            this._successful = this.raw.response.data.raw.successful;
        }

        return this._successful;
    }

    public get failed(): string[] {
        if (!this._failed) {
            this._failed = this.raw.response.data.raw.failed;
        }

        return this._failed;
    }

    public get missing(): ModelKey[] {
        if (!this._missing) {
            this._missing = ClientAtomicOperationError.serializeMissingResourceKeys(this.raw);
        }

        return this._missing;
    }

    // TODO: Get errors set.

}
