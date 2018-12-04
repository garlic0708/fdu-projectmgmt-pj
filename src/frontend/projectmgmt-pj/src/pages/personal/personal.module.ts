import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PersonalPage } from './personal';
import { IonicImageLoader} from "ionic-image-loader";
import {EventsJoinedPageModule} from "../events-joined/events-joined.module";
import {EventsReleasedPageModule} from "../events-released/events-released.module";


@NgModule({
  declarations: [
    PersonalPage,
  ],
  imports: [
    IonicImageLoader,
    EventsJoinedPageModule,
    EventsReleasedPageModule,
    IonicPageModule.forChild(PersonalPage),
  ],
})
export class PersonalPageModule {}
