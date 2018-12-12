import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { CheckinPage } from './checkin';
import { IonicImageLoader } from "ionic-image-loader";
import { ComponentsModule } from "../../components/components.module";


@NgModule({
  declarations: [
    CheckinPage,
  ],
  imports: [
    IonicImageLoader,
    ComponentsModule,
    IonicPageModule.forChild(CheckinPage),
  ],
})
export class CheckinPageModule {}
