import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { NewEventPage } from './new-event';
import { ComponentsModule } from "../../components/components.module";

@NgModule({
  declarations: [
    NewEventPage,
  ],
  imports: [
    IonicPageModule.forChild(NewEventPage),
    ComponentsModule,
  ],
})
export class NewEventPageModule {}
