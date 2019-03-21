import DateTime from 'luxon';
import { SortDirection } from './collection';
import { TimedCache } from './cache';
import { ValueUtility } from './value';

export type ISO8061DateString = string;
export type DateInput = DateTime | Date | undefined;
export type FullDateInput = DateInput | ISO8061DateString;

const TIME_IN_MINUTE = 60 * 1000;

export type Minutes = number;

export enum TimeRelationState {
  Before,
  Now,
  After
}

/**
 * Represents a span of time starting from the specified date for the specified duration in minutes.
 */
export interface IDurationSpan {
  date: FullDateInput;
  duration: Minutes;
}

export interface IDurationSpanTimingInfoStateValueListEntry<T extends IDurationSpan> {
  state: TimeRelationState;
  values: T[];
}

export interface IDurationSpanTimingInfoStateValueMap<T extends IDurationSpan> {
  before: T[];
  now: T[];
  after: T[];
}

export interface IDurationSpanTimingInfoStateMap<T extends IDurationSpan> {
  before: IDurationSpanTimingInfoStateMapEntry<T>[];
  now: IDurationSpanTimingInfoStateMapEntry<T>[];
  after: IDurationSpanTimingInfoStateMapEntry<T>[];
}

export interface IDurationSpanTimingInfoStateMapEntry<T extends IDurationSpan> {
  value: T;
  timing: IDurationSpanTimingInfo;
}

export interface IDurationSpanTimingInfo extends IDurationSpan {
  date: DateTime;
  end: DateTime;
  time: number;
  timeUntil: number;
  approxTimeUntil: Minutes;
  isBefore: boolean;
  isAfter: boolean;
  hasStarted: boolean;
  hasEnded: boolean;
  isNow: boolean;
  state: TimeRelationState;
  timeUntilEnd: number;
  timeSinceEnd: number;
  approxTimeUntilEnd: Minutes;
  approxTimeSinceEnd: Minutes;
}

class DurationSpanTimingInfo {

  private _timingInfo: IDurationSpanTimingInfo;

  constructor(private _durationObject: IDurationSpan) { }

  get durationObject() {
    return this._durationObject;
  }

  // MARK: Data
  get timingInfo() {
    if (!this._timingInfo) {
      this._timingInfo = this._buildTimingInfo();
    }

    return this._timingInfo;
  }

  _buildTimingInfo(): IDurationSpanTimingInfo {
    const now = DateTime.local();
    const date = DateTimeUtility.dateTimeFromInput(this.durationObject.date);

    const duration = this.durationObject.duration;       // Minutes
    const time = duration * TIME_IN_MINUTE;              // Milliseconds

    const end = date.plus({ minutes: duration });

    const timeUntil = date.diff(now).milliseconds;
    const approxTimeUntil = Math.floor(timeUntil / TIME_IN_MINUTE);  // Minutes

    const isBefore = now < date;
    const isAfter = now > end;
    const hasStarted = !isBefore;
    const hasEnded = isAfter;
    const isNow = hasStarted && !hasEnded;

    const timeUntilEnd = (!isAfter) ? end.diff(now).milliseconds : 0;
    const timeSinceEnd = (isAfter) ? now.diff(end).milliseconds : 0;

    const approxTimeUntilEnd = (timeUntilEnd) ? Math.floor(timeUntilEnd / TIME_IN_MINUTE) : 0;
    const approxTimeSinceEnd = (timeSinceEnd) ? Math.floor(timeSinceEnd / TIME_IN_MINUTE) : 0;

    const state = (isBefore) ? TimeRelationState.Before : ((isAfter) ? TimeRelationState.After : TimeRelationState.Now);

    return {
      date,
      end,
      duration,
      time,
      timeUntil,
      approxTimeUntil,
      isBefore,
      isAfter,
      state,
      hasStarted,
      hasEnded,
      isNow,
      timeUntilEnd,
      timeSinceEnd,
      approxTimeUntilEnd,
      approxTimeSinceEnd
    };
  }

}

export class DateTimeUtility {

  public static get timeRelationStates() {
    return [TimeRelationState.Before, TimeRelationState.Now, TimeRelationState.After];
  }

  public static sortDateTimeFn<T>(getTime: (x: T) => FullDateInput, direction: SortDirection = SortDirection.Descending) {
    const sort: (x: DateTime, y: DateTime) => number = (direction === SortDirection.Descending)
      ? ((x, y) => y.diff(x).milliseconds)
      : ((x, y) => x.diff(y).milliseconds);

    return (x: T, y: T) => {
      const xDate = this.dateTimeFromInput(getTime(x));
      const yDate = this.dateTimeFromInput(getTime(y));
      return sort(xDate, yDate);
    };
  }

  public static dateTimeISOFromInput(dateInput: FullDateInput, defaultDate?: DateInput, suppressError?: boolean) {
    const date = DateTimeUtility.dateTimeFromInput(dateInput, defaultDate, suppressError);

    if (date) {
      return date.toISO();
    } else {
      return undefined;
    }
  }

  public static dateTimeFromInput(dateInput: FullDateInput, defaultDate?: DateInput, suppressError: boolean = false) {
    dateInput = dateInput || defaultDate;

    if (dateInput instanceof DateTime) {
      return dateInput;
    } else if (dateInput instanceof Date) {
      return DateTime.fromJSDate(dateInput);
    } else if (typeof dateInput === 'string') {
      return DateTime.fromISO(dateInput);
    } else if (typeof dateInput === 'number') {
      return DateTime.fromMillis(dateInput);
    } else if (dateInput && !suppressError) {
      throw new Error('Unknown datetime input: ' + dateInput);
    } else {
      return undefined;
    }
  }

