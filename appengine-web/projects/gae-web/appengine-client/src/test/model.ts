import { of, Observable } from 'rxjs';
import { ReadRequest, ReadService, ReadResponse } from '@gae-web/appengine-api';
import { ValueUtility, ModelKey, NumberModelKey } from '@gae-web/appengine-utility';

export class TestReadService<T> implements ReadService<T> {

  private _filteredKeysSet: Set<ModelKey>;

  constructor(public readonly type: string, private makeFn: (key: ModelKey) => T) {
    this.filteredKeysSet = undefined;
  }

  get filteredKeysSet(): Set<ModelKey> {
    return this._filteredKeysSet;
  }

  set filteredKeysSet(set: Set<ModelKey> | undefined) {
    this._filteredKeysSet = set || new Set<ModelKey>();
  }

  // ReadService
  read(request: ReadRequest): Observable<ReadResponse<T>> {
    const modelKeys = ValueUtility.normalizeArray(request.modelKeys);
    const models = modelKeys.filter((x) => !this._filteredKeysSet.has(x)).map((x: NumberModelKey) => {
      const foo = this.makeFn(x);

      // TODO: Initialize any other way?

      return foo;
    });

    return of({
      models,
      failed: []
    });
  }

}
