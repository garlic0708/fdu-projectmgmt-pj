import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App} from 'ionic-angular';
import { DataProvider } from "../../providers/data/data";
import { Observable} from "rxjs";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import * as moment from 'moment';
import {RegisterPage} from "../register/register";


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

  detail: Observable<any>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
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

  goToRegister() {
    this.appCtrl.getRootNav().push(RegisterPage, {})
  }

}
