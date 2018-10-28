import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { HomePageModule } from "../pages/home/home.module";
import { EventDetailPageModule } from "../pages/event-detail/event-detail.module";
import { EventsNearbyPageModule } from "../pages/events-nearby/events-nearby.module";
import { NotifListPageModule } from "../pages/notif-list/notif-list.module";
import { PersonalPageModule } from "../pages/personal/personal.module";
import { SuperTabsModule } from "ionic2-super-tabs";

@NgModule({
  declarations: [
    MyApp,
  ],
  imports: [
    HomePageModule,
    EventDetailPageModule,
    EventsNearbyPageModule,
    NotifListPageModule,
    PersonalPageModule,
    BrowserModule,
    IonicModule.forRoot(MyApp),
    SuperTabsModule.forRoot(),
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
