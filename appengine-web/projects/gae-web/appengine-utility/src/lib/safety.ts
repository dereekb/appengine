import { ModelUtility, NumberModelKey, StringModelKey, ModelKey } from './model';

export type AccessorValueSafety<T> = (T) => T;

export function makePropertyDescriptorSafety<T>(safetyFn: AccessorValueSafety<T>) {
  return (target: object, propertyKey: string, descriptor: TypedPropertyDescriptor<T>) => {
    if (descriptor.set) {
      const originalSet: (T) => void = descriptor.set;

      // Override set function with assertion.
      descriptor.set = (value: T): void => {
        value = safetyFn(value);
        originalSet.call(this, value);  // Call with target as this.
      };
    }
  };
}

// MARK: DTOs
export function DtoKey() {
  return makePropertyDescriptorSafety<ModelKey | undefined>((value: ModelKey | undefined) => {
    if (ModelUtility.isInitializedModelKey(value)) {
      return value;
    } else {
      return undefined;
    }
  });
}

export function DtoKeys() {
  return makePropertyDescriptorSafety<ModelKey[] | undefined>((value: ModelKey[] | undefined) => {
    if (value) {
      value = ModelUtility.filterInitializedModelKeys(value);
      return (value.length > 0) ? value : undefined;
    }

    return undefined;
  });
}
