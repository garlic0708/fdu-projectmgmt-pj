import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventDetailPage, EventDetailPopover } from './event-detail';
import { IonicImageLoader } from "ionic-image-loader";
import {CheckinPageModule} from "../checkin/checkin.module";
import { ComponentsModule } from "../../components/components.module";

@NgModule({
  declarations: [
    EventDetailPage,
    EventDetailPopover,
  ],
  imports: [
    ComponentsModule,
    CheckinPageModule,
    IonicPageModule.forChild(EventDetailPage),
  ],
  entryComponents: [
    EventDetailPopover,
  ],
})
export class EventDetailPageModule {}
