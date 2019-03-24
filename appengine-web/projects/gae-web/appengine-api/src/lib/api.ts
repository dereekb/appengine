
export type ApiErrorCode = string;

// Request
/**
 * Base API request for a Appengine server.
 */
export class ApiRequest<T> {

    constructor(public data: T[]) { }

    public setDataElement(element: T) {
        this.data = [element];
    }

}

// MARK: Response
export interface ApiResponseJson {
    success: boolean;
    data?: any;
    included?: any;
    errors?: any;
}

/**
 * Base API response from an Appengine server.
 */
export class ApiResponse {

    public static fromJson(json: ApiResponseJson): ApiResponse {
        const success = json.success;

        let data: ApiResponseData | undefined;
        let included: Map<ApiErrorCode, ApiResponseData> | undefined;
        let errors: ApiResponseErrorSet | undefined;

        if (json.data) {
            data = ApiResponseData.fromJson(json.data);
        }

        if (json.included) {
            included = ApiResponseData.mapFromJson(json.included);
        }

        if (json.errors) {
            errors = ApiResponseErrorSet.fromJson(json.errors);
        }

        return new ApiResponse(success, data, included, errors);
    }

    constructor(private _success = true, private _data?: ApiResponseData,
        private _included: Map<string, ApiResponseData> = new Map<string, ApiResponseData>(),
        private _errors: ApiResponseErrorSet = new ApiResponseErrorSet()) { }

    get success(): boolean {
        return this._success;
    }

    get data(): ApiResponseData | undefined {
        return this._data;
    }

    get included(): Map<string, ApiResponseData> {
        return this._included;
    }

    get errors(): ApiResponseErrorSet {
        return this._errors;
    }

}

export class ApiResponseData {

    public static mapFromJson(json: any): Map<string, ApiResponseData> {
        const map = new Map<string, ApiResponseData>();

        Object.keys(json).forEach((key) => {
            const dataJson = json[key];
            const responseData = ApiResponseData.fromJson(dataJson);
            map.set(key, responseData);
        });

        return map;
    }

    public static fromJson(json: any): ApiResponseData {
        return new ApiResponseData(json);
    }

    constructor(private readonly _raw: any) { }

    public get type(): string {
        return this._raw.type;
    }

    get data(): any {
        return this._raw.data;
    }

    get raw(): any {
        return this._raw;
    }

}

export class ApiResponseErrorSet {

    private _map: Map<ApiErrorCode, ApiResponseError>;

    public static fromJson(json: any): ApiResponseErrorSet {
        let errors: ApiResponseError[] | undefined;

        if (Array.isArray(json)) {
            errors = json.map((errorJson) => {
                return ApiResponseError.fromJson(errorJson);
            });
        }

        return new ApiResponseErrorSet(errors);
    }

    constructor(private _errors: ApiResponseError[] = []) { }

    get errors(): ApiResponseError[] {
        return this._errors;
    }

    get errorsMap(): Map<ApiErrorCode, ApiResponseError> {
        if (!this._map) {
            this._map = new Map<ApiErrorCode, ApiResponseError>();

            this._errors.forEach((error) => {
                const key = error.code;
                this._map.set(key, error);
            });
        }

        return this._map;
    }

    public getError(code: ApiErrorCode): ApiResponseError | undefined {
        return this.errorsMap.get(code);
    }

}

export class ApiResponseErrorInfo {

    constructor(private _code: ApiErrorCode, private _title: string, private _detail: string = '') { }

    get code(): ApiErrorCode {
        return this._code;
    }

    get title(): string {
        return this._title;
    }

    get detail(): string {
        return this._detail;
    }

}

export class ApiResponseError extends ApiResponseErrorInfo {

    public static listFromJson(json: any): ApiResponseError[] {
        let result: ApiResponseError[];

        if (Array.isArray(json)) {
            result = json.map((errorJson) => {
                return ApiResponseError.fromJson(errorJson);
            });
        } else {
            result = [];
        }

        return result;
    }

    public static fromJson(json: any): ApiResponseError {
        return new ApiResponseError(json.code, json.title, json.detail, json.data);
    }

    constructor(code: ApiErrorCode, title: string, detail: string = '', private _data?: any) {
        super(code, title, detail);
    }

    get data(): any | undefined {
        return this._data;
    }

}
