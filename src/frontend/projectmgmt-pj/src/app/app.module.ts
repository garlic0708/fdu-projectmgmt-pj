import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { HttpModule } from "@angular/http";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { HomePageModule } from "../pages/home/home.module";
import { EventDetailPageModule } from "../pages/event-detail/event-detail.module";
import { EventsNearbyPageModule } from "../pages/events-nearby/events-nearby.module";
import { NotifListPageModule } from "../pages/notif-list/notif-list.module";
import { PersonalPageModule } from "../pages/personal/personal.module";
import { SuperTabsModule } from "ionic2-super-tabs";
import { StartupPageModule } from "../pages/startup/startup.module";
import { LocationSearchPage } from "../pages/location-search/location-search";
import { ShowEventLocationPage } from "../pages/show-event-location/show-event-location";
import { DataProvider } from "../providers/data/data";
import { MockProvider } from "../providers/mock/mock";
import { LocationSearchPageModule } from "../pages/location-search/location-search.module";
import { ShowEventLocationPageModule } from "../pages/show-event-location/show-event-location.module";
import { AMapApiProvider } from '../providers/amap-api/amap-api';

@NgModule({
  declarations: [
    MyApp,
  ],
  imports: [
    StartupPageModule,
    LocationSearchPageModule,
    ShowEventLocationPageModule,
    BrowserModule,
    HttpClientModule,
    IonicModule.forRoot(MyApp),
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MockProvider,
      multi: true,
    },
    StatusBar,
    SplashScreen,
    DataProvider,
    AMapApiProvider,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
  ]
})
export class AppModule {
}
