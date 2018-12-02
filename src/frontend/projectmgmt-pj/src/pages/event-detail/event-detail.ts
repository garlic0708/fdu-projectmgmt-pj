import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { DataProvider } from "../../providers/data/data";
import { Observable } from "rxjs";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import * as moment from 'moment';
import { map } from "rxjs/operators/map";

/**
 * Generated class for the EventDetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

type DisplayStatus = 'canCheck' | 'aboutToCheck' | 'canJoin' | 'joined' | 'full' |
  'alreadyStarted' | 'ended' | 'canceled';
type EventStatus = 'notStarted' | 'started' | 'ended' | 'canceled';

@IonicPage()
@Component({
  selector: 'page-event-detail',
  templateUrl: 'event-detail.html',
})
export class EventDetailPage {

  detail: Observable<any>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
    [this.detail] = this.loading.fetchData(this.data.getDetail(this.navParams.get('eventId')));
  }

  formatTime = (utcTime) => {
    return moment(utcTime).format('YYYY-MM-DD HH:mm')
  };

  ionViewDidLoad() {
    console.log('ionViewDidLoad EventDetailPage');
  }

  private amIInitiator(): boolean {
    return false; // todo
  }

  test() {

  }

  getDisplayStatus(d: {
    status: EventStatus, upperBound: number,
    currentAttendants: number, joined: boolean,
  }): DisplayStatus {
    console.log(d);
    const eventStatus = d.status;
    if (eventStatus === "ended") return "ended";
    if (eventStatus === "canceled") return "canceled";
    return this.amIInitiator() ?
      eventStatus === "started" ? "canCheck" : "aboutToCheck" :
      d.joined ?
        "joined" :
        eventStatus === "started" ?
          "alreadyStarted" :
          d.currentAttendants === d.upperBound ? "full" : "canJoin";
  }

}
