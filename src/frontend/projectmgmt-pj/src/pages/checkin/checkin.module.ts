import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { CheckinPage } from './checkin';
import { IonicImageLoader } from "ionic-image-loader";


@NgModule({
  declarations: [
    CheckinPage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(CheckinPage),
  ],
})
export class CheckinPageModule {}
