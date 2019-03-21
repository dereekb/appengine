
export interface Keyed<T> {

  readonly key: T | undefined;

}

export interface AlwaysKeyed<T> extends Keyed<T> {

  readonly key: T;

}

export enum SortDirection {
  Ascending,
  Descending
}

/**
 * Map wrapper that can associate multiple values with a single key.
 */
export class CollectionMap<K, T> {

  constructor(private _map: Map<K, T[]> = new Map<K, T[]>()) { }

  public get map() {
    return this._map;
  }

  public add(key: K, value: T) {
    this.getOrMakeCollection(key).push(value);
  }

  public getOrMakeCollection(key: K) {
    let collection = this._map.get(key);

    if (!collection) {
      collection = this.makeAndAddCollection(key);
    }

    return collection;
  }

  protected makeAndAddCollection(key: K): T[] {
    const collection = this.makeNewCollection(key);
    this._map.set(key, collection);
    return collection;
  }

  protected makeNewCollection(key: K): T[] {
    return [];
  }

}
