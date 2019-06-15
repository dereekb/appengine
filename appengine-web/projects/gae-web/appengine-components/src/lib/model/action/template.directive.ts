import { UniqueModel } from '@gae-web/appengine-utility';
import { Observable } from 'rxjs';
import { AbstractActionDirective, ProvideActionDirective } from '../../shared/action.directive';
import { TemplateResponse } from '@gae-web/appengine-api';
import { ActionObject, ActionEvent, ActionFactory } from '../../shared/action';
import { Type, Provider } from '@angular/core';

export interface TemplateActionDirectiveEvent<T extends UniqueModel> extends ActionEvent {
  readonly result?: TemplateResponse<T>;
}

export function ProvideTemplateActionDirective<S extends TemplateActionDirective<any>>(directiveType: Type<S>): Provider[] {
  return [...ProvideActionDirective(directiveType), { provide: TemplateActionDirective, useExisting: directiveType }];
}

export abstract class TemplateActionDirective<T extends UniqueModel> extends ActionObject {
  readonly stream: Observable<TemplateActionDirectiveEvent<T>>;
  abstract doTemplateAction(config: TemplateActionConfig<T>): Observable<TemplateResponse<T>>;
}

export interface TemplateActionConfig<T> {
  readonly templates: T | T[];
}

export abstract class AbstractTemplateActionDirective<E extends TemplateActionDirectiveEvent<T>, T extends UniqueModel>
  extends AbstractActionDirective<E> implements TemplateActionDirective<T> {

  constructor() {
    super();
  }

  public doTemplateAction(config: TemplateActionConfig<T>): Observable<TemplateResponse<T>> {
    const action: ActionFactory<TemplateResponse<T>> = () => {
      const templateObs = this.makeTemplateActionObservable(config);
      return templateObs;
    };

    return this.doAction(action, (r) => this.makeEventForResponse(r));
  }

  protected abstract makeEventForResponse(response: TemplateResponse<T>): E;

  protected abstract makeTemplateActionObservable(templateConfig: TemplateActionConfig<T>): Observable<TemplateResponse<T>>;

}
