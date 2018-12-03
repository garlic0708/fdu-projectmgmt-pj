import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App} from 'ionic-angular';
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";
import {EventPreview} from "../home/event-preview";
import {Observable} from "rxjs";
import {EventDetailPage} from "../event-detail/event-detail";

/**
 * Generated class for the EventsJoinPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-events-join',
  templateUrl: 'events-join.html',
})
export class EventsJoinPage {

  private button = 1;
  private page = 1;
  private total;

  eventItems: Observable<EventPreview[]>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
    [this.eventItems] = this.loading.fetchData(this.data.getEventJoin());
    //

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad EventsJoinPage');
  }

  button1() {
    this.button = 1;
  }

  button2() {
    this.button = 2;
  }

  button3() {
    this.button = 3;
  }

  nextPage() {

  }

  goToEventDetail(id) {
    this.appCtrl.getRootNav().push(EventDetailPage, { eventId: id })
  }

}
