import { Pipe, PipeTransform, Inject, LOCALE_ID } from '@angular/core';
import { FullDateInput, DateTimeUtility } from '@gae-web/appengine-utility';
import { formatDate } from '@angular/common';

/**
 * Pipe that takes in a date and number of minutes and outputs a formatted date.
 */
@Pipe({ name: 'dateFromPlusTo' })
export class DateFromToTimePipe implements PipeTransform {

  constructor(@Inject(LOCALE_ID) private locale: string) { }

  transform(input: FullDateInput | undefined, format: string, minutes: number) {
    if (input) {
      const dateTime = DateTimeUtility.dateTimeFromInput(input);
      const endDate = dateTime.plus({ minutes });

      const date = dateTime.toJSDate();
      const dateString = formatDate(date, format, this.locale);
      return dateString + ' - ' + endDate.toFormat('h:mm a');
    } else {
      return undefined;
    }
  }

}
