import { Pipe, PipeTransform } from '@angular/core';

/**
 * Generated class for the EventFilterPipe pipe.
 *
 * See https://angular.io/api/core/Pipe for more info on Angular Pipes.
 */
@Pipe({
  name: 'eventFilter',
})
export class EventFilterPipe implements PipeTransform {
  transform(value: any[], button: number) {
    console.log(value);
    if (value == null)
      return null;
    else
      return value.filter(k => ((k.status == 'notStarted' && button == 1) ||
        (k.status == 'started' && button == 2) ||
        (k.status == 'canceled' && button == 3) ||
        (k.status === 'ended' && button == 4)));
  }
}
