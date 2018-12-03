import { Component, ViewChild } from '@angular/core';
import { App, IonicPage, NavController, NavParams, Slides } from 'ionic-angular';
import { EventDetailPage } from "../event-detail/event-detail";
import { Observable, Subject } from "rxjs";
import { DataProvider } from "../../providers/data/data";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import { EventPreview } from "./event-preview";

/**
 * Generated class for the HomePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {

  slideItems: Observable<EventPreview[]>;
  flowItems: Observable<EventPreview[]>;

  @ViewChild(Slides) slides: Slides;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
    [this.slideItems, this.flowItems] =
      this.loading.fetchData(this.data.getHomeSlides(), this.data.getHomeFlow());
    // this.slideItems = this.data.getHomeSlides();
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad HomePage');
  }

  goToEventDetail(id) {
    this.appCtrl.getRootNav().push(EventDetailPage, { eventId: id })
  }

}
