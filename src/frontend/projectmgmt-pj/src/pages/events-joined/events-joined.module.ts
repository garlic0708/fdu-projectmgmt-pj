import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsJoinedPage } from './events-joined';
import {IonicImageLoader} from "ionic-image-loader";

@NgModule({
  declarations: [
    EventsJoinedPage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(EventsJoinedPage),
  ],
})
export class EventsJoinedPageModule {}
