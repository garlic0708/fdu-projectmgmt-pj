import { Component } from '@angular/core';
import {
  IonicPage,
  LoadingController,
  NavController,
  NavParams,
  PopoverController,
  ToastController
} from 'ionic-angular';
import { DataProvider } from "../../providers/data/data";
import { Observable } from "rxjs";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import * as moment from 'moment';
import { map } from "rxjs/operators/map";
import { CheckinPage } from "../checkin/checkin";
import { Poi } from "../../components/amap/poi";
import { ShowEventLocationPage } from "../show-event-location/show-event-location";
import { CurrentUserProvider, User } from "../../providers/current-user/current-user";
import { not } from "rxjs/util/not";
import { tap } from "rxjs/operators";
import { ConfirmProvider } from "../../providers/confirm/confirm";

type PopoverOptions = 'cancel' | 'quit';

@Component({
  templateUrl: 'event-detail-popover.html',
})
export class EventDetailPopover {

  options: (PopoverOptions)[];
  resolveResult: (s: PopoverOptions) => void;

  constructor(private navParams: NavParams,) {
    this.options = this.navParams.get('options');
    this.resolveResult = this.navParams.get('resolveResult')
  }

  text(s: PopoverOptions) {
    if (s === "cancel") return '取消活动';
    if (s === "quit") return '退出活动'
  }

  resolve(s: PopoverOptions) {
    this.resolveResult(s)
  }

}

type DisplayStatus = 'canCheck' | 'aboutToCheck' | 'canJoin' | 'joined' | 'full' |
  'alreadyStarted' | 'ended' | 'canceled' | 'notEnoughCredit';
type EventStatus = 'notStarted' | 'started' | 'ended' | 'canceled';

export type JoinedStatus = 'initiator' | 'check' | 'participated' | 'notJoined';

export type EventDetail = {
  name: string,
  tags: { id: number, name: string }[],
  initiator: User,
  startTime: string,
  endTime: string,
  address: Poi,
  lowerBound: number,
  upperBound: number,
  currentAttendants: number,
  creditLimit: number,
  description: string,
  status: EventStatus,
}

/**
 * Generated class for the EventDetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-event-detail',
  templateUrl: 'event-detail.html',
})
export class EventDetailPage {

  detail: Observable<EventDetail>;
  imageUrl: string;
  eventId: number;

  joined: JoinedStatus;
  joining: boolean = false;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private data: DataProvider,
              private loading: LoadingController,
              private loadingCover: LoadingCoverProvider,
              private currentUser: CurrentUserProvider,
              private toast: ToastController,
              private popover: PopoverController,
              private confirm: ConfirmProvider,) {
    this.eventId = this.navParams.get('eventId');
    this.reload();
    this.imageUrl = this.data.getEventImageUrl(this.eventId);
  }

  private reload() {
    const [detail] = this.loadingCover.fetchData(this.data.getDetail(this.eventId));
    this.detail = detail.pipe(tap(d => this.joined = d.joined),
      map(d => d.detail));
  }

  formatTime = (utcTime) => {
    return moment(utcTime).format('YYYY-MM-DD HH:mm')
  };

  ionViewDidLoad() {
    console.log('ionViewDidLoad EventDetailPage');
  }

  goToLocation(address: Poi) {
    this.navCtrl.push(ShowEventLocationPage, { poi: address })
  }

  getDisplayStatus(d: EventDetail): DisplayStatus {
    const eventStatus = d.status;
    if (eventStatus === "ended") return "ended";
    if (eventStatus === "canceled") return "canceled";
    return this.joined === "initiator" ?
      eventStatus === "started" ? "canCheck" : "aboutToCheck" :
      this.joined !== "notJoined" ?
        "joined" :
        eventStatus === "started" ?
          "alreadyStarted" :
          d.currentAttendants === d.upperBound ?
            "full" :
            this.currentUser.currentUser.credit < d.creditLimit ?
              "notEnoughCredit" :
              "canJoin";
  }

  goToCheckin() {
    this.navCtrl.push(CheckinPage, { eventId: this.eventId })
  }

  joinEvent() {
    this.joining = true;
    this.data.joinEvent(this.eventId)
      .finally(() => this.joining = false)
      .subscribe({
        next: () => this.reload(),
        error: () => this.toast.create({ message: '网络错误', duration: 1500 }).present(),
      })
  }

  quitOrCancelEvent(option: 'quit' | 'cancel') {
    const func: (id) => Observable<any> =
      option === "quit" ? this.data.quitEvent : this.data.cancelEvent;
    this.confirm.confirm(option === "quit" ? '确定退出活动吗？' : '确定取消活动吗？')
      .then(() => {
        const loading = this.loading.create({ content: '加载中……' });
        loading.present();
        func.call(this.data, this.eventId)
          .finally(() => loading.dismiss())
          .subscribe(
            () => {
              this.reload();
              this.toast.create({ message: option === "quit" ? '已退出活动' : '取消活动成功', duration: 1500 }).present()
            },
            () => this.toast.create({ message: '网络错误', duration: 1500 }).present(),
          )
      });
  }

  getPopoverOptions(d: EventDetail): PopoverOptions[] {
    if (d.status === "notStarted") {
      if (this.joined === "initiator") return ["cancel"];
      if (this.joined === "participated") return ["quit"];
    }
    return []
  }

  presentPopover(d: EventDetail, ev) {
    let popover;
    new Promise<PopoverOptions>(resolve => {
      popover = this.popover.create(EventDetailPopover, {
        options: this.getPopoverOptions(d), resolveResult: resolve,
      }, { cssClass: 'event-detail-popover' });
      popover.present({ ev })
    }).then(value => {
      popover.dismiss();
      this.quitOrCancelEvent(value);
    })
  }

}
