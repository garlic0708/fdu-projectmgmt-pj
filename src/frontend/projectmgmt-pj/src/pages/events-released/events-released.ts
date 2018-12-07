import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App} from 'ionic-angular';
import {Observable} from "rxjs";
import {EventPreview} from "../home/event-preview";
import {DataProvider} from "../../providers/data/data";
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {EventDetailPage} from "../event-detail/event-detail";

/**
 * Generated class for the EventsReleasedPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-events-released',
  templateUrl: 'events-released.html',
})
export class EventsReleasedPage {

  private button = 1;

  eventItems: Observable<EventPreview[]>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
    [this.eventItems] = this.loading.fetchData(this.data.getEventsReleased());
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad EventsReleasedPage');
  }

  showNotStarted() {
    this.button = 1;
  }

  showStarted() {
    this.button = 2;
  }

  showEnded() {
    this.button = 3;
  }

  goToEventDetail(id) {
    this.appCtrl.getRootNav().push(EventDetailPage, { eventId: id })
  }

}
