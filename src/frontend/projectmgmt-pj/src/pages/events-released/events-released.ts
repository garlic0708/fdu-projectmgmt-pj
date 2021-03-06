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

  goToEventDetail(id) {
    this.appCtrl.getRootNavs()[0].push(EventDetailPage, { eventId: id })
  }

}
