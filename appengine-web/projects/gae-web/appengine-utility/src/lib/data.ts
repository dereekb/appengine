import { ISO8601DateString } from './date';

/**
 * Interface for data that can be converted to JSON.
 */
export interface JsonConvertable {
  toJSON(): any;
}

/**
 * General Data Converter Utility class
 */
export class DataConverterUtility {

  // MARK: JSON
  static asJson(json?: JsonConvertable): any | undefined {
    if (json) {
      return json.toJSON();
    } else {
      return undefined;
    }
  }

  // MARK: Date
  static dateToString(date: Date | undefined): ISO8601DateString | undefined {
    if (date) {
      return date.toISOString();
    } else {
      return undefined;
    }
  }

  static dateFromString(isoDate: ISO8601DateString | undefined): Date | undefined {
    if (isoDate) {
      return new Date(isoDate);
    } else {
      return undefined;
    }
  }

}

export abstract class StringJsonData implements JsonConvertable {

  constructor(json?: any) {
    if (json) {
      this.initWithJson(json);
    }
  }

  // MARK: Json
  public initWithJson(json: any | string) {
    if (typeof json === 'string') {
      json = JSON.parse(json);
    }

    this.updateWithJson(json);
  }

  public toJsonString(): string {
    const json: any | null = this.toJSON();

    if (json) {
      return JSON.stringify(json);
    } else {
      return '';
    }
  }

  public abstract toJSON(): any | null;

  protected abstract updateWithJson(json: any);

}
