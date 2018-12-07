import { BrowserModule } from '@angular/platform-browser';
import { NgModule, ErrorHandler } from '@angular/core';
import { IonicApp, IonicModule, IonicErrorHandler } from 'ionic-angular';
import { MyApp } from './app.component';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { StartupPageModule } from "../pages/startup/startup.module";
import { MockProvider } from '../providers/mock/mock';
import { DataProvider } from "../providers/data/data";
import { IonicImageLoader } from "ionic-image-loader";
import { LoadingCoverProvider } from '../providers/loading-cover/loading-cover';
import { ComponentsModule } from "../components/components.module";
import { ImagePicker } from "@ionic-native/image-picker";



@NgModule({
  declarations: [
    MyApp
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    StartupPageModule,
    LocationSearchPageModule,
    ShowEventLocationPageModule,
    BrowserModule,
    IonicImageLoader.forRoot(),
    HttpClientModule,
  ],
  bootstrap: [IonicApp],
  entryComponents: [
    MyApp
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: MockProvider,
      multi: true,
    },
    DataProvider,
    ImagePicker,
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    LoadingCoverProvider,
  ]
})
export class AppModule {
}
