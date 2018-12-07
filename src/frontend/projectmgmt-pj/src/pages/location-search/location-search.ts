import {Component, ViewChild} from '@angular/core';
import {IonicPage, NavController, NavParams} from 'ionic-angular';
import {Poi} from "../../components/amap/poi";
import { EventsNearbyPage } from "../events-nearby/events-nearby";
import { AMapComponent } from "../../components/amap/amap";

/**
 * Generated class for the LocationSearchPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-location-search',
  templateUrl: 'location-search.html',
})
export class LocationSearchPage {

  resolveLocation: (l: Poi) => void;
  location: Poi = null;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.resolveLocation = this.navParams.get('resolveLocation')
  }

  ionViewDidLoad() {
    console.log('Hello LocationSearchPage')
  }

  returnLocation(){
    this.resolveLocation(this.location);
    this.navCtrl.pop()
  }
}
