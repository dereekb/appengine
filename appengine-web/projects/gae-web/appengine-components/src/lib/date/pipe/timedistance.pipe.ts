import { Pipe, PipeTransform } from '@angular/core';
import { FullDateInput, DateTimeUtility } from '@gae-web/appengine-utility';
import { DateTime } from 'luxon';
import { formatDistance } from 'date-fns';
import { ToJsDatePipe } from './tojsdate.pipe';

@Pipe({ name: 'timeDistance' })
export class TimeDistancePipe implements PipeTransform {

  transform(input: FullDateInput | undefined, to: Date = new Date(), unavailable: string = 'Not Available') {
    if (input) {
      const from = ToJsDatePipe.toJsDate(input);
      return formatDistance(to, from, {});
    } else {
      return unavailable;
    }
  }

}
