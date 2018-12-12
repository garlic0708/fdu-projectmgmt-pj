import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { HomePage } from "../home/home";
import { EventsNearbyPage } from "../events-nearby/events-nearby";
import { NotifListPage } from "../notif-list/notif-list";
import { PersonalPage } from "../personal/personal";
import { SuperTabsController } from "ionic2-super-tabs";
import { NewEventPage } from "../new-event/new-event";
import { NotifProvider } from "../../providers/notif/notif";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

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
  pages: Array<{title: string, component: any, id ?: any, tabIcon: string, badge?: Observable<number> }>;
  config: any = {
    allowElementScroll: true,
  };

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private superTabsCtrl: SuperTabsController,
              private notif: NotifProvider,) {
    // set our app's pages
    this.pages = [
      { title: '主页', component: HomePage, tabIcon: 'home', id: 'home' },
      { title: '附近', component: EventsNearbyPage, tabIcon: 'pin', id: 'nearby' },
      { title: '新建活动', component: NewEventPage, tabIcon: 'add' },
      { title: '消息列表', component: NotifListPage, tabIcon: 'notifications',
        badge: this.notif.unreadNumber.pipe(map(n => n ? n : null)) },
      { title: '我的', component: PersonalPage, tabIcon: 'person' },
    ];
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad StartupPage');
    this.notif.loadNotif();
    this.superTabsCtrl.enableTabSwipe('nearby', false,)
  }

}
