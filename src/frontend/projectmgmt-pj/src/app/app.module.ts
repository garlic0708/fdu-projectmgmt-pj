import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { HttpModule } from "@angular/http";
import { HttpClientModule } from "@angular/common/http";
import { HomePageModule } from "../pages/home/home.module";
import { EventDetailPageModule } from "../pages/event-detail/event-detail.module";
import { EventsNearbyPageModule } from "../pages/events-nearby/events-nearby.module";
import { NotifListPageModule } from "../pages/notif-list/notif-list.module";
import { PersonalPageModule } from "../pages/personal/personal.module";
import { SuperTabsModule } from "ionic2-super-tabs";
import { StartupPageModule } from "../pages/startup/startup.module";
import { LocationSearchPage } from "../pages/location-search/location-search";

@NgModule({
  declarations: [
    MyApp,
    LocationSearchPage,
  ],
  imports: [
    StartupPageModule,
    BrowserModule,
    HttpModule,
    HttpClientModule,
    IonicModule.forRoot(MyApp),
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
    LocationSearchPage,
  ],
  providers: [
    StatusBar,
    SplashScreen,HttpModule,HttpClientModule,
    {provide: ErrorHandler, useClass: IonicErrorHandler}
  ]
})
export class AppModule {}
