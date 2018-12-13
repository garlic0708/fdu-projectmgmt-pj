import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, ToastController } from 'ionic-angular';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { CurrentUserProvider } from "../../providers/current-user/current-user";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";

/**
 * Generated class for the ResetPasswordPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-reset-password',
  templateUrl: 'reset-password.html',
})
export class ResetPasswordPage {

  group: FormGroup;
  gotToken = false;
  gettingToken = false;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private fb: FormBuilder,
              private currentUser: CurrentUserProvider,
              private toast: ToastController,
              private loading: LoadingCoverProvider,) {
    this.group = this.fb.group({
      email: new FormControl('', [Validators.email, Validators.required]),
      verificationToken: new FormControl('', Validators.required),
      newPassword: new FormControl('', Validators.required),
      passwordRepeat: new FormControl('', Validators.required),
    }, {
      validator: (group: FormGroup): { [p: string]: boolean } | null => {
        return group.controls['newPassword'].value === group.controls['passwordRepeat'].value ?
          null : { passwordsNotMatch: true }
      }
    })
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ResetPasswordPage');
  }

  getToken() {
    this.gettingToken = true;
    this.currentUser.requestResetPassword(this.group.controls['email'].value)
      .finally(() => this.gettingToken = false)
      .subscribe(() => {
          this.gotToken = true;
          this.toast.create({
            message:
              '验证码已发送到您的邮箱', duration: 1500
          }).present();
        },
        () => this.toast.create({ message: '网络错误', duration: 1500 }).present())
  }

  submit() {
    const { verificationToken, email, newPassword } = this.group.value;
    const [loading] =
      this.loading.fetchData(this.currentUser.resetPassword(verificationToken, email, newPassword));
    loading.subscribe(
      () => {
        this.toast.create({
          message:
            '重置密码完成，请重新登录', duration: 1500
        }).present();
        this.navCtrl.pop()
      },
      () => this.toast.create({ message: '网络错误', duration: 1500 }).present()
    )
  }

}
