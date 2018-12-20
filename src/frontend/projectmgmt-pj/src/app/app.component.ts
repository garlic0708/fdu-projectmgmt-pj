import { Component } from '@angular/core';

import { Platform } from 'ionic-angular';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { LoginPage } from "../pages/login/login";
import { StartupPage } from "../pages/startup/startup";
import { CurrentUserProvider } from "../providers/current-user/current-user";
import { Geolocation } from "@ionic-native/geolocation";


@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage: any;

  constructor(
    public platform: Platform,
    public statusBar: StatusBar,
    public splashScreen: SplashScreen,
    private currentUser: CurrentUserProvider,
    private geolocation: Geolocation,
  ) {
    this.currentUser.refresh().then(() => this.rootPage = StartupPage,
      () => this.rootPage = LoginPage);
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      this.statusBar.overlaysWebView(false);
      this.statusBar.styleLightContent();
      this.splashScreen.hide();

      this.geolocation.getCurrentPosition({
        enableHighAccuracy: false,
        timeout: 15000,
        maximumAge: 0
      }).then(resp => console.log(resp),
        err => console.log(err))
    });
  }
}
