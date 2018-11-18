import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import {location_array} from "../../location_array";

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

  getEvent(location_array: location_array): Observable<any> {
    return this.http.get(`${this.EventUrl}/${location_array}`)
  }

}
