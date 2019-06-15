import { FullDateInput, ISO8601DateString, DateTimeUtility } from './date';
import { DateTime } from 'luxon';

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
  static dateToString(date: FullDateInput | undefined): ISO8601DateString | undefined {
    if (date) {
      return DateTimeUtility.dateTimeISOFromInput(date);
    } else {
      return undefined;
    }
  }

  static dateFromString(isoDate: ISO8601DateString | undefined): DateTime | undefined {
    if (isoDate) {
      return DateTimeUtility.dateTimeFromInput(isoDate);
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
