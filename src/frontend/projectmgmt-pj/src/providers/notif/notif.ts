import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NotifPreview } from "../../pages/notif-list/notif-preview";
import { DataProvider } from "../data/data";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { map } from "rxjs/operators";

/*
  Generated class for the NotifProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class NotifProvider {

  private notificationList: NotifPreview[];
  private _notifications = new BehaviorSubject<NotifPreview[]>([]);

  constructor(private data: DataProvider) {

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

}
