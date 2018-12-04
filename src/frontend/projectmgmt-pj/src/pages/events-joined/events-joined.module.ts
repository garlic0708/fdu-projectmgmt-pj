import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsJoinedPage } from './events-joined';
import {IonicImageLoader} from "ionic-image-loader";
import {EventFilterPipe} from "../../pipes/event-filter/event-filter";
import {PipesModule} from "../../pipes/pipes.module";

@NgModule({
  declarations: [
    EventsJoinedPage,
  ],
  imports: [
    IonicImageLoader,
    PipesModule,
    IonicPageModule.forChild(EventsJoinedPage),
  ],
})
export class EventsJoinedPageModule {}
