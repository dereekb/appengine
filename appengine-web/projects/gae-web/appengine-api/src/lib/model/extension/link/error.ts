import { ApiResponseError, ApiResponseErrorInfo } from '../../../api';

import { ClientApiResponse, ClientApiResponseErrorType, ClientApiResponseError } from '../../client';
import { LinkChange, LinkChangeAction, LinkName } from './link';
import { ModelKey } from '@gae-web/appengine-utility';

// MARK: Atomic Error
export const CLIENT_LINK_SERVICE_CHANGE_ERROR_CODE = 'LINK_CHANGE_ERROR_SET';
export type ClientLinkErrorCode = 'LINK_UNAVAILABLE' | 'CHANGE_FAILURE';

export enum LinkErrorReason {
    LinkUnavailable,
    ChangeFailure,
    UnknownReason
}

export class ClientLinkServiceChangeError extends ClientApiResponseError {

    // MARK: Assertions
    public static tryMakeClientLinkSystemChangeError(type: string, response: ClientApiResponse): Error | undefined {
        if (response.error.type === ClientApiResponseErrorType.BadResponse) {
            const errorSet = this.trySerializeClientLinkSystemChangeErrorSet(type, response);

            if (errorSet) {
                return new ClientLinkServiceChangeError(response, errorSet);
            }
        }

        return undefined;
    }

    // MARK: Serialization
    public static trySerializeClientLinkSystemChangeErrorSet(type: string, response: ClientApiResponse): ClientLinkSystemChangeErrorSet | undefined {
        let errorSet: ClientLinkSystemChangeErrorSet | undefined;

        const error: ApiResponseError = response.error.getError(CLIENT_LINK_SERVICE_CHANGE_ERROR_CODE);

        if (error) {
            errorSet = this.serializeClientLinkSystemChangeErrorSet(type, error);
        }

        return errorSet;
    }

    public static serializeClientLinkSystemChangeErrorSet(type: string, error: ApiResponseError): ClientLinkSystemChangeErrorSet {

        const linkSystemErrors: LinkSystemChangeApiResponseError[] = error.data;

        const errors: ClientLinkSystemChangeError[] = linkSystemErrors.map((linkSystemError) => {
            const change: LinkSystemChange = LinkSystemChange.make(type, linkSystemError);
            const reason = this.readLinkErrorReason(linkSystemError.code);
            return new ClientLinkSystemChangeError(change, reason);
        });

        return new ClientLinkSystemChangeErrorSet(type, errors);
    }

    public static readLinkErrorReason(code: ClientLinkErrorCode): LinkErrorReason {
        switch (code) {
            case 'LINK_UNAVAILABLE':
                return LinkErrorReason.LinkUnavailable;
            case 'CHANGE_FAILURE':
                return LinkErrorReason.ChangeFailure;
            default:
                return LinkErrorReason.UnknownReason;
        }
    }

    constructor(response: ClientApiResponse, public readonly _errorSet: ClientLinkSystemChangeErrorSet) {
        super(response, 'One or more errors occured while linking.');
    }

}

// MARK: Errors
export interface LinkSystemChangeApiResponseError extends LinkChange, ApiResponseErrorInfo {
    readonly code: ClientLinkErrorCode;
}

class LinkSystemChange implements LinkChange {

    static make(type: string, linkChange: LinkChange): LinkSystemChange {
        return new LinkSystemChange(type, linkChange.action, linkChange.primaryKey, linkChange.linkName, linkChange.targetKeys);
    }

    constructor(readonly type: string, readonly action: LinkChangeAction, readonly primaryKey: ModelKey, readonly linkName: LinkName, readonly targetKeys: ModelKey[]) { }

}

class ClientLinkSystemChangeError implements ClientLinkSystemChangeError {

    constructor(readonly change: LinkSystemChange, readonly reason: LinkErrorReason) { }

}

class ClientLinkSystemChangeErrorSet implements ClientLinkSystemChangeErrorSet {

    constructor(readonly type: string, readonly errors: ClientLinkSystemChangeError[]) { }

}

