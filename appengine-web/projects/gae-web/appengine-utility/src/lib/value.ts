export type OneOrMore<T> = T | T[];

export type ObjectAttribute = string;
export type ExistingObject = string | number | boolean | symbol | object | any;
export type PropertyKey = string | number;
export type MakePropertyKeyFunction<T> = (input: T) => PropertyKey;
export type MakeMapKeysFunction<K, T> = (input: T) => K | K[] | undefined;
export type IterateFunction<T> = (x: T, index: number) => any | undefined;

export interface SeparateResult<T> {
  included: T[];
  excluded: T[];
}

export interface GroupingResult<T> {
  [key: string]: T[];
}

export interface UniqueArrayMap<T> {
  [key: string]: T;
}

export interface ObjectPropertiesReading {
  readonly values: any[];
  readonly missing: PropertyKey[];
}

export class ArrayDelta<T> {
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
  static readValue(value: any | (() => any), args?: any) {
    let result;

    if (value instanceof Function) {
      result = value.apply(undefined, args);
    } else {
      result = value;
    }

    return result;
  }

  static getObjectProperties<T extends object>(object: T, properties: OneOrMore<PropertyKey>): any[] {
    const result = this.readObjectProperties(object, properties).values;
    return result;
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

  static lastValueInArray<T>(array: T[] | undefined): T | undefined {
    if (array) {
      return array[array.length - 1];
    } else {
      return undefined;
    }
  }

  static batch<T>(array: T[], batchSize: number): T[][] {
    array = [].concat(array);
    const batch = [];

    while (array.length > 0) {
      batch.push(array.splice(0, batchSize));
    }

    return batch;
  }

  static reduceArrayFn<T>() {
    const fn = ((x: T[] = [], y: T) => x.concat(Array.isArray(y) ? y : [y]));
    return fn;
  }

  static readAttributesToArray(value: any, attributes: ObjectAttribute[], filterUndefined: boolean = false) {
    const values = attributes.map((x) => value[x]);
    return (filterUndefined) ? values.filter((x) => x !== undefined) : values;
  }

  // MARK: Filter
  static filterUniqueValuesFn<K, T>(filterOn: (x: T) => K = ((x) => x as any)) {
    const set = new Set<K>();
    return (x: T) => {
      const key = filterOn(x);

      if (!set.has(key)) {
        set.add(key);
        return true;
      } else {
        // Filter Out Repeats
        return false;
      }
    };
  }

  static filterUniqueValues<T>(values: T[]) {
    return Array.from(new Set(values));
  }

  // MARK: Special
  static isNotNullOrUndefined(object: any | null | undefined): boolean {
    const result = !this.isNullOrUndefined(object);
    return result;
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
  static copyArrayObjectProperties(from: object, to: object, properties: PropertyKey[] = Object.keys(from), returnNull?: boolean) {
    properties.forEach((property) => {
      const array = from[property];
      to[property] = ValueUtility.normalizeMaybeArrayCopy(array, returnNull);
    });
  }

  static copyObjectProperties(from: object, to: object, properties: PropertyKey[] = Object.keys(from), defaults: object = {}) {
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
   * Creates a new map from the input array and make keys function.
   */
  static mapFromArray<K, T>(input: T[], makeKeysFn: MakeMapKeysFunction<K, T>): Map<K, T> {
    const map: Map<K, T> = new Map();

    input.forEach((value: T) => {
      const keys: K | K[] | undefined = makeKeysFn(value);
      const normalizedKeys: K[] | undefined = ValueUtility.normalizeArray(keys);

      normalizedKeys.forEach((x) => {
        if (x !== undefined) {
          map.set(x, value);
        }
      });
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
   * Converts a set to an array. If the set is undefined, returns an empty array.
   */
  static setToArray<T>(input: Set<T> | undefined): T[] {
    const array: T[] = [];

    if (input) {
      input.forEach((item) => {
        array.push(item);
      });
    }

    return array;
  }

  /**
   * Converts an array to a set. If the array is undefined, returns an empty set.
   */
  static arrayToSet<T>(input: T[] | undefined): Set<T> {
    const set = new Set<T>();

    if (input) {
      input.forEach((x) => {
        set.add(x);
      });
    }

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

  static arrayDelta<T>(from: T[], to: T[]): ArrayDelta<T> {
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
    const result = this._isEmptyObject(object, recursive, 0, maxDepth);
    return result;
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
        isEmpty = (this.iterateObjectWhileUndefined(object, checkChildFn) === undefined);
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
    return Object.prototype.toString.call(value) === '[object Date]';
  }

  static undefinedIfNullOrUndefined<T>(value: T): T | undefined {
    const result = this.isNotNullOrUndefined(value) ? value : undefined;
    return result;
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
    const result = this._iterateWhileUndefined(array, fn, start, end);
    return result;
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
  static getUniqueValues<T>(array: T[], keyFn: MakePropertyKeyFunction<T> = ((x: any) => x as any)): T[] {
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

  // MARK: Grouping
  static separateValues<T>(values: T[], checkInclusionFn: (T) => boolean): SeparateResult<T> {
    const result = this.groupValues(values, (x) => {
      return (checkInclusionFn(x)) ? 'include' : 'exclude';
    });

    return {
      included: result.include || [],
      excluded: result.exclude || []
    };
  }

  /**
   * Similar to makeValuesGroupMap, but returns an object instead of a map.
   */
  static groupValues<K extends PropertyKey, T>(values: T[], groupKeyFn: (T) => K): GroupingResult<T> {
    const map = this.makeValuesGroupMap<K, T>(values, groupKeyFn);
    return this.mapToObject(map);
  }

  // MARK: Map
  static makeValuesGroupMap<K, T>(values: T[], keyForValue: (T) => K): Map<K, T[]> {
    const map = new Map<K, T[]>();

    values.forEach((x) => {
      const key = keyForValue(x);

      if (map.has(key)) {
        map.get(key).push(x);
      } else {
        map.set(key, [x]);
      }
    });

    return map;
  }

  static mapToObject<K extends PropertyKey, T>(map: Map<K, T>): { [key: string]: T } {
    const object = {} as any;

    map.forEach((x: T, key: K) => {
      object[key] = x;
    });

    return object;
  }

  // MARK: Array-Map Conversions
  static mergeUniqueModelArrays<T>(a: T[], b: T[], keyFn: MakePropertyKeyFunction<T>): T[] {
    const mapA = this.convertArrayToMap(a, keyFn);
    const mapB = this.convertArrayToMap(b, keyFn);

    const map = { ...mapA, ...mapB };
    return this.convertMapToArray(map);
  }

  static convertArrayToMirrorMap<T extends PropertyKey>(array: T[]): UniqueArrayMap<T> {
    const result = this.convertArrayToMap(array, (value) => {
      return value;
    });
    return result;
  }

  static convertArrayToMap<T>(array: T[], keyFn: MakePropertyKeyFunction<T>): UniqueArrayMap<T> {
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

  /**
   * Converts a map to an array of key/value pairs.
   */
  static convertMapToPairsArray<T>(map: UniqueArrayMap<T>): { key: string, value: string | number | undefined | any }[] {
    const array: { key: string, value: string }[] = [];

    ValueUtility.forEachProperty(map, (value, key) => {
      array.push({
        key,
        value: (value !== undefined) ? value : undefined
      });
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
    const result = this.filterAndJoin(array, ValueUtility.isTruthy, separator);
    return result;
  }

  static joinNonNullValues<T>(array: OneOrMore<T>, separator?: string): string {
    const result = this.filterAndJoin(array, ValueUtility.isNotNullOrUndefined, separator);
    return result;
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

  // MARK: Number/Math
  static roundToPrecision(value: number, precision: number): number {
    return +(Math.round(Number(value + 'e+' + precision)) + 'e-' + precision);
  }

  static roundNumberUpToStep(value: number, step: number) {
    return Math.ceil(value / step) * step;
  }

  static range(from: number = 0, to: number): number[] {
    const range: number[] = [];

    for (let i = from; i < to; i += 1) {
      range.push(i);
    }

    return range;
  }

  /**
   * Attempts to convert an input String or Array of Strings to a map of true or false.
   * @deprecated
   */
  static normalizeTruthMap(map: object): object {
    const result = this.normalizeSetObject(map, true);
    return result;
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

  static isEqualIgnoreCase(a: string, b: string) {
    const result = a.toLowerCase() === b.toLowerCase();
    return result;
  }

  static filterIsEqualIgnoreCase<T>(a: string | undefined, values: T[], getString: (x: T) => string, keepSame = true): T[] {
    const lowerCaseA = String(a || '').toLowerCase();
    const result = values.filter(x => {
      const xString = (getString(x) || '').toLowerCase();

      const isSame = lowerCaseA === xString;
      return (isSame) ? keepSame : !keepSame;
    });
    return result;
  }

}
