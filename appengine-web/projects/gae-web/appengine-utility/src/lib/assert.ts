import { BaseError } from 'make-error';
import { ModelUtility, NumberModelKey, StringModelKey, ModelKey } from './model';

/**
 * Functions for asserting correctness.
 */
export interface AssertionOptions {

  message?: string;

}

export interface AssertionIssue {

  target: object;
  propertyKey: string;

  options?: AssertionOptions;

}

export class AssertionError extends BaseError {

  private _target: object;
  private _property: string;

  constructor(error: { target: object, propertyKey: string }, message: string) {
    super(message);
    this.name = 'AssertionError';
    this._target = error.target;
    this._property = error.propertyKey;
  }

}

export class AssertionIssueHandler {

  public handle(error: AssertionIssue) {
    throw this.buildException(error);
  }

  public buildException(error: AssertionIssue): AssertionError {
    const message: string = this.buildExceptionString(error);
    return new AssertionError(error, message);
  }

  protected buildExceptionString(error: AssertionIssue): string {
    let message: string;

    if (error.options && error.options.message) {
      message = error.options.message;
    } else {
      message = 'Assertion failed for property \'' + error.propertyKey + '".';
    }

    return message;
  }

}

export let ASSERTION_HANDLER: AssertionIssueHandler = new AssertionIssueHandler();

// MARK: Numbers
export function AssertMin(min: number, options?: AssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was less than the minimum "' + min + '".' };
  return makePropertyDescriptorAssertion<number>((value: number) => {
    return value >= min;
  }, options, DEFAULT_OPTIONS);
}

export function AssertMax(max: number, options?: AssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was greater than the maximum "' + max + '".' };
  return makePropertyDescriptorAssertion<number>((value: number) => {
    return value <= max;
  }, options, DEFAULT_OPTIONS);
}

// MARK: Generic Assertions
type SetAccessorFunction<T> = (T) => void;

/**
 * Assertion function type.
 *
 * Returns true if the assertion passes.
 */
export type AccessorValueAssertion<T> = (T) => boolean;

export function Assert<T>(assertion: AccessorValueAssertion<T>, options?: AssertionOptions) {
  return makePropertyDescriptorAssertion<T>(assertion, options);
}

export function makePropertyDescriptorAssertion<T>(assertValueFn: AccessorValueAssertion<T>, options?: AssertionOptions, defaultOptions?: AssertionOptions) {

  // Build options
  options = {
    ...defaultOptions,
    ...options
  };

  return (target: object, propertyKey: string, descriptor: TypedPropertyDescriptor<T>) => {
    if (descriptor.set) {
      const setValue: SetAccessorFunction<T> = descriptor.set;

      // Override set function with assertion.
      descriptor.set = function(value: T) {
        if (assertValueFn(value)) {
          setValue.call(this, value);
        } else {
          const error: AssertionIssue = { target, propertyKey, options };
          ASSERTION_HANDLER.handle(error);
        }
      };
    }
  };
}

// MARK: Assertions
export function AssertValidModelKey(options?: AssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was not a valid ModelKey.' };
  return makePropertyDescriptorAssertion<ModelKey>((value: ModelKey) => {
    return ModelUtility.isValidModelKey(value);
  }, options, DEFAULT_OPTIONS);
}

export function AssertValidNumberModelKey(options?: AssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was not a valid NumberModelKey.' };
  return makePropertyDescriptorAssertion<NumberModelKey>((value: NumberModelKey) => {
    return ModelUtility.isValidNumberModelKey(value);
  }, options, DEFAULT_OPTIONS);
}

export function AssertValidStringModelKey(options?: AssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was not a valid StringModelKey.' };
  return makePropertyDescriptorAssertion<StringModelKey>((value: StringModelKey) => {
    return ModelUtility.isValidStringModelKey(value);
  }, options, DEFAULT_OPTIONS);
}
