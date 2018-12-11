import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { EventPreview } from "../../pages/home/event-preview";
import { NotifPreview } from "../../pages/notif-list/notif-preview"
import { AddEventForm } from "../../pages/new-event/add-event-form";
import { EventPoint } from "../../components/amap/event_point";
import { EventDetail, JoinedStatus } from "../../pages/event-detail/event-detail";
import { map } from "rxjs/operators/map";

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
  private markNotifAsReadUrl = '/api/notif/read';
  private joinEventUrl = '/api/user/join';
  private quitEventUrl = '/api/user/quit';
  private cancelAnEventUrl: '/api/event/cancel';
  private eventsJoinedUrl = '/api/user/joined';
  private eventsReleasedUrl = '/api/user/released';
  private checkinListUrl = '/api/event/checkin';
  private nearbyEventsUrl = '/api/event/nearby';
  private checkinUrl = '/api/user/checkIn';
  private checkoutUrl = '/api/user/checkOut';

  private addEventUrl = '/api/event/add';
  private eventImageUrl = '/api/eventImg/get';

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
        return { id: x.mId, content: x.content, type: x.messageState.toLowerCase(), }
      })))
  }

  markNotifAsRead(notifId): Observable<any> {
    return this.http.put(`${this.markNotifAsReadUrl}/${notifId}`, null)
  }

  joinEvent(eventId): Observable<any> {
    return this.http.put(`${this.joinEventUrl}/${eventId}`, null)
  }

  quitEvent(eventId): Observable<any> {
    return this.http.put(`${this.quitEventUrl}/${eventId}`, null)
  }

  cancelEvent(eventId): Observable<any> {
    return this.http.put(`${this.cancelAnEventUrl}/${eventId}`, null)
  }

  getEventsJoined(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.eventsJoinedUrl)
  }

  getEventsReleased(): Observable<EventPreview[]> {
    return this.http.get<EventPreview[]>(this.eventsReleasedUrl)
  }

  getEventCheckinList(eventId): Observable<any[]> {
    return this.http.get<any[]>(`${this.checkinListUrl}/${eventId}`).map(
      v => v.map((u) => {
        const { type, ...rest } = u;
        return { checked: type, ...rest }
      }))
  }

  getNearbyEvents(locationArray: any[]): Observable<EventPoint[]> {
    return this.http.get<any[]>(`${this.nearbyEventsUrl}`, {
      params: {
        nex: locationArray[0],
        ney: locationArray[1],
        swx: locationArray[2],
        swy: locationArray[3]
      }
    }).map(v => v.map(u => {
      return {
        id: u.eId,
        name: u.eventName,
        x: u.address.positionX,
        y: u.address.positionY,
      }
    }))
  }

  checkin(eventId, userId, isCancel: boolean = false): Observable<any> {
    const formData = new FormData();
    formData.set('uid', userId);
    return this.http.put(`${isCancel ? this.checkoutUrl : this.checkinUrl}/${eventId}`,
      formData)
  }

  getEventImageUrl(eventId): string {
    return `${this.eventImageUrl}/${eventId}`
  }

}
