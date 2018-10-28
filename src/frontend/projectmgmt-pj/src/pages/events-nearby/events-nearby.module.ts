import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsNearbyPage } from './events-nearby';

@NgModule({
  declarations: [
    EventsNearbyPage,
  ],
  imports: [
    IonicPageModule.forChild(EventsNearbyPage),
  ],
})
export class EventsNearbyPageModule {}
