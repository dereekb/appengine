import { ValueUtility, OneOrMore, ObjectAttribute } from './value';
import { Keyed } from './collection';
import { PropertyDescriptorUtility, DescriptorAssertionOptions } from './assert';

// MARK: ModelKey
export type ModelType = string;

/**
 * Number key for models.
 */
export type NumberModelKey = number;

/**
 * StringModelKey String key for models.
 */
export type StringModelKey = string;

/**
 * Key for models. Is either a string or number.
 */
export type ModelKey = StringModelKey | NumberModelKey | 0;

/**
 * String that contains ModelKey values that are space-separated.
 */
export type SpaceSeparatedModelKeys = string;

/**
 * An array of ModelKey values.
 */
export type MultipleModelKeys = ModelKey[] | SpaceSeparatedModelKeys;

export type ModelOrKey<T extends Keyed<ModelKey>> = T | ModelKey;

/**
 * Interface for unique models.
 */
export interface UniqueModel extends Keyed<ModelKey> {
  readonly modelKey: ModelKey | undefined;
}

/**
 * UniqueModel with a mutable model key.
 */
export interface MutableUniqueModel extends UniqueModel {
  modelKey: ModelKey;
}

/**
 * UniqueModel that also has a name associated with it.
 */
export interface NamedUniqueModel extends UniqueModel {
  readonly uniqueModelName: string | undefined;
}

/**
 * Map of unique models keyed by their modelKey value.
 */
export interface ModelMap<T extends UniqueModel> {
  [key: string]: T;
}

export const NO_MODEL_KEY_VALUE = 0;
export const MIN_NUMBER_MODEL_KEY_VALUE = NO_MODEL_KEY_VALUE;

export type ReadModelKeyFunction<T> = (model: T) => ModelKey;

export class ModelUtility {

  static makeRandomNumberKey() {
    return Math.floor(100000000 * Math.random());
  }

  /**
   * Orders the input models by the input keys.
   *
   * Unordered keys are ignored.
   */
  static orderModels<T extends UniqueModel>(keys: ModelKey[], models: T[]): T[] {
    const map = this.makeModelKeyMap(models, undefined, true);
    const array: T[] = [];

    keys.forEach((key) => {
      key = String(key);
      const model: T = map.get(String(key));

      if (model) {
        array.push(model);
      }
    });

    return array;
  }

  /**
   * Converts the modelkey to a string if definined and initialized, or returns undefined.
   */
  static modelKeyToString(value: ModelKey | undefined): string | undefined {
    if (ModelUtility.isInitializedModelKey(value)) {
      return String(value);
    } else {
      return undefined;
    }
  }

  /**
   * Similar to filterUniqueModelKeys but doesn't validate the input keys.
   */
  static modelKeysAsUniqueStrings(values: ModelKey[]): string[] {
    const set = ModelUtility.makeModelKeysSet(values);
    return ValueUtility.setToArray(set);
  }

  static modelKeysAsStrings(values: ModelKey[]): string[] {
    const result = values.map((x) => x.toString());
    return result;
  }

  /**
   * Converts the input modelKeys to a single string that contains all input keys in a comma-separated string.
   */
  static makeModelKeysParameter(keys: ModelKey | ModelKey[], uniqueOnly?: boolean): string | undefined {
    const keysArray: string[] = this.makeStringModelKeysArray(keys, uniqueOnly);
    return this.makeModelKeysParameterWithStringArray(keysArray);
  }

  static makeModelKeysParameterWithStringArray(keysArray: string[]): string | undefined {

    if (keysArray.length) {
      return keysArray.join(',');
    }

    return undefined;
  }

  static makeStringModelKeysArray(keys: ModelKey | ModelKey[], uniqueOnly = true): string[] {
    if (Array.isArray(keys)) {
      if (uniqueOnly) {
        keys = ModelUtility.filterUniqueModelKeys(keys);
      }

      return keys.map((x) => ModelUtility.modelKeyToString(x)).filter((x) => x !== undefined) as string[];
    } else {
      if (ModelUtility.isInitializedModelKey(keys)) {
        return [keys.toString()];
      }
    }

    return [];
  }

  static makeStringModelKey(value: number | string): StringModelKey {
    const key: StringModelKey = String(value);
    return key;
  }

