import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import {location_array, location_array, location_array, location_array} from "../../location_array";
import {EventPoint} from "../../event_point";

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

  getEvent(location_array: any[]): Observable<EventPoint[]> {
    return this.http.get<EventPoint[]>(`${this.EventUrl}`,{
      params: {
        nex:location_array[0],
        ney:location_array[1],
        swx:location_array[2],
        swy:location_array[3]
      }
    })
  }

}
