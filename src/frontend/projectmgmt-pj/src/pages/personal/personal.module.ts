import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { PersonalPage } from './personal';
import {IonicImageLoader} from "ionic-image-loader";

@NgModule({
  declarations: [
    PersonalPage,
  ],
  imports: [
    IonicImageLoader,
    IonicPageModule.forChild(PersonalPage),
  ],
})
export class PersonalPageModule {}
