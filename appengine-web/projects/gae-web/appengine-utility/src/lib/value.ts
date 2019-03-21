
export type OneOrMore<T> = T | T[];

export type ExistingObject = string | number | boolean | symbol | object;
export type PropertyKey = string | number;
export type MakePropertyKeyFunction<T> = (input: T) => PropertyKey;
export type MakeMapKeyFunction<K, T> = (input: T) => K | undefined;
export type IterateFunction<T> = (x: T, index: number) => any | undefined;

export interface UniqueArrayMap<T> {
  [key: string]: T;
}

export interface ObjectPropertiesReading {
  readonly values: any[];
  readonly missing: PropertyKey[];
}

export interface IArrayDelta<T> {
  kept: T[];
  added: T[];
  removed: T[];
}

export class ArrayDelta<T> implements ArrayDelta<T> {
  kept: T[] = [];
  added: T[] = [];
  removed: T[] = [];
}

export class ValueUtility {

  /**
   * Returns the input value, or executes it and passes the input args if it is a function.
   * @param value Value or Function to read the value from.
   * @param args Args to pass to the function, if applicable.
   */
  static readValue(value: any | (() => any), args: any) {
    let result;

    if (value instanceof Function) {
      result = value.apply(undefined, args);
    } else {
      result = value;
    }

    return result;
  }

  static getObjectProperties<T extends object>(object: T, properties: OneOrMore<PropertyKey>): any[] {
    return this.readObjectProperties(object, properties).values;
  }

  /**
   * Attempts to read the specified properties from the object.
   * @param object Object to read each of the properties from.
   * @param properties Properties to read from the object.
   */
  static readObjectProperties<T extends object>(object: T, properties: OneOrMore<PropertyKey>): ObjectPropertiesReading {
    const result: ObjectPropertiesReading = {
      values: [],
      missing: []
    };

    properties = this.normalizeArray(properties);

    properties.forEach((key: PropertyKey, index: number) => {
      const value: any | undefined = object[key];

      if (value !== undefined) {
        result.values.push(value);
      } else {
        result.missing.push(key);
      }
    });

    return result;
  }

  static normalizeArray<T>(object: OneOrMore<T>): T[] {
    if (!Array.isArray(object)) {
      if (this.isNotNullOrUndefined(object)) {
        object = [object];
      } else {
        object = [];
      }
    }

    return object;
  }

  // MARK: Special
  static isNotNullOrUndefined(object: any | null | undefined): boolean {
    return !this.isNullOrUndefined(object);
  }

  static isNullOrUndefined(object: any | null | undefined): boolean {
    return (object === undefined || object === null);
  }

  static isTruthy(object: any): boolean {
    return Boolean(object);
  }

  /**
   * Copies all object properties from one object and normalizes them into an array and onto the target object.
   */
  static copyArrayObjectProperties(to: object, from: object, properties: PropertyKey[], returnNull?: boolean) {
    properties.forEach((property) => {
      const array = from[property];
      to[property] = ValueUtility.normalizeMaybeArrayCopy(array, returnNull);
    });
  }


  static copyObjectProperties(to: object, from: object, properties: PropertyKey[], defaults: object = {}) {
    properties.forEach((property: PropertyKey) => {
      let value: any = from[property];

      if (value === undefined) {
        value = defaults[property];
      }

      to[property] = value;
    });
  }

  // this.getValuesFromObject = this.readObjectProperties;

  /**
   * Not really sure what this function is supposed to do.
   *
   * Will only return the 'object' if it is at the last index. Will return the default value otherwise...
   */
  static readObjectPropertyAt(object: object, keysArray: OneOrMore<PropertyKey>, defaultValue: any = null): any {
    keysArray = this.normalizeArray(keysArray);

    let currentObject = object;
    const lastIndex = (keysArray as Array<PropertyKey>).length - 1;

    return this.iterateWhileUndefined<PropertyKey>(keysArray, (key, index) => {
      currentObject = currentObject[key];

      if (ValueUtility.isObject(currentObject) === false) {
        return null;    // Return null if not an object, breaking the loop.
      } else if (index === lastIndex) {
        return currentObject;
      }

      return undefined;
    }) as any || defaultValue;
  }

  static asArray<T>(input: T | T[]): T[] {
    if (!Array.isArray(input)) {
      input = [input];
    }

    return input;
  }

