import { NgModule } from '@angular/core';
import { DateFromToTimePipe } from './datefromtoformat.pipe';
import { MinutesStringPipe } from './minutesstring.pipe';
import { TimeDistancePipe } from './timedistance.pipe';
import { ToDateTimePipe } from './todatetime.pipe';
import { ToJsDatePipe } from './tojsdate.pipe';
import { ToMinutesPipe } from './tominutes.pipe';
import { DateFormatDistancePipe } from './dateformatdistance.pipe';

/**
 * Pre-configured GaeComponentsModule that imports GaeComponentsModule and GaeMaterialComponentsModule.
 */
@NgModule({
  exports: [
    DateFromToTimePipe,
    DateFormatDistancePipe,
    MinutesStringPipe,
    TimeDistancePipe,
    ToDateTimePipe,
    ToJsDatePipe,
    ToMinutesPipe
  ],
  declarations: [
    DateFromToTimePipe,
    DateFormatDistancePipe,
    MinutesStringPipe,
    TimeDistancePipe,
    ToDateTimePipe,
    ToJsDatePipe,
    ToMinutesPipe
  ]
})
export class GaeDatePipesModule { }
