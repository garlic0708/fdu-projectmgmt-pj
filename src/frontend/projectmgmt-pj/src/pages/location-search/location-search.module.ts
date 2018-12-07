import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { LocationSearchPage } from './location-search';
import { ComponentsModule } from "../../components/components.module";

@NgModule({
  declarations: [
    LocationSearchPage,
  ],
  imports: [
    IonicPageModule.forChild(LocationSearchPage),
    ComponentsModule,
  ],
})
export class LocationSearchPageModule {}
