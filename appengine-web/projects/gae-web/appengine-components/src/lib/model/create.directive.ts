import { CreateResponse, CreateRequestOptions, CreateService, CreateRequest } from '@gae-web/appengine-api';
import { UniqueModel } from '@gae-web/appengine-utility';
import { Observable } from 'rxjs';
import { TemplateActionDirectiveEvent, TemplateActionDirective, TemplateActionConfig, AbstractTemplateActionDirective } from './template.directive';
import { ActionState } from '../shared/action';

export interface CreateActionDirectiveEvent<T extends UniqueModel> extends TemplateActionDirectiveEvent<T> {

  // Create response, only available on complete.
  readonly result?: CreateResponse<T>;

}

export interface CreateActionDirective<T extends UniqueModel> extends TemplateActionDirective<T> {

  readonly stream: Observable<CreateActionDirectiveEvent<T>>;

  doCreate(config: CreateActionConfig<T>): Observable<CreateResponse<T>>;

}

export interface CreateActionConfig<T> extends TemplateActionConfig<T> {
  readonly overrideOptions?: CreateRequestOptions;
}

export class AbstractCreateActionDirective<T extends UniqueModel> extends AbstractTemplateActionDirective<CreateActionDirectiveEvent<T>, T> implements CreateActionDirective<T> {

  public createOptions?: CreateRequestOptions;

  constructor(private _service: CreateService<T>) {
    super();
  }

  public doCreate(config: CreateActionConfig<T>): Observable<CreateResponse<T>> {
    // Create response returned by makeCreateObservable() result.
    return this.doTemplateAction(config) as Observable<CreateResponse<T>>;
  }

  protected makeCreateObservable(config: CreateActionConfig<T>): Observable<CreateResponse<T>> {
    const request = this.makeCreateRequest(config.templates, config.overrideOptions || this.createOptions);
    return this._service.create(request);
  }

  protected makeCreateRequest(templates: T | T[], options?: CreateRequestOptions): CreateRequest<T> {
    const request: CreateRequest<T> = {
      templates,
      options
    };

    return request;
  }

  // MARK: Template
  protected makeEventForResponse(response: any): CreateActionDirectiveEvent<T> {
    return {
      state: ActionState.Complete,
      result: response
    };
  }

  protected makeTemplateActionObservable(templateConfig: TemplateActionConfig<T>): Observable<CreateResponse<T>> {
    return this.makeCreateObservable(templateConfig as CreateActionConfig<T>);
  }

}
