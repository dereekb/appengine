import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'minutesString' })
export class MinutesStringPipe implements PipeTransform {

  transform(input: number | string) {
    const minutes = Number(input);

    if (input !== undefined && !isNaN(minutes)) {
      if (minutes > 3600) {
        const unrounded = minutes / 3600;
        const days = Math.ceil(unrounded);
        return ((unrounded !== days) ? '~' : '') + days + ' days';
      } else if (minutes > 180) {
        const unrounded = minutes / 60;
        const hours = Math.ceil(unrounded);
        return ((unrounded !== hours) ? '~' : '') + hours + ' hours';
      } else {
        return minutes + ' minutes';
      }
    } else {
      return undefined;
    }
  }

}