  static takeFromArrays<T>(arrays: T[][], limit: number): T[] {
    let result: T[] = [];

    function getRemaining() {
      return limit - result.length;
    }

    let i = 0;

    while (i < arrays.length) {
      const remaining = getRemaining();

      // Check if any remaining.
      if (remaining === 0) {
        break;
      }

      const array = arrays[i];

      if (array.length > remaining) {
        // Concat only the remaining portion and break;
        const sub = array.slice(0, remaining);
        result = result.concat(sub);
        break;
      } else {
        // Concat the entire array.
        result = result.concat(array);
      }

      i += 1;
    }

    return result;
  }

  /**
   * Creates a new map from the input array and key function.
   */
  static mapFromArray<K, T>(input: T[], makeKeyFn: MakeMapKeyFunction<K, T>): Map<K, T> {
    const map: Map<K, T> = new Map();

    input.forEach((value: T) => {
      const key: K | undefined = makeKeyFn(value);

      if (key !== undefined) {
        map.set(key, value);
      }
    });

    return map;
  }

  /**
   * Takes the keys of the map and returns them within a new array.
   */
  static arrayFromMapKeys<K, V>(input: Map<K, V>): K[] {
    const array: K[] = [];

    input.forEach((_, key: K) => {
      array.push(key);
    });

    return array;
  }

  static mapKeysSet<K, V>(input: Map<K, V>): Set<K> {
    const set = new Set<K>();

    input.forEach((_, key: K) => {
      set.add(key);
    });

    return set;
  }

  /**
   * Converts a set to an array.
   */
  static setToArray<T>(input: Set<T>): T[] {
    const array: T[] = [];

    input.forEach((item) => {
      array.push(item);
    });

    return array;
  }

  static arrayToSet<T>(input: T[]): Set<T> {
    const set = new Set<T>();

    input.forEach((x) => {
      set.add(x);
    });

    return set;
  }

  static reduceArray<T>(array: T[][] | undefined): T[] {
    if (array && array.length) {
      return array.reduce((prev, curr) => {
        return prev.concat(curr);
      });
    } else {
      return [];
    }
  }

  static arrayDelta<T>(from: T[], to: T[]): IArrayDelta<T> {
    const toSet: Set<T> = this.arrayToSet(to);
    const visited = new Set<T>();

    const kept: T[] = [];
    const removed: T[] = [];
    let added: T[] = [];

    from.forEach((x) => {
      if (toSet.has(x)) {
        kept.push(x);
      } else {
        removed.push(x);
      }

      visited.add(x);
    });

    added = to.filter((x) => !visited.has(x));

    return {
      kept,
      added,
      removed
    };
  }

  /**
   * Map function for map-like objects that maps by each property.
   *
   * @param object Object to map from.
   * @param mapFn Values mapping function.
   * @param removeNulls Whether or not to remove nulls.
   */
  static mapObjectValues(object: object, mapFn: (key: string, value: any) => any, removeNulls: boolean = false) {
    const map = object;

    this.forEachProperty(object, (value, key) => {
      const mappedValue: any = mapFn(value, key);

      if (!removeNulls || !ValueUtility.isNullOrUndefined(mappedValue)) {
        map[key] = mappedValue;
      }
    });

    return map;
  }

  /**
   * Map function that maps keys on an object to new values.
   *
   * Null/Undefined keys are ignored and the values discarded.
   * @param object Object to map from.
   * @param mapFn Values mapping function.
   */
  static mapObjectKeys(object: object, mapKeyFn: (value: any, key: string) => PropertyKey | undefined): object {
    const map = object;

    this.forEachProperty(object, (value, key) => {
      const mapKey: PropertyKey | undefined = mapKeyFn(value, key);

      if (mapKey !== undefined) {
        map[mapKey] = value;
      }
    });

    return map;
  }

  /**
   * Function called over every key in the object.
   */
  static forEachProperty(object: object, fn: (value: any, key: string) => void): void {
    const keys = Object.keys(object);

    for (const key of keys) {
      const value = object[key];
      fn(value, key);
    }
  }

  /**
   * Checks if the object has any keys with values in them or not.
   * If recursive, will only return true if sub-objects are also empty.
   * @param object Object check.
   * @param recursive Whether or not to check recursively.
   * @param maxDepth Max depth at which to check the object.
   */
  static isEmptyObject(object: object, recursive: boolean, maxDepth: number = 5): boolean {
    return this._isEmptyObject(object, recursive, 0, maxDepth);
  }

