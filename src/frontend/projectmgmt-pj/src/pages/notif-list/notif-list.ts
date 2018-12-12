import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App, AlertController} from 'ionic-angular';
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";
import {NotifPreview} from "./notif-preview";
import {Observable} from "rxjs";
import { NotifProvider } from "../../providers/notif/notif";

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
              private notif: NotifProvider,
              public alertCtrl: AlertController) {
    this.notifItems = notif.notifications

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad NotifListPage');
  }

  doAlert(notifItem: NotifPreview) {
    const alert = this.alertCtrl.create({
      subTitle: notifItem.content,
      buttons: ['Ok']
    });

    alert.present();

    this.notif.markAsRead(notifItem.id)
  }

}
