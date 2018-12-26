import { Injectable } from '@angular/core';
import { NotifPreview } from "../../pages/notif-list/notif-preview";
import { DataProvider } from "../data/data";
import { BehaviorSubject, Observable } from "rxjs";
import { map } from "rxjs/operators";
import * as SockJS from "sockjs-client";
import { StompRService } from "@stomp/ng2-stompjs";
import { ApiRedirectProvider } from "../api-redirect/api-redirect";
import { CurrentUserProvider } from "../current-user/current-user";


/*
  Generated class for the NotifProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class NotifProvider {

  private notificationList: NotifPreview[];
  private _notifications = new BehaviorSubject<NotifPreview[]>([]);

  private webSocketUrl = '/websocket';
  private subscriptionUrl = '/user/queue/notif';

  constructor(private data: DataProvider,
              private stomp: StompRService,
              private currentUser: CurrentUserProvider,) {
    this.stomp.config = {
      url: () => new SockJS(`${ApiRedirectProvider.host}${this.webSocketUrl}`),
      headers: {
        token: this.currentUser.auth.getToken()
      },
      heartbeat_in: 0,
      heartbeat_out: 0,
      reconnect_delay: 0,
      debug: true,
    }
  }

  get notifications(): Observable<NotifPreview[]> {
    return this._notifications;
  }

  loadNotif() {
    console.log('loading notif');
    this.data.getNotifList().subscribe(v => {
      console.log('loaded notif', v);
      this.notificationList = v;
      this._notifications.next(v)
    })
  }

  get unreadNumber() {
    return this._notifications.pipe(map(n => {
      console.log('notif', n);
      return n.filter(x => x.type === 'unread').length;
    }))
  }

  markAsRead(id) {
    this.data.markNotifAsRead(id).subscribe(() => {
      this.notificationList.find(x => x.id === id).type = "read";
      this._notifications.next(this.notificationList)
    })
  }

  startListening() {
    this.stomp.initAndConnect();
    this.stomp.subscribe(this.subscriptionUrl)
      .subscribe(m => {
        const notif = JSON.parse(m.body);
        this.notificationList.push({
          id: notif.mId,
          content: notif.content,
          type: notif.messageState.toLowerCase(),
        });
        this._notifications.next(this.notificationList)
      })
  }

  stopListening() {
    this.stomp.disconnect()
  }

}