  private static _isEmptyObject(object: any, recursive: boolean, depth: number, maxDepth: number): boolean {
    let isEmpty = false;    // Object not empty if it isn't an object, since we use this function recursively.

    if (this.isObject(object)) {
      if (Array.isArray(object)) {
        isEmpty = (object.length === 0);
      } else if (depth < maxDepth) {
        const makeFn = () => {
          let fn: (value: any) => any;

          if (recursive) {
            fn = (value) => {
              // Return undefined to continue loop.
              return ValueUtility._isEmptyObject(value, true, depth + 1, maxDepth) ? undefined : false;
            };
          } else {
            fn = ValueUtility.undefinedIfNullOrUndefined;
          }

          return fn as (value) => any;
        };

        const checkChildFn = makeFn();
        const test = (this.iterateObjectWhileUndefined(object, checkChildFn) === undefined);
      }
    }

    return isEmpty;
  }

  static isObject(value: any): boolean {
    return (value !== null && typeof value === 'object');
  }

  static isString(value: any): boolean {
    return typeof value === 'string';
  }

  static isJSDate(value: any): boolean {
    return toString.call(value) === '[object Date]';
  }

  static undefinedIfNullOrUndefined<T>(value: T): T | undefined {
    return this.isNotNullOrUndefined(value) ? value : undefined;
  }

  static iterateObjectWhileUndefined(object: object, fn): void {
    const keys = Object.keys(object);

    return this.iterateWhileUndefined(keys, (key) => {
      return fn(object[key], key);
    });
  }

  // MARK: Arrays
  static findWhileUndefined<T>(array: T[], fn: IterateFunction<T>, start?: number, end?: number): any | undefined {
    let result: any;

    this.iterateWhileUndefined(array, (x, index) => {
      result = fn(x, index);
      return result;
    }, start, end);

    return result;
  }

  /**
   * Iterates an array until the iterate function returns a non-undefined value.
   *
   * @param array Array to iterate over.
   * @param fn Iterate Function
   * @param start Index to start at. Defaults to the beginning.
   * @param end Index to end at. Defaults at the end.
   */
  static iterateWhileUndefined<T>(array: T[], fn: IterateFunction<T>, start: number = 0, end: number = array.length): void {
    return this._iterateWhileUndefined(array, fn, start, end);
  }

  private static _iterateWhileUndefined<T>(array: T[], fn: IterateFunction<T>, start: number, end: number): void {
    let value: any | undefined;
    let i: number = (start < 0) ? 0 : start;
    let next: T = array[i];

    while (value === undefined && next !== undefined) {
      value = fn(next, i);
      i += 1;

      if (i <= end) {
        next = array[i];
      } else {
        break;
      }
    }

    return value;
  }

  /*
      Normalizes the array, then copies it.
  */
  static normalizeArrayCopy<T>(input: OneOrMore<T>): T[] {
    const array = this.normalizeArray(input);
    return array.slice(0);
  }

  static normalizeMaybeArrayCopy<T>(input: OneOrMore<T> | null | undefined, returnNull: boolean = false): T[] | null {
    let copy: T[] | null;

    if (input) {
      input = this.normalizeArray(input);
      copy = input.slice(0);
    } else {
      if (returnNull) {
        copy = null;
      } else {
        copy = [];
      }
    }

    return copy;
  }

  /**
   * Filters out all repeating values from the array.
   * Can specify a function to get the key from the input model.
   * @param array Array to filter on.
   * @param keyFn Property key function.
   */
  static getUniqueValues<T>(array: T[], keyFn: MakePropertyKeyFunction<T> = ((x: any) => x as any)): object {
    const map = {};

    array.forEach((element) => {
      const key = keyFn(element);
      map[key] = element;
    });

    return this.convertMapToArray(map);
  }

  // MARK: Partitions
  /**
   * Gets a "partition" of the input array.
   *
   * @param input Objects to partition.
   * @param page Page number.
   * @param pageSize Size of each page.
   */
  static getArrayPartition<T>(input: OneOrMore<T>, page: number, pageSize: number = 10): T[] {
    input = this.normalizeArray(input);

    const startIndex = page * pageSize;
    const endIndex = startIndex + pageSize;
    return input.slice(startIndex, endIndex);
  }

