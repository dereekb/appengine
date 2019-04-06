import { Observable } from 'rxjs';
import { LinkRequest, LinkResponse, ClientLinkService } from '@gae-web/appengine-api';
import { AbstractActionDirective } from '../shared/action.directive';
import { ActionFactory, ActionObject, ActionEvent, ActionState } from '../shared/action';

// MARK: Link Action
export interface LinkActionDirective extends ActionObject {

  readonly stream: Observable<LinkActionDirectiveEvent>;

  doLinkAction(request: LinkRequest): Observable<LinkResponse>;

}

export interface LinkActionDirectiveEvent extends ActionEvent {

  readonly request: LinkRequest;
  readonly result?: LinkResponse;

}

export abstract class AbstractLinkActionDirective
  extends AbstractActionDirective<LinkActionDirectiveEvent> implements LinkActionDirective {

  constructor(protected readonly service: ClientLinkService) {
    super();
  }

  public doLinkAction(request: LinkRequest): Observable<LinkResponse> {
    const action: ActionFactory<LinkResponse> = () => {
      const linkObs = this.makeLinkActionObservable(request);
      return linkObs;
    };

    return this.doAction(action, (r) => this.makeEventForResponse(request, r));
  }

  protected makeLinkActionObservable(request: LinkRequest): Observable<LinkResponse> {
    return this.service.updateLinks(request);
  }

  protected makeEventForResponse(request: LinkRequest, response: LinkResponse): LinkActionDirectiveEvent {
    return {
      state: ActionState.Complete,
      request,
      result: response
    };
  }

}
