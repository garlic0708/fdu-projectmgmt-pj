import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PersonalPage, PersonalPopover } from './personal';
import { IonicImageLoader} from "ionic-image-loader";
import {EventsJoinedPageModule} from "../events-joined/events-joined.module";
import {EventsReleasedPageModule} from "../events-released/events-released.module";
import { ComponentsModule } from "../../components/components.module";


@NgModule({
  declarations: [
    PersonalPage,
    PersonalPopover,
  ],
  imports: [
    IonicImageLoader,
    EventsJoinedPageModule,
    EventsReleasedPageModule,
    ComponentsModule,
    IonicPageModule.forChild(PersonalPage),
  ],
})
export class PersonalPageModule {}
