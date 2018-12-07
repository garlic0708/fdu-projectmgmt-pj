import { Component } from '@angular/core';
import { App, IonicPage, NavController, NavParams } from 'ionic-angular';
import { EventDetailPage } from "../event-detail/event-detail";
import { Poi } from "../../components/amap/poi";
import { LocationSearchPage } from "../location-search/location-search";
import { ShowEventLocationPage } from "../show-event-location/show-event-location";

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

  poi: Poi;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad HomePage');
  }

  test() {
    new Promise<Poi>(resolve => {

      this.appCtrl.getRootNav().push(LocationSearchPage, { resolveLocation: resolve })
    }).then(msg => {
      this.poi = msg
    })
  }

  test2() {
    this.appCtrl.getRootNav().push(ShowEventLocationPage, { poi: this.poi })
  }

}
