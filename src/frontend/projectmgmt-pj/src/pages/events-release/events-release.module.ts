import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsReleasePage } from './events-release';

@NgModule({
  declarations: [
    EventsReleasePage,
  ],
  imports: [
    IonicPageModule.forChild(EventsReleasePage),
  ],
})
export class EventsReleasePageModule {}
