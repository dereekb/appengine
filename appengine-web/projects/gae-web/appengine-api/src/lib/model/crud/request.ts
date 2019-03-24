import { ApiRequest } from '../../api';

/**
 * Atomic edit request.
 */
export class EditApiRequest<T> extends ApiRequest<T> {

    constructor(public atomic = true, data: T[]) {
        super(data);
    }

}