  static makeNumberModelKey(value: number | string): NumberModelKey {
    let key: NumberModelKey;

    if (typeof value === 'number') {
      key = value;
    } else {
      key = Number(value);
    }

    return key;
  }

  static filterUniqueModelKeys(modelKeys: ModelKey[]): string[] {
    modelKeys = ModelUtility.filterInitializedModelKeys(modelKeys);
    const set = ModelUtility.makeModelKeysSet(modelKeys);
    return ValueUtility.setToArray(set);
  }

  static readModelKeysAsSet(models: Keyed<ModelKey>[]): Set<string> {
    const keys: ModelKey[] = this.readModelKeys(models);
    return this.makeModelKeysSet(keys);
  }

  static makeModelKeysSet(keys: ModelKey[]): Set<string> {
    const set = new Set<string>();

    keys.forEach((key) => {
      set.add(String(key));
    });

    return set;
  }

  static makeModelMap<T extends UniqueModel, M extends ModelMap<T>>(input: T[], read?: ReadModelKeyFunction<T>): M {
    const map = {} as M as any;
    input.forEach((x) => map[ModelUtility.readModelKey(x, read)] = x);
    return map;
  }

  static filterUniqueModels<T extends UniqueModel>(models: T[]): T[] {
    const result = models.filter(ValueUtility.filterUniqueValuesFn((x) => x.modelKey));
    return result;
  }

  static readModelKeysFromAttributes<T>(models: T[], attributes: OneOrMore<ObjectAttribute>): ModelKey[] {
    attributes = ValueUtility.normalizeArray(attributes);
    return models.map((x: T) => ValueUtility.readAttributesToArray(x, attributes as string[], true))
      .reduce(ValueUtility.reduceArrayFn(), [])       // Models To Single Array
      .map((x: any) => this.readModelKey(x))
      .reduce(ValueUtility.reduceArrayFn(), [])
      .filter(ValueUtility.filterUniqueValuesFn());
  }

  static readModelKeysFromModels<T extends UniqueModel>(models: ModelOrKey<T>[], read?: ReadModelKeyFunction<T>): ModelKey[] {
    const keys: ModelKey[] = models.map((x) => ModelUtility.readModelKey(x, read));
    return ModelUtility.filterInitializedModelKeys(keys);
  }

  static readModelKeys(models: Keyed<ModelKey>[]): ModelKey[] {
    const keys: ModelKey[] = models.map((x) => x.key);
    return ModelUtility.filterInitializedModelKeys(keys);
  }

  static makeModelKeyMap<T extends Keyed<ModelKey>>(models: T[], read?: ReadModelKeyFunction<T>, mapStringKeys = false): Map<ModelKey, T> {
    const fn = (mapStringKeys) ? ModelUtility.makeModelKeyStringMappingFunction(read) : ModelUtility.makeModelKeyMappingFunction(read);
    return ValueUtility.mapFromArray(models, fn);
  }

  static makeModelKeyStringMappingFunction<T extends Keyed<ModelKey>>(read?: ReadModelKeyFunction<T>): ReadModelKeyFunction<T> {
    const mapToKey = ModelUtility.makeModelKeyMappingFunction(read);
    return (input: T) => {
      const key: ModelKey | undefined = mapToKey(input);

      if (key) {
        return String(key);
      }

      return undefined;
    };
  }

  static makeModelKeyMappingFunction<T extends Keyed<ModelKey>>(read: ReadModelKeyFunction<T> = (x) => x.key): ReadModelKeyFunction<T> {
    const fn = (input: T) => {
      const key: ModelKey | undefined = read(input);

      if (key && ModelUtility.isInitializedModelKey(key)) {
        return key;
      } else {
        return undefined;
      }
    };
    return fn;
  }

  static makeModelKeysStringFromModels<T extends UniqueModel>(modelsOrKeys: ModelOrKey<T>[], joiner: string = ' '): string {
    const result = this.makeModelKeysString(this.readModelKeysFromModels(modelsOrKeys), joiner);
    return result;
  }

  static makeModelKeysString(keys: ModelKey[], joiner: string = ' '): string {
    if (keys) {
      return keys.map((x) => this.readModelKeyString(x).toLowerCase()).sort().join(joiner).trim();
    } else {
      return undefined;
    }
  }

