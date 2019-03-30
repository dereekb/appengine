import { of, Observable } from 'rxjs';
import { ValueUtility, ModelKey, NumberModelKey } from '@gae-web/appengine-utility';
import { ReadService, ReadResponse, ReadRequest } from '../lib/model/crud/read.service';

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
    const keysSeparation = ValueUtility.separateValues(modelKeys, (x) => !this._filteredKeysSet.has(x));

    const models = keysSeparation.included.map((x: NumberModelKey) => {
      const foo = this.makeFn(x);

      return foo;
    });

    return of({
      models,
      failed: keysSeparation.excluded
    });
  }

}
