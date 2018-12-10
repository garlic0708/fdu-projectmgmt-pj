import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { EventPreview } from "../../pages/home/event-preview";
import { NotifPreview } from "../../pages/notif-list/notif-preview"
import { AddEventForm } from "../../pages/new-event/add-event-form";
import { EventPoint } from "../../components/amap/event_point";
import { EventDetail, JoinedStatus } from "../../pages/event-detail/event-detail";
import { map } from "rxjs/operators/map";
import * as moment from "moment";

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
  private joinEventUrl = '/api/user/join';
  private eventsJoinedUrl = '/api/user/joined';
  private eventsReleasedUrl = '/api/user/released';
  private checkinUrl = '/api/event/checkin';
  private nearbyEventsUrl = '/api/nearby/list';

  private addEventUrl = '/api/event/add';
  private eventImageUrl = '/api/image/get';

  constructor(public http: HttpClient) {
    console.log('Hello DataProvider Provider');
  }

  getDetail(eventId): Observable<{ detail: EventDetail, joined: JoinedStatus }> {
    return this.http.get<any>(`${this.detailUrl}/${eventId}`)
      .pipe(map((d: { detail: any, state: JoinedStatus }) => {
        const {
          initiator, tags, currentAttendants,
          creditLimit, startTime, endTime
        } = d.detail;
        return {
          detail: {
            name: d.detail.eventName,
            tags,
            initiator,
            startTime,
            endTime,
            address: {
              id: d.detail.address.poiId,
              name: d.detail.address.addressName,
              location: {
                lng: d.detail.address.positionX,
                lat: d.detail.address.positionY,
              },
              address: d.detail.address.addressPosition,
            },
            lowerBound: d.detail.lowerLimit,
            upperBound: d.detail.upperLimit,
            currentAttendants,
            creditLimit,
            description: d.detail.content,
            status: d.detail.eventState,
          }, joined: d.state,
        }
      }))
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
        const format = "YYYY-MM-DD HH:mm:ssZZ";
        formData.append('addEventForm', JSON.stringify({
          ...payload,
          startTime: payload.startTime.format(format),
          endTime: payload.endTime.format(format),
        }));
        return this.http.post(this.addEventUrl, formData).toPromise()
      })
  }

  getNotifList(): Observable<NotifPreview[]> {
    return this.http.get<any[]>(this.notifListUrl).pipe(map(n =>
      n.map(x => {
        return { id: x.mId, content: x.content, type: x.messagestate, }
      })))
  }

  joinEvent(eventId): Observable<any> {
    return this.http.put(`${this.joinEventUrl}/${eventId}`, null)
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

  getNearbyEvents(locationArray: any[]): Observable<EventPoint[]> {
    return this.http.get<EventPoint[]>(`${this.nearbyEventsUrl}`, {
      params: {
        nex: locationArray[0],
        ney: locationArray[1],
        swx: locationArray[2],
        swy: locationArray[3]
      }
    })
  }

  getEventImageUrl(eventId): string {
    return `${this.eventImageUrl}/${eventId}`
  }

}
