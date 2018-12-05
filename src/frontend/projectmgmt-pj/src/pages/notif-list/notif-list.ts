import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App, AlertController} from 'ionic-angular';
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";
import {NotifPreview} from "./notif-preview";
import {Observable} from "rxjs";

/**
 * Generated class for the NotifListPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-notif-list',
  templateUrl: 'notif-list.html',
})
export class NotifListPage {

  notifItems: Observable<NotifPreview[]>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider,
              public alertCtrl: AlertController) {
    [this.notifItems] = this.loading.fetchData(this.data.getNotifList());

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad NotifListPage');
  }

  doAlert(notifItem) {
    let alert = this.alertCtrl.create({
      title: notifItem.title,
      subTitle: notifItem.content,
      buttons: ['Ok']
    });

    alert.present();

    //TODO 若未读发送已读请求
  }

}
