import { Observable } from 'rxjs';
import { ModelKey } from '@gae-web/appengine-utility/lib/model';

export type LinkName = string;

export type LinkChangeAction = 'add' | 'remove' | 'set' | 'clear';

export interface LinkChange {

    readonly id?: string;
    readonly action: LinkChangeAction;
    readonly primaryKey: ModelKey;
    readonly linkName: LinkName;
    readonly targetKeys: ModelKey[];    // Max of 50

    // PrimaryModelBefore
    readonly primaryModelBefore?: any;

}

// MARK: Generic Interfaces
export interface LinkRequest {
    readonly isAtomic?: boolean;
    readonly type: string;
    readonly changes: LinkChange[];
}

export interface LinkResponse {
    readonly missing: ModelKey[];

    readonly successful: string[];
    readonly failed: string[];
}

export interface LinkService {
    updateLinks(request: LinkRequest): Observable<LinkResponse>;
}
