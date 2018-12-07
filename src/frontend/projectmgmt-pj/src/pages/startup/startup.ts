import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { HomePage } from "../home/home";
import { EventsNearbyPage } from "../events-nearby/events-nearby";
import { NotifListPage } from "../notif-list/notif-list";
import { PersonalPage } from "../personal/personal";
import { SuperTabsController } from "ionic2-super-tabs";

/**
 * Generated class for the StartupPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-startup',
  templateUrl: 'startup.html',
})
export class StartupPage {
  pages: Array<{title: string, component: any, id ?: any}>;
  config: any = {
    allowElementScroll: true,
  };

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private superTabsCtrl: SuperTabsController,) {
    // set our app's pages
    this.pages = [
      { title: '主页', component: HomePage },
      { title: '附近', component: EventsNearbyPage, id: 'nearby' },
      { title: '消息列表', component: NotifListPage },
      { title: '我的', component: PersonalPage },
    ];
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad StartupPage');
    this.superTabsCtrl.enableTabSwipe('nearby', false,)
  }

}
