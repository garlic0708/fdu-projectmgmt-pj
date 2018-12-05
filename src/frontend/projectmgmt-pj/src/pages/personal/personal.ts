import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App} from 'ionic-angular';

import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";
import {EventsJoinedPage} from "../events-joined/events-joined";
import {EventsReleasedPage} from "../events-released/events-released";

/**
 * Generated class for the PersonalPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-personal',
  templateUrl: 'personal.html',
})
export class PersonalPage {

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad PersonalPage');
  }

  goToEventJoined() {
    this.appCtrl.getRootNav().push(EventsJoinedPage, {})
  }

  goToEventReleased() {
    this.appCtrl.getRootNav().push(EventsReleasedPage, {})
  }

}
