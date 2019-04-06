import { UniqueModel, ModelKey } from '@gae-web/appengine-utility';
import { DeleteResponse, DeleteRequestOptions, DeleteService, DeleteRequest } from '@gae-web/appengine-api';
import { Observable } from 'rxjs';
import { AbstractActionDirective } from '../shared/action.directive';
import { ActionFactory, ActionObject, ActionEvent, ActionState } from '../shared/action';

export interface DeleteActionDirectiveEvent<T extends UniqueModel> extends ActionEvent {

  // Delete response, only available on complete.
  readonly result?: DeleteResponse<T>;

}

export interface DeleteActionDirective<T extends UniqueModel> extends ActionObject {

  readonly stream: Observable<DeleteActionDirectiveEvent<T>>;

  doDelete(config: DeleteActionConfig<T>): Observable<DeleteResponse<T>>;

}

export interface DeleteActionConfig<T> {
  readonly keys: ModelKey | ModelKey[];
  readonly overrideOptions?: DeleteRequestOptions;
}

export class AbstractDeleteActionDirective<T extends UniqueModel> extends AbstractActionDirective<DeleteActionDirectiveEvent<T>> implements DeleteActionDirective<T> {

  public deleteOptions?: DeleteRequestOptions;

  constructor(private _service: DeleteService<T>) {
    super();
  }

  public doDelete(config: DeleteActionConfig<T>): Observable<DeleteResponse<T>> {
    const action: ActionFactory<DeleteResponse<T>> = () => {
      const deleteObs = this.makeDeleteObservable(config);
      return deleteObs;
    };

    return this.doAction(action, (success) => {
      return {
        state: ActionState.Complete,
        result: success
      };
    });
  }

  protected makeDeleteObservable(config: DeleteActionConfig<T>): Observable<DeleteResponse<T>> {
    const request = this.makeDeleteRequest(config.keys, config.overrideOptions || this.deleteOptions);
    return this._service.delete(request);
  }

  protected makeDeleteRequest(keys: ModelKey | ModelKey[], options?: DeleteRequestOptions): DeleteRequest {
    const request: DeleteRequest = {
      modelKeys: keys,
      options
    };

    return request;
  }

}
