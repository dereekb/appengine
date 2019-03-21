import { IUniqueModel, ReadModelKeyFunction, ModelUtility } from './model';

export type SrefExpression = string;
export type SrefString = string;
export type SrefPath = string;      // a.b.c
export type MakeSrefFn<T> = ((input: T) => SrefString | undefined);

export class RouteUtility {

    static makeSrefPathForUniqueModelFn<T extends IUniqueModel>(basePath: SrefString, read?: ReadModelKeyFunction<T>) {
        return this.makeSrefPathForModelFn(basePath, (x: T) => ({ id: ModelUtility.readModelKey(x, read) }));
    }

    static makeSrefPathForModelFn<T>(basePath: SrefString, readVariables: (input: T) => any, requireInput: boolean = true) {
        return (input: T) => {
            if (input) {
                const variables = readVariables(input);
                return RouteUtility.makeSrefString(basePath, variables);
            } else {
                return (requireInput) ? undefined : basePath;
            }
        };
    }

    static makeSrefForInputFn<T>(makeFn: (input: T | undefined) => MakeSrefFn<T> | undefined, requireInput: boolean = false): (input: T | undefined) => SrefString | undefined {
        return (input?: T | undefined) => {
            const fn = (!requireInput || input !== undefined) ? makeFn(input) : undefined;

            if (fn) {
                return fn(input);
            } else {
                return undefined;
            }
        };
    }

    static makeSrefString(basePath: string, variables?: any): SrefString {
        const variablesString = (variables) ? `(${JSON.stringify(variables)})` : '';
        return `${basePath}${variablesString}`;
    }

}
