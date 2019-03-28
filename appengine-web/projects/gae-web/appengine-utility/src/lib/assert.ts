import { BaseError } from 'make-error';

/**
 * Functions for asserting correctness.
 */
export interface DescriptorAssertionOptions {
  message?: string;
}

/**
 * DescriptorAssertionOptions extension that also maps one value to another.
 */
export interface MapDescriptorAssertionOptions<T> extends DescriptorAssertionOptions {

  /**
   * Maps the value after it has been validated.
   */
  map?: (value: T) => T;

}

export interface AssertionIssue {

  target: object;
  propertyKey: string;

  options?: DescriptorAssertionOptions;

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

// MARK: Generic
export function Assert<T>(assertion: AccessorValueAssertion<T>, options?: MapDescriptorAssertionOptions<T>) {
  return PropertyDescriptorUtility.makePropertyDescriptorAssertion<T>(assertion, options);
}

// MARK: Numbers
export function AssertMin(min: number, options?: DescriptorAssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was less than the minimum "' + min + '".' };
  return PropertyDescriptorUtility.makePropertyDescriptorAssertion<number>((value: number) => {
    return value >= min;
  }, options, DEFAULT_OPTIONS);
}

export function AssertMax(max: number, options?: DescriptorAssertionOptions) {
  const DEFAULT_OPTIONS = { message: 'Value was greater than the maximum "' + max + '".' };
  return PropertyDescriptorUtility.makePropertyDescriptorAssertion<number>((value: number) => {
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

export interface SetValueInterceptorFunctionInput<T> {
  target: object;
  propertyKey: string;
  descriptor: TypedPropertyDescriptor<T>;
  setValue: (value: T) => void;
}

export type SetValueInterceptorFunctionFactory<T> = (input: SetValueInterceptorFunctionInput<T>) => ((T) => void);

export class PropertyDescriptorUtility {

  static makePropertyDescriptorAssertion<T>(assertValueFn: AccessorValueAssertion<T>, options?: MapDescriptorAssertionOptions<T>, defaultOptions?: MapDescriptorAssertionOptions<T>) {

    // Build options
    options = {
      ...defaultOptions,
      ...options
    };

    return this.makeSetPropertyDescriptorInterceptor<T>(({ target, propertyKey, setValue }) => {
      const map = options.map || ((x) => x);

      return function (value: T) {
        if (assertValueFn(value)) {
          const mappedValue = map(value);
          setValue.call(this, mappedValue);
        } else {
          const error: AssertionIssue = { target, propertyKey, options };
          ASSERTION_HANDLER.handle(error);
        }
      };
    });
  }

  static makeSetPropertyDescriptorInterceptor<T>(makeSetValueInterceptorFn: SetValueInterceptorFunctionFactory<T>) {
    const interceptor = (target: object, propertyKey: string, descriptor: TypedPropertyDescriptor<T>) => {
      if (descriptor.set) {
        const setValue: SetAccessorFunction<T> = descriptor.set;

        // Override set function with assertion.
        descriptor.set = makeSetValueInterceptorFn({
          target,
          propertyKey,
          descriptor,
          setValue
        });
      }
    };
    return interceptor;
  }
}
