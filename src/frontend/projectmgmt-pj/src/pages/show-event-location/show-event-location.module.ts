import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ShowEventLocationPage } from './show-event-location';
import { ComponentsModule } from "../../components/components.module";

@NgModule({
  declarations: [
    ShowEventLocationPage,
  ],
  imports: [
    IonicPageModule.forChild(ShowEventLocationPage),
    ComponentsModule,
  ],
})
export class ShowEventLocationPageModule {}
