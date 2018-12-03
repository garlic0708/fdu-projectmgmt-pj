import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsJoinPage } from './events-join';

@NgModule({
  declarations: [
    EventsJoinPage,
  ],
  imports: [
    IonicPageModule.forChild(EventsJoinPage),
  ],
})
export class EventsJoinPageModule {}
