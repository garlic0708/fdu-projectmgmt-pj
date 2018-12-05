import { Component } from '@angular/core';
import { App, IonicPage, NavController, NavParams } from 'ionic-angular';
import { CurrentUserProvider } from "../../providers/current-user/current-user";
import { LoginPage } from "../login/login";

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
              private app: App,
              private currentUser: CurrentUserProvider,) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad PersonalPage');
  }

  logout() {
    this.currentUser.logout().subscribe(() => this.app.getRootNav().setRoot(LoginPage))
  }

}
