import { of, Observable } from 'rxjs';
import { ValueUtility, ModelKey, NumberModelKey, UniqueModel, ModelUtility } from '@gae-web/appengine-utility';
import { ReadService, ReadResponse, ReadRequest } from '../lib/model/crud/read.service';
import { QueryService, ModelSearchResponse, SearchRequest } from '../public-api';
import { delay } from 'rxjs/operators';

export class TestReadService<T> implements ReadService<T> {

  public loadingTime?: number;
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

    const obs = of({
      models,
      failed: keysSeparation.excluded
    });

    if (this.loadingTime) {
      return obs.pipe(
        delay(this.loadingTime)
      );
    } else {
      return obs;
    }
  }

}

export class TestQueryService<T extends UniqueModel> implements QueryService<T> {

  public cursor = 'abcd=';

  private _keyResults: ModelKey[];
  private _modelResults: T[];

  get keyResults() {
    return this._keyResults;
  }

  set keyResults(keyResults) {
    this._keyResults = keyResults;
  }

  get modelResults() {
    return this._modelResults;
  }

  set modelResults(modelResults) {
    this._modelResults = modelResults;

    if (modelResults) {
      this.keyResults = ModelUtility.readModelKeys(modelResults);
    }
  }

  query(request: SearchRequest): Observable<ModelSearchResponse<T>> {

    let keyResults: ModelKey[] = this._keyResults;
    let modelResults: T[];

    if (request.isKeysOnly === false) {
      modelResults = this._modelResults;
    }

    if (request.limit) {
      keyResults = keyResults.slice(0, request.limit);

      if (modelResults) {
        modelResults = modelResults.slice(0, request.limit);
      }
    }

    if (request.cursor) {
      // When a cursor is passed, we're out of elements.
      modelResults = [];
      keyResults = [];
    }

    const response: ModelSearchResponse<T> = {
      isKeysOnlyResponse: request.isKeysOnly,
      modelResults,
      cursor: this.cursor,
      keyResults
    };

    return of(response);
  }

}
