import { Pipe, PipeTransform } from '@angular/core';
import { FullDateInput, DateTimeUtility } from '@gae-web/appengine-utility';

@Pipe({ name: 'toJsDate' })
export class ToJsDatePipe implements PipeTransform {

  public static toJsDate(input: FullDateInput | undefined): Date | undefined {
    const result = DateTimeUtility.dateTimeFromInput(input);
    return (result) ? result.toJSDate() : undefined;
  }

  transform(input: FullDateInput | undefined) {
    return ToJsDatePipe.toJsDate(input);
  }

}
