import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { EventPreview } from "../../pages/home/event-preview";
import { NotifPreview } from "../../pages/notif-list/notif-preview"
import { EventPoint } from "../../components/amap/event_point";

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
  private notifListUrl = '/api/notif/notif-list';
  private eventsJoinedUrl = '/api/personal/events-joined';
  private eventsReleasedUrl = '/api/personal/events-released';
  private checkinUrl = '/api/event/checkin';
  private EventUrl = '/api/nearby/list';

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

  getNotifList(): Observable<NotifPreview[]> {
    return this.http.get<NotifPreview[]>(this.notifListUrl)
  }


  getEventsJoined(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.eventsJoinedUrl)
  }

  getEventsReleased(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.eventsReleasedUrl)
  }

  getEventCheckin(eventId): Observable<any> {
    return this.http.get(`${this.checkinUrl}/${eventId}`)

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
