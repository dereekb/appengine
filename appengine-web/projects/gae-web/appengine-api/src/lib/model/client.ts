import { ApiResponse, ApiErrorCode, ApiResponseError, ApiResponseErrorSet, ApiResponseJson } from '../api';

import { HttpResponse } from '@angular/common/http';

import { BaseError } from 'make-error';

export enum ClientApiResponseErrorType {

    None,

    Authentication,

    BadRequest,

    BadResponse,

    ServerError

}

export class ClientApiUtility {

    static errorTypeForHttpStatus(status: number): ClientApiResponseErrorType {
        let errorType: ClientApiResponseErrorType;

        if (status >= 500) {
            errorType = ClientApiResponseErrorType.ServerError;
        } else if (status >= 400) {
            if (status === 401 || status === 403) {
                errorType = ClientApiResponseErrorType.Authentication;
            } else if (status === 400) {
                errorType = ClientApiResponseErrorType.BadRequest;
            } else {
                errorType = ClientApiResponseErrorType.BadResponse;
            }
        } else if (status >= 200) {
            errorType = ClientApiResponseErrorType.None;
        } else {
            // Shouldn't really ever be here.
            throw new Error('Unknown error type encountered for status: ' + status);
        }

        return errorType;
    }

}

// MARK: implementation
export class RawClientResponseAccessor {

    constructor(private readonly _raw: ClientApiResponse) { }

    get raw(): ClientApiResponse {
        return this._raw;
    }

}

export class ClientResponse {

    constructor(private _success: boolean, private _status: number, private _json: any) { }

    get success(): boolean {
        return this._success;
    }

    get status(): number {
        return this._status;
    }

    get json(): any {
        return this._json;
    }

}

export class ClientApiResponseErrorSet extends ApiResponseErrorSet {

    constructor(private _errorType: ClientApiResponseErrorType, errorSet: ApiResponseErrorSet) {
        super(errorSet.errors);
     }

    get type(): ClientApiResponseErrorType {
        return this._errorType;
    }

}

export class ClientApiResponse extends ClientResponse {

    private _error: ClientApiResponseErrorSet;

    public static fromResponse(httpResponse: HttpResponse<ApiResponseJson>): ClientApiResponse {
        const data = httpResponse.body;
        const apiResponse: ApiResponse = ApiResponse.fromJson(data);
        return new ClientApiResponse(apiResponse, httpResponse);
    }

    private constructor(private _apiResponse: ApiResponse, _httpResponse: HttpResponse<any>) {
        super(_apiResponse.success, _httpResponse.status, _httpResponse.body);
    }

    get response() {
        return this._apiResponse;
    }

    get error() {
        if (!this._error) {
            const errorCode = ClientApiUtility.errorTypeForHttpStatus(this.status);
            this._error = new ClientApiResponseErrorSet(errorCode, this._apiResponse.errors);
        }

        return this._error;
    }

}

// MARK: Error
/**
 * Generic throwable error.
 */
export class ClientApiResponseError extends BaseError {

    constructor(public readonly response: ClientApiResponse, message: string = 'Request returned an error.') {
        super(message);
    }

}

// MARK: Utility
export interface ClientModelSerializer<T, O> {

    convertJsonToDtoArray(json: any): O[];

    convertArrayToDto(models: T[]): O[];

    convertArrayToModels(dtos: O[]): T[];

    convertJsonToDto(json: any): O;

    convertToDto(model: T): O;

    convertToModel(dto: O): T;

}

/**
 * Abstract implementation of a ClientModelSerializer.
 */
export abstract class AbstractClientModelSerializer<T, O> implements ClientModelSerializer<T, O> {

    public convertJsonToDtoArray(json: any): O[] {
        if (Array.isArray(json)) {
            return json.map((data) => this.convertJsonToDto(data));
        } else {
            return [this.convertJsonToDto(json)];
        }
    }

    public convertArrayToDto(models: T[]): O[] {
        return models.map((model) => this.convertToDto(model));
    }

    public convertArrayToModels(dtos: O[]): T[] {
        return dtos.map((dto) => this.convertToModel(dto));
    }

    public abstract convertJsonToDto(json: any): O;

    public abstract convertToDto(model: T): O;

    public abstract convertToModel(dto: O): T;

}
