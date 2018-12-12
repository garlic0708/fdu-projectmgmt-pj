import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsNearbyPage } from './events-nearby';
import { ComponentsModule } from "../../components/components.module";

@NgModule({
  declarations: [
    EventsNearbyPage,
  ],
  imports: [
    IonicPageModule.forChild(EventsNearbyPage),
    ComponentsModule,
  ],
})
export class EventsNearbyPageModule {}
