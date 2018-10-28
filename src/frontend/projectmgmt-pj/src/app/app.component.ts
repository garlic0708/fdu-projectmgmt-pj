import { Component, ViewChild } from '@angular/core';

import { Platform, Nav } from 'ionic-angular';

import { StatusBar } from '@ionic-native/status-bar';
import { SplashScreen } from '@ionic-native/splash-screen';
import { HomePage } from "../pages/home/home";
import { EventsNearbyPage } from "../pages/events-nearby/events-nearby";
import { NotifListPage } from "../pages/notif-list/notif-list";
import { PersonalPage } from "../pages/personal/personal";
import { StartupPage } from "../pages/startup/startup";


@Component({
  templateUrl: 'app.html'
})
export class MyApp {
  rootPage: any = StartupPage;

  constructor(
    public platform: Platform,
    public statusBar: StatusBar,
    public splashScreen: SplashScreen
  ) {
    this.initializeApp();
  }

  initializeApp() {
    this.platform.ready().then(() => {
      // Okay, so the platform is ready and our plugins are available.
      // Here you can do any higher level native things you might need.
      this.statusBar.styleDefault();
      this.splashScreen.hide();
    });
  }
}
