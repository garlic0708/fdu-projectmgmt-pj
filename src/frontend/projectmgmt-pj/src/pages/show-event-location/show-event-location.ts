import { Component, ViewChild, ElementRef } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { HttpClient } from '@angular/common/http';
import { LBSMsgPsg } from "../../components/amap/lbs-msg-psg";
import { Poi } from "../../components/amap/poi";
// import { ShowEventLocationPage } from "../show-near-by/show-near-by";


/**
 * Generated class for the ShowEventLocationPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */


declare var AMap;
let map;

@IonicPage()
@Component({
  selector: 'page-show-event-location',
  templateUrl: 'show-event-location.html',
})
export class ShowEventLocationPage {

  poi: Poi;

  constructor(public navCtrl: NavController, public navParams: NavParams) {
    this.poi = this.navParams.get('poi')
  }

}