  public static makeTimingInfoCache(getObject: () => IDurationSpan, lifetime: number = 60 * 1000) {
    return new TimedCache<IDurationSpanTimingInfo>({
      refresh: () => {
        return DateTimeUtility.makeTimingInfo(getObject());
      }
    }, lifetime);
  }

  public static makeTimingInfo(durationObject: IDurationSpan): IDurationSpanTimingInfo {
    return new DurationSpanTimingInfo(durationObject).timingInfo;
  }

  public static makeTimingInfoStateValueArray<T extends IDurationSpan>(durationObjects: T[], order: TimeRelationState[] = this.timeRelationStates): IDurationSpanTimingInfoStateValueListEntry<T>[] {
    const result = this.makeTimingInfoStateMap(durationObjects);

    return order.map((state) => {
      const values = result[state].map((x) => x.value);
      return { state, values };
    });
  }

  public static makeTimingInfoStateValueMap<T extends IDurationSpan>(durationObjects: T[]): IDurationSpanTimingInfoStateValueMap<T> {
    const result = this.makeTimingInfoStateMap(durationObjects);

    this.timeRelationStates
      .forEach((state) => {
        result[state] = result[state].map((x) => x.value);
      });

    return result as any;
  }

  public static makeTimingInfoStateMap<T extends IDurationSpan>(durationObjects: T[]): IDurationSpanTimingInfoStateMap<T> {
    const map = {
      before: [],
      now: [],
      after: []
    };

    durationObjects
      .map((x) => {
        return {
          value: x,
          timings: DateTimeUtility.makeTimingInfo(x)
        };
      })
      .forEach((x) => {
        map[x.timings.state].push(x);
      });

    return map;
  }

  public static roundDateTimeToMinuteSteps(dateTime: DateTime, stepSize: number): DateTime {
    if (stepSize <= 1) {
      return dateTime;
    } else {
      const minute = dateTime.minute; // 0 - 59
      const roundedValue = ValueUtility.roundNumberUpToStep(minute, stepSize);

      if (roundedValue !== minute) {
        if (roundedValue === 60) {  // Round the hour up.
          return dateTime.set({ minute: 0 }).plus({ hours: 1 });
        } else {
          return dateTime.set({ minute: roundedValue });
        }
      } else {
        return dateTime;
      }
    }
  }

}

/**
 * @deprecated Use non-deprecated DateUtility.
 */
export class JSDateUtility {

  static readonly MS_IN_DAY = 24 * 60 * 60 * 1000;

  // MARK: Date
  static isExpired(date: Date, expireTime): boolean {
    const timeNow = new Date().getTime();
    const dateTime = date.getTime();
    const expirationTime = dateTime + expireTime;
    return timeNow > expirationTime;
  }

  static compareDates(dateA: Date, dateB: Date): number {
    const a = dateA.getTime();
    const b = dateB.getTime();
    return (a > b) ? 1 : (a === b) ? 0 : -1;
  }

  static isToday(date: Date): boolean {
    return this.isSameDay(new Date(), date);
  }

  static isSameDay(a: Date, b: Date): boolean {
    return a.getDate() === b.getDate() && a.getMonth() === b.getMonth() && a.getFullYear() === b.getFullYear();
  }

  static isPriorAndWithinTheCurrentWeek(date: Date): boolean {
    const now = new Date();
    const days = this.fullDaysBetweenDates(now, date);
    return days < 7 && (date.getDay() <= now.getDay());
  }

  static isWithinTheLastWeek(date: Date): boolean {
    return this.fullDaysBetweenDates(new Date(), date) < 7;
  }

  static fullDaysBetweenDates(a: Date, b: Date): number {
    return Math.floor(this.daysBetweenDates(a, b));
  }

  static daysBetweenDates(a: Date, b: Date): number {
    const timeDifference = Math.abs(a.getTime() - b.getTime());
    return timeDifference / JSDateUtility.MS_IN_DAY;
  }

  static timeDifferenceBetweenDates(a: Date, b: Date): number {
    return Math.abs(a.getTime() - b.getTime());
  }

  // DEPRECATED
  static getShortDateString(date: Date, separator = '/'): string {
    return (date.getMonth() + 1) + separator + (date.getDate()) + separator + date.getFullYear();
  }

  // DEPRECATED
  static get12HourTime(date: Date, addPadding?: boolean, addAmPm: boolean = true): string {
    const hour = date.getHours();

    return this.get12HourString(hour, addPadding) + ':' +
      this.getTwoDigitString(date.getMinutes()) + ':' +
      this.getTwoDigitString(date.getSeconds()) + ' ' +
      this.getAmPmString(hour);
  }

  static get12HourString(time: number, addPadding: boolean = false): string {
    time = ((time + 11) % 12 + 1);

    if (addPadding) {
      return this.getTwoDigitString(time);
    } else {
      return String(time);
    }
  }

  static getTwoDigitString(time: number | string): string {
    time = String(time);

    if (time.length === 1) {
      return '0' + time;
    } else {
      return time;
    }
  }

  static getAmPmString(dateHours: number) {
    if (dateHours < 12) {
      return 'AM';
    } else {
      return 'PM';
    }
  }

}
