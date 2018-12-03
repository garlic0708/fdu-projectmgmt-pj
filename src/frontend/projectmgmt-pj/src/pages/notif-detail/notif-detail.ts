import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";
import {Observable} from "rxjs";

/**
 * Generated class for the NotifDetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-notif-detail',
  templateUrl: 'notif-detail.html',
})
export class NotifDetailPage {

  detail: Observable<any>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
    [this.detail] = this.loading.fetchData(this.data.getNotifDetail(this.navParams.get('notifId')));
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad NotifDetailPage');
  }

}
