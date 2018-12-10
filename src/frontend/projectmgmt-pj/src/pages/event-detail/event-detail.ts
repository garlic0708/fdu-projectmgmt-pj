import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ToastController } from 'ionic-angular';
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


/**
 * Generated class for the EventDetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

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
              private loading: LoadingCoverProvider,
              private currentUser: CurrentUserProvider,
              private toast: ToastController,) {
    this.eventId = this.navParams.get('eventId');
    this.reload();
    this.imageUrl = this.data.getEventImageUrl(this.eventId);
  }

  private reload() {
    const [detail] = this.loading.fetchData(this.data.getDetail(this.eventId));
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
    console.log(d);
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

}
