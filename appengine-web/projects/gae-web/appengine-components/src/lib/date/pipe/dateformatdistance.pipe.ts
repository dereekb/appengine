import { Pipe, PipeTransform, Inject, LOCALE_ID } from '@angular/core';
import { FullDateInput, DateTimeUtility } from '@gae-web/appengine-utility';
import { formatDate } from '@angular/common';
import { formatDistanceToNow } from 'date-fns';
import { from } from 'rxjs';

/**
 * Pipe that takes in a date and appends the distance to it in parenthesis.
 */
@Pipe({ name: 'dateFormatDistance' })
export class DateFormatDistancePipe implements PipeTransform {

  constructor(@Inject(LOCALE_ID) private locale: string) { }

  transform(input: FullDateInput | undefined, format: string, includeSeconds = false) {
    if (input) {
      const dateTime = DateTimeUtility.dateTimeFromInput(input);

      const date = dateTime.toJSDate();
      const dateString = formatDate(date, format, this.locale);

      const distance = formatDistanceToNow(date, {
        includeSeconds,
        addSuffix: true
      });

      return `${dateString} (${distance})`;
    } else {
      return undefined;
    }
  }

}
