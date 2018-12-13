import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { LoginPage } from './login';
import { ResetPasswordPageModule } from "../reset-password/reset-password.module";

@NgModule({
  declarations: [
    LoginPage,
  ],
  imports: [
    IonicPageModule.forChild(LoginPage),
    ResetPasswordPageModule,
  ],
})
export class LoginPageModule {}
