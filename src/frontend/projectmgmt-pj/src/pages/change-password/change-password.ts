import { Component } from '@angular/core';
import { IonicPage, LoadingController, NavController, NavParams, ToastController } from 'ionic-angular';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { CurrentUserProvider } from "../../providers/current-user/current-user";

/**
 * Generated class for the ChangePasswordPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-change-password',
  templateUrl: 'change-password.html',
})
export class ChangePasswordPage {

  private formGroup: FormGroup;
  private readonly result: () => void;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private fb: FormBuilder,
              private currentUser: CurrentUserProvider,
              private toast: ToastController,
              private loading: LoadingController,) {
    this.result = this.navParams.get('resolveResult');
    this.formGroup = fb.group({
      oldPassword: new FormControl('', Validators.required),
      newPassword: new FormControl('', Validators.required),
    })
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ChangePasswordPage');
  }

  submit() {
    const loading = this.loading.create({ content: '加载中……' });
    loading.present();
    this.currentUser.changePassword(this.formGroup.value)
      .finally(() => loading.dismissAll())
      .subscribe(
        () => this.result(),
        (err) => this.toast.create({ message: '密码错误', duration: 1500 }).present(),
      )
  }

}
