import DateTime from 'luxon';
import { SortDirection } from './collection';
import { TimedCache } from './cache';
import { ValueUtility } from './value';

export type ISO8601DateString = string;
export type DateInput = DateTime | Date | undefined;
export type FullDateInput = DateInput | ISO8601DateString;

const TIME_IN_MINUTE = 60 * 1000;

export type Minutes = number;

export type Year = number;
export type Day = number;

export enum Month {
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December,
}

export enum DayOfWeek {
    Sunday = 0,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday
}

export enum TimeRelationState {
  Before,
  Now,
  After
}

/**
 * Represents a model with a date.
 */
export interface DatedModel {

  readonly date: Date;

}

/**
 * Represents a span of time starting from the specified date for the specified duration in minutes.
 */
export interface DurationSpan {
  date: FullDateInput;
  duration: Minutes;
}

export interface DurationSpanTimingInfoStateValueListEntry<T extends DurationSpan> {
  state: TimeRelationState;
  values: T[];
}

export interface DurationSpanTimingInfoStateValueMap<T extends DurationSpan> {
  before: T[];
  now: T[];
  after: T[];
}

export interface DurationSpanTimingInfoStateMap<T extends DurationSpan> {
  before: DurationSpanTimingInfoStateMapEntry<T>[];
  now: DurationSpanTimingInfoStateMapEntry<T>[];
  after: DurationSpanTimingInfoStateMapEntry<T>[];
}

export interface DurationSpanTimingInfoStateMapEntry<T extends DurationSpan> {
  value: T;
  timing: DurationSpanTimingInfo;
}

export interface DurationSpanTimingInfo extends DurationSpan {
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

export class DurationSpanTimingInfoBuilder {

  private _timingInfo: DurationSpanTimingInfo;

  constructor(private _durationObject: DurationSpan) { }

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

