import { ClientLinkService, LinkService, LinkRequest, LinkResponse, ClientLinkResponse } from '@gae-web/appengine-api';
import { ModelServiceWrapperSet } from './model.service';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { ModelKey } from '@gae-web/appengine-utility';
import { WrapperEventType } from './wrapper';

// MARK: Links
export class ModelLinkService implements LinkService {

  constructor(private _parent: ModelServiceWrapperSet, private _linkService: ClientLinkService) { }

  // MARK: Link Service
  public updateLinks(request: LinkRequest): Observable<ClientLinkResponse> {

    // TODO: Try get before models if they aren't linked?

    return this._linkService.updateLinks(request).pipe(
      tap((response) => {
        const type: string = request.type;
        const wrapper = this._parent.getWrapper(type);

        // Remove all primary keys from the cache.
        if (wrapper) {
          const keys: ModelKey[] = request.changes.map((x) => x.primaryKey);

          // Remove models from the cache.
          wrapper.cache.removeAll(keys);

          // Announce link event.
          wrapper.next({
            eventType: WrapperEventType.ModelLink,
            request,
            response,
            keys
          });
        }
      })
    );
  }

}
