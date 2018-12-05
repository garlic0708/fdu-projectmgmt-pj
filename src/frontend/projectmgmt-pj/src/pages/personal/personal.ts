import { Component } from '@angular/core';
import { App, IonicPage, NavController, NavParams } from 'ionic-angular';
import { CurrentUserProvider } from "../../providers/current-user/current-user";
import { LoginPage } from "../login/login";

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
              private loading: LoadingCoverProvider,
              private currentUser: CurrentUserProvider,) {
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

  logout() {
    this.currentUser.logout().subscribe(() => this.appCtrl.getRootNav().setRoot(LoginPage))
  }

}
