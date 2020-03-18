import { Pipe, PipeTransform, Inject, LOCALE_ID } from '@angular/core';
import { FullDateInput, DateTimeUtility } from '@gae-web/appengine-utility';
import { formatDate } from '@angular/common';

/**
 * Pipe that takes in a date and number of minutes and outputs a formatted date.
 */
@Pipe({ name: 'dateFromPlusTo' })
export class DateFromToTimePipe implements PipeTransform {

  static formatFromTo(input: FullDateInput | undefined, format: string, minutes: number, locale?: string) {
    if (input) {
      const dateTime = DateTimeUtility.dateTimeFromInput(input);
      const endDate = dateTime.plus({ minutes });

      const date = dateTime.toJSDate();
      const dateString = formatDate(date, format, locale);
      return dateString + ' - ' + endDate.toFormat('h:mm a');
    } else {
      return undefined;
    }
  }

  constructor(@Inject(LOCALE_ID) private locale: string) { }

  transform(input: FullDateInput | undefined, format: string, minutes: number) {
    return DateFromToTimePipe.formatFromTo(input, format, minutes, this.locale);
  }

}
