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
import { Ng2UiAuthModule } from 'ng2-ui-auth';
import { ImagePicker } from "@ionic-native/image-picker";
import { CurrentUserProvider } from '../providers/current-user/current-user';
import { LoginPageModule } from "../pages/login/login.module";
import { ChangePasswordPageModule } from "../pages/change-password/change-password.module";


@NgModule({
  declarations: [
    MyApp,
  ],
  imports: [
    IonicModule.forRoot(MyApp),
    LoginPageModule,
    StartupPageModule,
    BrowserModule,
    IonicImageLoader.forRoot(),
    HttpClientModule,
    Ng2UiAuthModule.forRoot({
      signupUrl: '/auth/register',
    }),
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
    DataProvider,
    ImagePicker,
    StatusBar,
    SplashScreen,
    { provide: ErrorHandler, useClass: IonicErrorHandler },
    LoadingCoverProvider,
    CurrentUserProvider,
  ]
})
export class AppModule {
}
