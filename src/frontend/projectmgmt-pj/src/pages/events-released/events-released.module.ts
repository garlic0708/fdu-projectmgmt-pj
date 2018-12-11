import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsReleasedPage } from './events-released';
import {IonicImageLoader} from "ionic-image-loader";
import { ComponentsModule } from "../../components/components.module";

@NgModule({
  declarations: [
    EventsReleasedPage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(EventsReleasedPage),
    ComponentsModule,
  ],
})
export class EventsReleasedPageModule {}
