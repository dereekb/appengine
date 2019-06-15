import { Observable, of, empty } from 'rxjs';
import { Input } from '@angular/core';
import { UniqueModel, ModelOrKey, ModelKey, ModelUtility } from '@gae-web/appengine-utility';
import { map, filter } from 'rxjs/operators';

/**
 * Abstract update component that takes in either a model or a key and
 * outputs a model key to the contained view.
 *
 * This is used primarily by a model's concrete create, update, or delete
 * views that take in a single model or key as a target for the edit.
 */
export abstract class AbstractModelChangeFormComponent<T extends UniqueModel> {

  private _output: Observable<ModelKey>;

  @Input()
  public set target(input: ModelOrKey<T>) {
    if (input) {
      this.targetObs = of(input);
    }
  }

  @Input()
  public set targetObs(input: Observable<ModelOrKey<T>>) {
    if (!input) {
      input = empty();
    }

    this._output = input.pipe(
      map((x) => ModelUtility.readModelKey(x)),
      filter((x) => ModelUtility.isInitializedModelKey(x))
    );
  }

  public get targetKey(): Observable<ModelKey> {
    return this._output || empty();
  }

}