  static partitionArray<T>(input: T[], pageSize: number = 10) {
    if (pageSize < 0) {
      throw new Error('Page size must be positive.');
    }

    const partitions: T[] = [];
    let partition;
    let page = 0;
    let start;
    let end;

    while (true) {
      start = page * pageSize;
      end = start + pageSize;

      partition = input.slice(start, end);

      if (partition.length > 0) {
        partitions.push(partition);
        page += 1;
      } else {
        break;
      }
    }

    return partitions;
  }

  // MARK: Array-Map Conversions
  static mergeUniqueModelArrays<T>(a: T[], b: T[], keyFn: MakePropertyKeyFunction<T>): T[] {
    const mapA = this.convertArrayToMap(a, keyFn);
    const mapB = this.convertArrayToMap(b, keyFn);

    const map = { ...mapA, ...mapB };
    return this.convertMapToArray(map);
  }

  static convertArrayToMirrorMap<T extends PropertyKey>(array: T[]) {
    return this.convertArrayToMap(array, (value) => {
      return value;
    });
  }

  static convertArrayToMap<T>(array: T[], keyFn: MakePropertyKeyFunction<T>) {
    const map: UniqueArrayMap<T> = {};

    array.forEach((value) => {
      const key = keyFn(value);
      map[key] = value;
    });

    return map;
  }

  static convertMapToArray<T>(map: UniqueArrayMap<T>): T[] {
    const array: any[] = [];

    ValueUtility.forEachProperty(map, (value) => {
      array.push(value);
    });

    return array;
  }

  static convertIndexedMapToArray<T>(map: Map<number, T>, emptyValue: T, maxDefaultLimit: number = 1000, indexLimit?: number): T[] {
    if (indexLimit === undefined) {
      const getDefaultLimit = () => {
        let limit = -1;

        map.forEach((_, key) => {
          if (key > limit) {
            limit = key;
          }
        });

        return Math.min(limit, maxDefaultLimit);
      };

      indexLimit = getDefaultLimit();

      if (indexLimit === -1) {
        return []; // Empty map.
      }
    }

    const array: any[] = [];
    let i;
    let value;

    for (i = 0; i < indexLimit; i += 1) {
      value = map.get(i);

      if (value === undefined) {
        value = emptyValue;
      }

      array.push(value);
    }

    return array;
  }

  // MARK: Join
  static joinNonFalsyValues<T>(array: OneOrMore<T>, separator?: string): string {
    return this.filterAndJoin(array, ValueUtility.isTruthy, separator);
  }

  static joinNonNullValues<T>(array: OneOrMore<T>, separator?: string): string {
    return this.filterAndJoin(array, ValueUtility.isNotNullOrUndefined, separator);
  }

  static filterAndJoin<T>(array: OneOrMore<T>, filter: (value: T, index: number) => boolean, separator?: string): string {
    array = this.normalizeArray(array);

    array = array.filter(filter);

    return array.join(separator);
  }

  static joinArrays<T>(values: OneOrMore<T>[]) {
    let results: T[] = [];

    values.forEach((value: OneOrMore<T>) => {
      if (Array.isArray(value)) {
        results = results.concat(value);
      } else {
        results.push(value);
      }
    });

    return results;
  }

  // MARK: Sets
  /**
   * Creates a set from the input.
   * Input is generally a primative, String, Array, or map.
   * @deprecated
   */
  static normalizeSetObject(object: ExistingObject | Array<PropertyKey>, value: any = true): object {
    let set = {};

    if (ValueUtility.isString(object)) {
      set[object as string] = value;
    } else if (Array.isArray(object)) {
      object.forEach((key) => {
        set[key] = value;
      });
    } else if (ValueUtility.isObject(object)) {
      set = { ...object as object };
    } else if (ValueUtility.isNotNullOrUndefined(object)) {
      set[object as PropertyKey] = value;
    }

    return set;
  }

  // MARK: Number
  static roundToPrecision(value: number, precision: number): number {
    return +(Math.round(Number(value + 'e+' + precision)) + 'e-' + precision);
  }

 /**
  * Attempts to convert an input String or Array of Strings to a map of true or false.
  * @deprecated
  */
  static normalizeTruthMap(map: object): object {
    return this.normalizeSetObject(map, true);
  }

  static stringStartsWith(value: string, prefix: string): boolean {
    return (value.indexOf(prefix) === 0);
  }

  static capitalizeFirstLetter(value: string): string {
    return value.charAt(0).toUpperCase() + value.slice(1);
  }

  static lowercaseFirstLetter(value: string): string {
    return value.charAt(0).toLowerCase() + value.slice(1);
  }

}