  static splitModelKeysString(keysString: MultipleModelKeys, separator: string = ' '): string[] {
    if (Array.isArray(keysString)) {
      return keysString as string[];
    } else if (keysString) {
      return keysString.split(separator);
    } else {
      return [];
    }
  }

  static areSameUniqueModelArrays(a: ModelOrKey<any>[], b: ModelOrKey<any>[]): boolean {
    if (a && b) {
      if (a.length === b.length) {
        return ModelUtility.makeModelKeysStringFromModels(a) === ModelUtility.makeModelKeysStringFromModels(b);
      }
    }

    return false;
  }

  static filterInitializedModelKeys(keys: ModelKey[]): ModelKey[] {
    const result = keys.filter((x) => ModelUtility.isInitializedModelKey(x)) as ModelKey[];
    return result;
  }

  /**
   * Filters out any falsy (invalid) valid model keys.
   *
   * @param key ModelKey.
   */
  static isInitializedModelKey(key: ModelKey | undefined) {
    return Boolean(key) && this.isValidModelKey(key);
  }

  static isNumberModelKey(value: ModelKey | undefined): boolean {
    return (typeof value === 'number') && value !== NO_MODEL_KEY_VALUE;
  }

  static isStringModelKey(value: ModelKey | undefined): boolean {
    return (typeof value === 'string');
  }

  static isValidModelKey(value: ModelKey): boolean {
    if (typeof value === 'number') {
      return ModelUtility.isValidNumberModelKey(value);
    } else {
      return ModelUtility.isValidStringModelKey(value);
    }
  }

  static isValidNumberModelKey(value: NumberModelKey): boolean {
    const numValue: number = value;

    if (numValue < MIN_NUMBER_MODEL_KEY_VALUE) {
      return false;
    }

    return true;
  }

  static isValidStringModelKey(value: StringModelKey): boolean {
    if (typeof value === 'string') {
      return Boolean(value);
    } else {
      return false;
    }
  }

  static readModelKeyString<T extends UniqueModel>(input: ModelOrKey<T> | undefined, read?: ReadModelKeyFunction<T>): string | undefined {
    const key = this.readModelKey(input, read);
    return ModelUtility.modelKeyToString(key);
  }

  static readModelKey<T extends UniqueModel>(input: ModelOrKey<T> | undefined, read: ReadModelKeyFunction<T> = (x) => x.key): ModelKey {
    let key: ModelKey = 0;

    switch (typeof input) {
      case 'number':
      case 'string':
        key = input as ModelKey;
        break;
      case 'object':
        key = read(input) || 0;
        break;
      case 'undefined':
      default:
        key = 0;
        break;
    }

    return key;
  }

  static isModelKey<T extends UniqueModel>(input: ModelOrKey<T>): boolean {
    switch (typeof input) {
      case 'number':
      case 'string':
        return true;
      default:
        return false;
    }
  }

  static isEqual(a: ModelKey, b: ModelKey): boolean {
    // tslint:disable-next-line: triple-equals
    const result = a == b;
    return result;
  }

}

// MARK: Assertions
export function AssertValidModelKey(options?: DescriptorAssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was not a valid ModelKey.' };
  return PropertyDescriptorUtility.makePropertyDescriptorAssertion<ModelKey>((value: ModelKey) => {
    return ModelUtility.isValidModelKey(value);
  }, options, DEFAULT_OPTIONS);
}

/**
 * Modifies the input to assert values set are NumberModelKeys, and casts them to numbers.
 */
export function ValidNumberModelKey(options?: DescriptorAssertionOptions) {
  const DEFAULT_OPTIONS = {
    message: 'Value was not a valid NumberModelKey.',
    map: (x) => Number(x)
  };
  return PropertyDescriptorUtility.makePropertyDescriptorAssertion<NumberModelKey>((value: NumberModelKey) => {
    return ModelUtility.isValidNumberModelKey(value);
  }, options, DEFAULT_OPTIONS);
}

export function ValidStringModelKey(options?: DescriptorAssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was not a valid StringModelKey.' };
  return PropertyDescriptorUtility.makePropertyDescriptorAssertion<StringModelKey>((value: StringModelKey) => {
    return ModelUtility.isValidStringModelKey(value);
  }, options, DEFAULT_OPTIONS);
}
