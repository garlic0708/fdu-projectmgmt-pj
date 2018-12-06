import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { ShowNearByPage } from './show-near-by';

@NgModule({
  declarations: [
    ShowNearByPage,
  ],
  imports: [
    IonicPageModule.forChild(ShowNearByPage),
  ],
})
export class ShowNearByPageModule {}
