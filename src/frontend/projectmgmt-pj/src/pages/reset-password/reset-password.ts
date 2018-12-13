import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";

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

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private fb: FormBuilder,) {
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
    this.gotToken = true;
  }

  submit() {

  }

}
