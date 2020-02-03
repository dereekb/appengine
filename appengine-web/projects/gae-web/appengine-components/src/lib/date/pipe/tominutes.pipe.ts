import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'toMinutes' })
export class ToMinutesPipe implements PipeTransform {

  transform(milliseconds: number) {
    if (milliseconds) {
      return Math.floor(milliseconds / (60 * 1000));
    }

    return milliseconds;
  }

}
