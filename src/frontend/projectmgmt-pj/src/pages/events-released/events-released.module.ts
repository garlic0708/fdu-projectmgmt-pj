import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { EventsReleasedPage } from './events-released';

@NgModule({
  declarations: [
    EventsReleasedPage,
  ],
  imports: [
    IonicPageModule.forChild(EventsReleasedPage),
  ],
})
export class EventsReleasedPageModule {}
