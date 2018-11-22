import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { EventPreview } from "../../pages/home/event-preview";

/*
  Generated class for the DataProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class DataProvider {

  private detailUrl = '/api/event/detail';
  private slidesUrl = '/api/event/home-slides';
  private flowUrl = '/api/event/home-flow';
  private eventTypeListUrl = '/api/event-type/list';

  constructor(public http: HttpClient) {
    console.log('Hello DataProvider Provider');
  }

  getDetail(eventId): Observable<any> {
    return this.http.get(`${this.detailUrl}/${eventId}`)
  }

  getHomeSlides(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.slidesUrl)
  }

  getHomeFlow(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.flowUrl)
  }

  getEventTypeList(): Observable<any> {
    return this.http.get(this.eventTypeListUrl)
  }

}
