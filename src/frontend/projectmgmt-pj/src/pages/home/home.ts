import { Component } from '@angular/core';
import { App, IonicPage, NavController, NavParams } from 'ionic-angular';
import { EventDetailPage } from "../event-detail/event-detail";

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

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad HomePage');
  }

  test() {
    this.appCtrl.getRootNav().push(EventDetailPage)
  }

}