  _buildTimingInfo(): DurationSpanTimingInfo {
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

const TIME_RELATION_STATES = [TimeRelationState.Before, TimeRelationState.Now, TimeRelationState.After];

export class DateTimeUtility {

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

  public static makeTimingInfoCache(getObject: () => DurationSpan, lifetime: number = 60 * 1000) {
    const cache = new TimedCache<DurationSpanTimingInfo>({
      refresh: () => {
        return DateTimeUtility.makeTimingInfo(getObject());
      }
    }, lifetime);
    return cache;
  }

  public static makeTimingInfo(durationObject: DurationSpan): DurationSpanTimingInfo {
    return new DurationSpanTimingInfoBuilder(durationObject).timingInfo;
  }

  public static makeTimingInfoStateValueArray<T extends DurationSpan>(durationObjects: T[], order: TimeRelationState[] = TIME_RELATION_STATES): DurationSpanTimingInfoStateValueListEntry<T>[] {
    const result = this.makeTimingInfoStateMap(durationObjects);

    return order.map((state) => {
      const values = result[state].map((x) => x.value);
      return { state, values };
    });
  }

  public static makeTimingInfoStateValueMap<T extends DurationSpan>(durationObjects: T[]): DurationSpanTimingInfoStateValueMap<T> {
    const result = this.makeTimingInfoStateMap(durationObjects);

    TIME_RELATION_STATES
      .forEach((state) => {
        result[state] = result[state].map((x) => x.value);
      });

    return result as any;
  }

  public static makeTimingInfoStateMap<T extends DurationSpan>(durationObjects: T[]): DurationSpanTimingInfoStateMap<T> {
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


// MARK: Date Collection
export type getDateFromValue<T> = (T) => Date;

export interface DateCollection<T> {
    add(value: T, date: Date);
}

export abstract class AbstractDateCollection<K extends number, C extends DateCollection<T>, T> implements DateCollection<T> {

    private _sortedValues?: C[];

    protected map = new Map<K, C>();

    protected abstract keyFromDate(date: Date): K;

    public ordered(): C[] {
        return this.sort();
    }

    protected sort() {
        if (!this._sortedValues) {
            let keys: K[] = [];

            this.map.forEach((value, key) => {
                keys.push(key);
            });

            keys = this.sortKeys(keys);

            const values: C[] = [];

            keys.forEach((key) => {
                const value: C | undefined = this.map.get(key);

                if (value) {
                    values.push(value);
                }
            });

            this._sortedValues = values;
        }

        return this._sortedValues;
    }

    protected sortKeys(keys: K[]): K[] {
        return keys.sort((a: number, b: number) => (b - a));   // Sort in descending order.
    }

    public add(value: T, date: Date) {
        const key = this.keyFromDate(date);
        const collection = this.getOrMake(key);
        collection.add(value, date);
        this._sortedValues = undefined;
    }

    public getOrMake(key: K): C {
        return this.map.get(key) || this.addCollection(key);
    }

    protected addCollection(key: K) {
        const collection = this.makeCollection(key);
        this.map.set(key, collection);
        return collection;
    }

    protected abstract makeCollection(key: K): C;

}

export class CalendarFlattenedMonth<T> {

    constructor(public readonly year: Year, public readonly monthCollection: CalendarCollectionMonth<T>) { }

    public get month(): Month {
        return this.monthCollection.month;
    }

    public get values(): T[] {
        return this.monthCollection.allDayValues();
    }

}

export class CalendarCollection<T> extends AbstractDateCollection<Year, CalendarCollectionYear<T>, T> {

    protected keyFromDate(date: Date): Year {
        return date.getFullYear();
    }

    protected makeCollection(key: Year): CalendarCollectionYear<T> {
        return new CalendarCollectionYear<T>(key);
    }

    public addAll(values: T[], getDate: getDateFromValue<T>) {
        values.forEach((value) => {
            this.add(value, getDate(value));
        });
    }

    public flattenedMonths(): CalendarFlattenedMonth<T>[] {
        const ordered = this.ordered().map((year) => year.flattenedMonths());
        return ValueUtility.reduceArray(ordered);
    }

}

export class CalendarCollectionYear<T> extends AbstractDateCollection<Month, CalendarCollectionMonth<T>, T> {

    constructor(public readonly year: Year) {
        super();
    }

    protected keyFromDate(date: Date): Year {
        return date.getMonth();
    }

    protected makeCollection(key: Month): CalendarCollectionMonth<T> {
        return new CalendarCollectionMonth<T>(key);
    }

    public flattenedMonths(): CalendarFlattenedMonth<T>[] {
        return this.ordered().map((month) => new CalendarFlattenedMonth<T>(this.year, month));
    }

}

export class CalendarCollectionMonth<T> extends AbstractDateCollection<Day, CalendarCollectionDay<T>, T> {

    constructor(public readonly month: Month) {
        super();
    }

    protected keyFromDate(date: Date): Day {
        return date.getDate();
    }

    protected makeCollection(key: Month): CalendarCollectionDay<T> {
        return new CalendarCollectionDay<T>(key);
    }

    public allDayValues(): T[] {
        return ValueUtility.reduceArray(this.ordered().map((x) => x.orderedArray()));
    }

}

export class DateValue<T> {

    constructor(public readonly date: Date, public readonly value: T) { }

}

export class CalendarCollectionDay<T> implements DateCollection<T> {

    private _sorted = true;
    private _collection: DateValue<T>[] = [];

    constructor(public readonly day: Day) { }

    public orderedArray(): T[] {
        this.sort();
        return this._collection.map((x) => x.value);
    }

    public sort(): void {
        if (!this._sorted) {
            this._collection.sort((a, b) => {
                // Sort in descending order.
                return -(JSDateUtility.compareDates(a.date, b.date));
            });

            this._sorted = true;
        }
    }

    public add(value: T, date: Date) {
        this._collection.push(new DateValue(date, value));
        this._sorted = false;
    }

}

/**
 * @deprecated Use non-deprecated DateTimeUtility.
 */
export class JSDateUtility {

  static readonly MS_IN_DAY = 24 * 60 * 60 * 1000;

  static readonly TIME_IN_SECOND = 1000;
  static readonly TIME_IN_MINUTE = 60 * JSDateUtility.TIME_IN_SECOND;
  static readonly TIME_IN_HOUR = 60 * JSDateUtility.TIME_IN_MINUTE;
  static readonly TIME_IN_3_HOURS = 3 * JSDateUtility.TIME_IN_HOUR;
  static readonly TIME_IN_DAY = 24 * JSDateUtility.TIME_IN_HOUR;
  static readonly TIME_IN_3_DAYS = 3 * JSDateUtility.TIME_IN_DAY;
  static readonly TIME_IN_WEEK = 7 * JSDateUtility.TIME_IN_DAY;
  static readonly TIME_IN_90_DAYS = 90 * JSDateUtility.TIME_IN_DAY;

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
    const result = this.isSameDay(new Date(), date);
    return result;
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
    const result = Math.floor(this.daysBetweenDates(a, b));
    return result;
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

    // MARK: Dates
    static getRelativeDateString(date: Date, today: string = 'Today'): string {
      if (JSDateUtility.isWithinTheLastWeek(date)) {
          if (JSDateUtility.isToday(date)) {
              return today;
          } else {
              const day = date.getDay();
              return this.nameForDay(day);
          }
      } else {
          return JSDateUtility.getShortDateString(date);
      }
  }

  static nameForMonth(month: Month): string {
      return Month[month];
  }

  static nameForDay(day: DayOfWeek): string {
      return DayOfWeek[day];
  }

  static getDateFromDatedModel(model: DatedModel) {
      return model.date;
  }

  static makeCalendarCollection<T extends DatedModel>(values: T[]): CalendarCollection<T> {
      const calendar = new CalendarCollection<T>();
      calendar.addAll(values, this.getDateFromDatedModel);
      return calendar;
  }

  // MARK: Times
  static makeTimePeriodString(time: number, notAvailableString: string | null = 'Unavailable'): string {
      time = JSDateUtility.roundDownToMinute(time);

      if (time === Infinity) {
          return notAvailableString;
      }

      let timeNumber: number;
      let timeString: string;

      const ago = (time < 0);

      if (ago) {
          time = -time;
      }

      if (time < JSDateUtility.TIME_IN_3_DAYS) {

          // Less than 3 days, show hours or less.
          if (time < JSDateUtility.TIME_IN_3_HOURS) {

              if (time < JSDateUtility.TIME_IN_MINUTE) {

                  // Less than 1 Minute, show seconds.
                  timeNumber = time / JSDateUtility.TIME_IN_SECOND;
                  timeString = 'Second';
              } else {

                  // Less than 3 hours, show minutes.
                  timeNumber = time / JSDateUtility.TIME_IN_MINUTE;
                  timeString = 'Minute';
              }
          } else {

              // More than 3 hours, show hours.
              timeNumber = time / JSDateUtility.TIME_IN_HOUR;
              timeString = 'Hour';
          }
      } else if (time < JSDateUtility.TIME_IN_90_DAYS) {

          // Less than 90 days.
          timeNumber = time / JSDateUtility.TIME_IN_DAY;
          timeString = 'Day';
      } else {

          timeNumber = time / JSDateUtility.TIME_IN_WEEK;
          timeString = 'Week';
      }

      if (timeNumber !== 1) {
          timeString = timeString + 's';
      }

      if (ago) {
          timeString = timeString + ' Ago';
      }

      return timeNumber.toFixed(1) + ' ' + timeString;
  }

  static roundDownToMinute(time: number): number {
      const minutes = Math.floor(time / JSDateUtility.TIME_IN_MINUTE);
      return minutes * JSDateUtility.TIME_IN_MINUTE;
  }

  static roundDownToSecond(time: number): number {
      const seconds = Math.floor(time / JSDateUtility.TIME_IN_SECOND);
      return seconds * JSDateUtility.TIME_IN_SECOND;
  }

}
