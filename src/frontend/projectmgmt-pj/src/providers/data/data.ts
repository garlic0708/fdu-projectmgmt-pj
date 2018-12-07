import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { EventPoint } from "../../components/amap/event_point";

/*
  Generated class for the DataProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class DataProvider {

  private EventUrl = '/api/nearby/list';

  constructor(public http: HttpClient) {
    console.log('Hello DataProvider Provider');
  }

  getEvent(locationArray: any[]): Observable<EventPoint[]> {
    return this.http.get<EventPoint[]>(`${this.EventUrl}`, {
      params: {
        nex: locationArray[0],
        ney: locationArray[1],
        swx: locationArray[2],
        swy: locationArray[3]
      }
    })
  }

}
