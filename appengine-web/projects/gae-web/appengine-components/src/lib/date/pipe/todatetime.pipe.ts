import { Pipe, PipeTransform } from '@angular/core';
import { FullDateInput, DateTimeUtility } from '@gae-web/appengine-utility';
import { DateTime } from 'luxon';

@Pipe({ name: 'toDateTime' })
export class ToDateTimePipe implements PipeTransform {

  public static toDateTime(input: FullDateInput | undefined): DateTime | undefined {
    if (input) {
      return DateTimeUtility.dateTimeFromInput(input);
    } else {
      return undefined;
    }
  }

  transform(input: FullDateInput | undefined) {
    return ToDateTimePipe.toDateTime(input);
  }

}
