import { Component } from '@angular/core';
import {
  AlertController,
  App,
  IonicPage, LoadingController,
  NavController,
  NavParams, Popover,
  PopoverController,
  ToastController
} from 'ionic-angular';
import { CurrentUserProvider, User } from "../../providers/current-user/current-user";
import { LoginPage } from "../login/login";

import { DataProvider } from "../../providers/data/data";
import { EventsJoinedPage } from "../events-joined/events-joined";
import { EventsReleasedPage } from "../events-released/events-released";
import { ChangePasswordPage } from "../change-password/change-password";
import { ImagePicker } from "@ionic-native/image-picker";
import { ConfirmProvider } from "../../providers/confirm/confirm";

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
              private toast: ToastController,
              private popover: PopoverController,
              private imagePicker: ImagePicker,
              private alert: AlertController,
              private confirm: ConfirmProvider,
              private loading: LoadingController,) {
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

  showPersonalSettings(ev) {
    let popover: Popover;
    new Promise<'avatar' | 'nickname'>((resolve) => {
      popover = this.popover.create(PersonalPopover, { resolveResult: resolve },
        { cssClass: 'personal-popover' });
      popover.present({ ev })
    }).then(value => {
      popover.dismiss();
      if (value === "avatar") {
        this.imagePicker.getPictures({ maximumImagesCount: 1 }).then(([result]) => {
          const loading = this.loading.create({ content: '加载中……' });
          loading.present();
          this.currentUserProvider.changeAvatar(result)
            .then(
              () => loading.dismiss(),
              () => {
                this.toast.create({ message: '网络错误', duration: 1500 }).present();
                loading.dismiss()
              },
            )
        })
      } else {
        this.alert.create({
          title: '请输入新昵称',
          inputs: [{ name: 'nickname' },],
          buttons: [{
            text: '确定',
            handler: data => {
              const loading = this.loading.create({ content: '加载中……' });
              loading.present();
              this.currentUserProvider.changeNickname(data.nickname)
                .catch(() => this.toast.create({ message: '网络错误', duration: 1500 }).present())
                .finally(() => loading.dismiss())
                .subscribe(() => {})
            }
          }]
        }).present()
      }
    })
  }

  goToChangePassword() {
    new Promise(resolve => {
      this.appCtrl.getRootNavs()[0].push(ChangePasswordPage, { resolveResult: resolve })
    }).then(() => {
      this.toast.create({ message: '密码修改完成，请重新登录', duration: 1500 }).present();
      this.logout();
    })
  }

  confirmLogout() {
    this.confirm.confirm('确定退出登录吗？').then(() => this.logout())
  }

  logout() {
    this.currentUserProvider.logout().subscribe(() => this.appCtrl.getRootNavs()[0].setRoot(LoginPage))
  }
}
