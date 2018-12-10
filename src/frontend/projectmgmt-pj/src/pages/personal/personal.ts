import { Component } from '@angular/core';
import { App, IonicPage, NavController, NavParams, ToastController } from 'ionic-angular';
import { CurrentUserProvider, User } from "../../providers/current-user/current-user";
import { LoginPage } from "../login/login";

import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import { DataProvider } from "../../providers/data/data";
import { EventsJoinedPage } from "../events-joined/events-joined";
import { EventsReleasedPage } from "../events-released/events-released";
import { ChangePasswordPage } from "../change-password/change-password";

@Component({
  templateUrl: 'personal-popover.html',
})
export class PersonalPopover {

  private readonly result: (s: 'avatar' | 'nickname') => void;

  constructor(private navParams: NavParams) {
    this.result = this.navParams.get('resolveResult')
  }

  changeAvatar() {
    this.result('avatar')
  }

  changeNickname() {
    this.result("nickname")
  }
}

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

  currentUser: User;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private currentUserProvider: CurrentUserProvider,
              private toast: ToastController,) {
    this.currentUser = this.currentUserProvider.currentUser;
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad PersonalPage');
  }

  goToEventJoined() {
    this.appCtrl.getRootNavs()[0].push(EventsJoinedPage, {})
  }

  goToEventReleased() {
    this.appCtrl.getRootNavs()[0].push(EventsReleasedPage, {})
  }

  goToChangePassword() {
    new Promise(resolve => {
      this.appCtrl.getRootNavs()[0].push(ChangePasswordPage, { resolveResult: resolve })
    }).then(() => {
      this.toast.create({ message: '密码修改完成，请重新登录', duration: 1500 }).present();
      this.logout();
    })
  }

  logout() {
    this.currentUserProvider.logout().subscribe(() => this.appCtrl.getRootNavs()[0].setRoot(LoginPage))
  }
}
