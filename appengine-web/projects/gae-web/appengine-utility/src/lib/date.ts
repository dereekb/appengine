
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
