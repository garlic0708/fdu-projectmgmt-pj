import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsJoinedPage } from './events-joined';
import {IonicImageLoader} from "ionic-image-loader";
import { ComponentsModule } from "../../components/components.module";

@NgModule({
  declarations: [
    EventsJoinedPage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(EventsJoinedPage),
    ComponentsModule,
  ],
})
export class EventsJoinedPageModule {}
