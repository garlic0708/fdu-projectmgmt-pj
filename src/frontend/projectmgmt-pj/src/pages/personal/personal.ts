import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App} from 'ionic-angular';
import {EventsJoinPage} from "../events-join/events-join";
import {EventsReleasePage} from "../events-release/events-release";
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";
import {DataProvider} from "../../providers/data/data";

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

  goToEvent1() {
    this.appCtrl.getRootNav().push(EventsJoinPage, {})
  }

  goToEvent2() {
    this.appCtrl.getRootNav().push(EventsReleasePage, {})
  }

}
