import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { CurrentUserProvider } from "../../providers/current-user/current-user";

/**
 * Generated class for the LoginPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-login',
  templateUrl: 'login.html',
})
export class LoginPage {

  private loginGroup: FormGroup;
  private registerGroup: FormGroup;

  private currentPage: 'login' | 'register' = "login";
  private firstTime: boolean = true;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private fb: FormBuilder,
              private currentUser: CurrentUserProvider,) {
    this.loginGroup = this.fb.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', [Validators.required]),
      rememberMe: new FormControl(false),
    });
    this.registerGroup = this.fb.group({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
      passwordRepeat: new FormControl('', Validators.required),
      nickname: new FormControl(''),
    }, {
      validator: (group: FormGroup): {[p: string]: boolean} | null => {
        return group.controls['password'].value === group.controls['passwordRepeat'].value ?
          null : { passwordsNotMatch: true }
      }
    })
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad LoginPage');
  }

  login() {
    console.log(this.loginGroup.value);
    const {email, password, rememberMe} = this.loginGroup.value;
    this.currentUser.login(email, password, rememberMe).then(
      () => console.log("yes"), // todo
      (msg) => console.log("no", msg),
    )
  }

  register() {
    console.log(this.registerGroup.value);
    const {email, password, nickname} = this.registerGroup.value;
    this.currentUser.register(email, password, nickname).then(
      () => console.log("yes"),
      (msg) => console.log("no", msg),
    )
  }

  toggleCurrentPage() {
    this.firstTime = false;
    this.currentPage = this.currentPage === "login" ? "register" : "login";
  }

  private static formControlHasError(formControl: AbstractControl, error: string): boolean {
    return formControl.touched && formControl.hasError(error)
  }

  private loginError(group = 'login'): string {
    const control = name => (group === 'login' ? this.loginGroup : this.registerGroup).controls[name];
    if (LoginPage.formControlHasError(control('email'), 'required'))
      return '请输入Email';
    else if (LoginPage.formControlHasError(control('email'), 'email'))
      return '请输入正确的Email地址';
    else if (LoginPage.formControlHasError(control('password'), 'required'))
      return '请输入密码';
  }

  private registerError(): string {
    const error = this.loginError('register');
    if (error) return error;
    if (LoginPage.formControlHasError(this.registerGroup, 'passwordsNotMatch'))
      return '两次输入的密码不一致'
  }

}
