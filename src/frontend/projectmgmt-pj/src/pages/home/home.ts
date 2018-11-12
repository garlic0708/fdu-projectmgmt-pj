import { Component, ViewChild } from '@angular/core';
import { App, IonicPage, NavController, NavParams, Slides } from 'ionic-angular';
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

  imgs: Array<{ path: string, title: string }>; // todo

  @ViewChild(Slides) slides: Slides;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App) {
    this.imgs = [
      {path: './assets/imgs/logo.png', title: 'Title 1'},
      {path: './assets/imgs/logo.png', title: 'Title 2'},
      {path: './assets/imgs/logo.png', title: 'Title 3'},
    ];
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad HomePage');
  }

  goToEventDetail(id) {
    this.appCtrl.getRootNav().push(EventDetailPage, { eventId: id })
  }

}
