import { ModelKey } from '../../../datastore/modelkey';

import { ClientApiResponse } from '../client';

export interface ModelServiceResponse<T> extends ModelServiceModelsResponse<T>, ModelServiceFailedKeysResponse {

}

export interface ModelServiceModelsResponse<T> {

    readonly models: T[];

}

export interface ModelServiceFailedKeysResponse {

    readonly failed: ModelKey[];

}
