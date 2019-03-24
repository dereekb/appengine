import { ModelKey } from '@gae-web/appengine-utility/lib/model';

export interface ModelServiceResponse<T> extends ModelServiceModelsResponse<T>, ModelServiceFailedKeysResponse {}

export interface ModelServiceModelsResponse<T> {
    readonly models: T[];
}

export interface ModelServiceFailedKeysResponse {
    readonly failed: ModelKey[];
}
