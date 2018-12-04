import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsReleasedPage } from './events-released';
import {IonicImageLoader} from "ionic-image-loader";
import {PipesModule} from "../../pipes/pipes.module";

@NgModule({
  declarations: [
    EventsReleasedPage,
  ],
  imports: [
    IonicImageLoader,
    PipesModule,
    IonicPageModule.forChild(EventsReleasedPage),
  ],
})
export class EventsReleasedPageModule {}
