import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { EventPreview } from "../../pages/home/event-preview";
import { NotifPreview } from "../../pages/notif-list/notif-preview"
import { AddEventForm } from "../../pages/new-event/add-event-form";

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
  private eventTagListUrl = '/api/event-tag/list';
  private notifListUrl = '/api/notif/notif-list';
  private eventsJoinedUrl = '/api/personal/events-joined';
  private eventsReleasedUrl = '/api/personal/events-released';
  private checkinUrl = '/api/event/checkin';

  private addEventUrl = '/api/event/add';

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

  getEventTagList(): Observable<any> {
    return this.http.get(this.eventTagListUrl)
  }

  addEvent(imageUri: string, payload: AddEventForm): Promise<any> {
    return fetch(imageUri).then(res => res.blob())
      .then(blob => {
        const formData = new FormData();
        formData.append('file', blob);
        const format = "yyyy-MM-dd HH:mm:ssZ";
        formData.append('form', JSON.stringify({
          ...payload,
          startTime: payload.startTime.format(format),
          endTime: payload.endTime.format(format),
        }));
        return this.http.post(this.addEventUrl, formData).toPromise()
      })
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

  }

}
