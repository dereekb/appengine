import { Observable } from 'rxjs';
import { UniqueModel } from '@gae-web/appengine-utility';
import { TemplateActionDirectiveEvent, TemplateActionDirective, TemplateActionConfig, AbstractTemplateActionDirective } from './template.directive';
import { UpdateResponse, UpdateRequestOptions, UpdateService, UpdateRequest } from '@gae-web/appengine-api';
import { ActionState } from '../shared/action';

export interface UpdateActionDirectiveEvent<T extends UniqueModel> extends TemplateActionDirectiveEvent<T> {

  // Update response, only available on complete.
  readonly result?: UpdateResponse<T>;

}

export interface UpdateActionDirective<T extends UniqueModel> extends TemplateActionDirective<T> {

  readonly stream: Observable<UpdateActionDirectiveEvent<T>>;

  doUpdate(config: UpdateActionConfig<T>): Observable<UpdateResponse<T>>;

}

export interface UpdateActionConfig<T> extends TemplateActionConfig<T> {
  readonly overrideOptions?: UpdateRequestOptions;
}

export class AbstractUpdateActionDirective<T extends UniqueModel> extends AbstractTemplateActionDirective<UpdateActionDirectiveEvent<T>, T> implements UpdateActionDirective<T> {

  public updateOptions?: UpdateRequestOptions;

  constructor(private _service: UpdateService<T>) {
    super();
  }

  public doUpdate(config: UpdateActionConfig<T>): Observable<UpdateResponse<T>> {
    // Update response returned by makeCreateObservable() result.
    return this.doTemplateAction(config) as Observable<UpdateResponse<T>>;
  }

  protected makeUpdateObservable(config: UpdateActionConfig<T>): Observable<UpdateResponse<T>> {
    const request = this.makeUpdateRequest(config.templates, config.overrideOptions || this.updateOptions);
    return this._service.update(request);
  }

  protected makeUpdateRequest(templates: T | T[], options?: UpdateRequestOptions): UpdateRequest<T> {
    const request: UpdateRequest<T> = {
      templates,
      options
    };

    return request;
  }

  // MARK: Template
  protected makeEventForResponse(response: any): UpdateActionDirectiveEvent<T> {
    return {
      state: ActionState.Complete,
      result: response
    };
  }

  protected makeTemplateActionObservable(templateConfig: TemplateActionConfig<T>): Observable<UpdateResponse<T>> {
    return this.makeUpdateObservable(templateConfig as UpdateActionConfig<T>);
  }

}
