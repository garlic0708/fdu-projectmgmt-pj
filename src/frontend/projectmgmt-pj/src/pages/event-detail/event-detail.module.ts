import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventDetailPage } from './event-detail';
import { IonicImageLoader } from "ionic-image-loader";
import {CheckinPageModule} from "../checkin/checkin.module";

@NgModule({
  declarations: [
    EventDetailPage,
  ],
  imports: [
    IonicImageLoader,
    CheckinPageModule,
    IonicPageModule.forChild(EventDetailPage),
  ],
})
export class EventDetailPageModule {}
