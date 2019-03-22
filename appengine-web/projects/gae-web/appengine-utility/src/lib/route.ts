import { IUniqueModel, ReadModelKeyFunction, ModelUtility } from './model';

export type SrefExpression = string;
export type SrefString = string;
export type SrefPath = string;      // a.b.c
export type MakeSrefFn<T> = ((input: T) => SrefString | undefined);

export class RouteUtility {

  static makeSrefPathForUniqueModelFn<T extends IUniqueModel>(basePath: SrefString, read?: ReadModelKeyFunction<T>) {
    const fn = this.makeSrefPathForModelFn(basePath, (x: T) => ({ id: ModelUtility.readModelKey(x, read) }));
    return fn;
  }

  static makeSrefPathForModelFn<T>(basePath: SrefString, readVariables: (input: T) => any, requireInput: boolean = true) {
    const fn = (input: T) => {
      if (input) {
        const variables = readVariables(input);
        return RouteUtility.makeSrefString(basePath, variables);
      } else {
        return (requireInput) ? undefined : basePath;
      }
    };
    return fn;
  }

  static makeSrefForInputFn<T>(makeFn: (input: T | undefined) => MakeSrefFn<T> | undefined, requireInput: boolean = false): (input: T | undefined) => SrefString | undefined {
    const fn = (input?: T | undefined) => {
      const makeSref = (!requireInput || input !== undefined) ? makeFn(input) : undefined;

      if (makeSref) {
        return makeSref(input);
      } else {
        return undefined;
      }
    };
    return fn;
  }

  static makeSrefString(basePath: string, variables?: any): SrefString {
    const variablesString = (variables) ? `(${JSON.stringify(variables)})` : '';
    return `${basePath}${variablesString}`;
  }

}
