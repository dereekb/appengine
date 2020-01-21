import { ClientApiResponse, ClientApiResponseError, MissingExpectedResponseData, ClientModelSerializer, RawClientResponseAccessor } from './client';
import { ApiResponseError, ApiResponseData, ApiResponseJson } from '../api';
import { HttpResponse, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { ModelKey } from '@gae-web/appengine-utility';
import { ApiModuleRouteConfiguration } from '../api.config';

export abstract class ClientServiceConfig {
    readonly httpClient: HttpClient;
    readonly routeConfig: ApiModuleRouteConfiguration;
}

export abstract class ClientModelServiceConfig<T, O> extends ClientServiceConfig {
    readonly type: string;
    readonly serializer: ClientModelSerializer<T, O>;
}

// MARK: Client Service
export abstract class AbstractClientService {

    constructor(private _clientServiceConfig: ClientServiceConfig) { }

    // MARK: Accessors
    protected get rootPath() {
        return this._clientServiceConfig.routeConfig.root;
    }

    protected get httpClient() {
        return this._clientServiceConfig.httpClient;
    }

    // MARK: HttpResponse<ApiResponseJson> Handler
    protected handleResponse(obs: Observable<HttpResponse<ApiResponseJson>>): Observable<ClientApiResponse> {
        return obs.pipe(
            // Here as a legacy option. HttpErrorResponse is the new response.
            catchError((e) => this.catchHttpResponseError(e)),
            catchError((e) => this.catchHttpErrorResponseError(e)),
            catchError((e) => this.catchClientApiResponseError(e)),
            map((x) => this.convertToClientApiResponse(x))
        );
    }

    protected catchHttpResponseError(error: HttpResponse<ApiResponseJson> | any): Observable<any> {
        if (error instanceof HttpResponse) {
            const response = this.convertToClientApiResponse(error);
            const clientError = new ClientApiResponseError(response);
            return throwError(clientError);
        } else {
            return throwError(error);
        }
    }

    protected catchHttpErrorResponseError(error: HttpErrorResponse | any): Observable<any> {
        if (error instanceof HttpErrorResponse) {
            const response = this.convertErrorToClientApiResponse(error);
            const clientError = new ClientApiResponseError(response);
            return throwError(clientError);
        } else {
            return throwError(error);
        }
    }

    protected catchClientApiResponseError(error: ClientApiResponseError | any) {
        if (error instanceof ClientApiResponseError) {
            const response = error.response;
            const newError = this.handleClientApiResponseError(response);
            return throwError(newError || error);
        } else {
            return throwError(error);
        }
    }

    protected handleClientApiResponseError(response: ClientApiResponse): any | undefined {
        return throwError(response);
    }

    protected convertToClientApiResponse(response: HttpResponse<ApiResponseJson>): ClientApiResponse {
        return ClientApiResponse.fromResponse(response);
    }

    protected convertErrorToClientApiResponse(response: HttpErrorResponse): ClientApiResponse {
        return ClientApiResponse.fromErrorResponse(response);
    }

}

// MARK: Client Model Service
export abstract class AbstractClientModelService<T, O> extends AbstractClientService {

    constructor(protected _clientConfig: ClientModelServiceConfig<T, O>) {
        super(_clientConfig);
    }

    // MARK: Accessors
    protected get type() {
        return this._clientConfig.type;
    }

    // MARK: Serialize Results
    public serializedModelsFromResponse(data: ApiResponseData): T[] {
        return this.serializedModels(data.data);
    }

    public serializedDtosFromResponse(data: ApiResponseData): O[] {
        return this.serializedModelDtos(data.data);
    }

    public serializedModels(json: any): T[] {
        const output: O[] = this.serializedModelDtos(json);
        return this._clientConfig.serializer.convertArrayToModels(output);
    }

    public serializedModelDtos(json: any): O[] {
        return this._clientConfig.serializer.convertJsonToDtoArray(json);
    }

    // MARK: Serialize Errors
    public serializeKeysFromError(error: ApiResponseError): ModelKey[] {
        return ClientModelUtility.serializeKeys(error);
    }

    public serializeKeysFromResponse(data: ApiResponseData): ModelKey[] {
        return ClientModelUtility.serializeKeys(data.data);
    }

    public serializeKeys(json: any): ModelKey[] {
        return ClientModelUtility.serializeKeys(json);
    }

}

export class ClientModelUtility {

    public static serializeKeysFromError(error: ApiResponseError): ModelKey[] {
        let data = error.data as any;

        if (data.resources) {
            data = data.resources;
        }

        return ClientModelUtility.serializeKeys(data);
    }

    public static serializeKeysFromResponse(data: ApiResponseData): ModelKey[] {
        return ClientModelUtility.serializeKeys(data.data);
    }

    public static serializeKeys(json: any): ModelKey[] {
        if (Array.isArray(json)) {
            return json as ModelKey[];
        } else {
            return [json];
        }
    }

}

export class ClientModelResponse<T> extends RawClientResponseAccessor {

    private _models: T[];

    constructor(response: ClientApiResponse, private _service: AbstractClientModelService<T, {}>) {
        super(response);
    }

    get models() {
        return this.getModels();
    }

    protected getModels() {
        if (!this._models) {
            const data: ApiResponseData | undefined = this.raw.response.data;
            let models;

            if (data) {
                models = this._service.serializedModelsFromResponse(data);
            } else if (this.raw.success) {
                throw new MissingExpectedResponseData('No model data was returned where expected.');
            } else {
                models = [];
            }

            this._models = models;
        }

        return this._models;
    }

    protected serializeKeysResponse() {
        const data: ApiResponseData | undefined = this.raw.response.data;

        if (data) {
            return this._service.serializeKeysFromResponse(data);
        } else {
            throw new MissingExpectedResponseData('No data was returned from server where expected.');
        }
    }

}
