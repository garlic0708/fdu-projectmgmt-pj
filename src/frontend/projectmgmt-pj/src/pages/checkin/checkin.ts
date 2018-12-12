import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams, App, ToastController } from 'ionic-angular';
import { Observable } from "rxjs";
import { DataProvider } from "../../providers/data/data";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";

/**
 * Generated class for the CheckinPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-checkin',
  templateUrl: 'checkin.html',
})
export class CheckinPage {

  personItems: Observable<any[]>;
  eventId: number;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider,
              private toast: ToastController,) {
    this.eventId = this.navParams.get('eventId');
    console.log('-------------------', this.eventId);
    [this.personItems] =
      this.loading.fetchData(this.data.getEventCheckinList(this.eventId));
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad RegisterPage');
  }

  change(personItem) {
    personItem.loading = true;
    this.data.checkin(this.eventId, personItem.uid, personItem.checked)
      .finally(() => personItem.loading = false)
      .subscribe(
        () => personItem.checked = !personItem.checked,
        () => this.toast.create({ message: '网络错误', duration: 1500 }).present(),
      )
  }

  getAvatarUrl(userId) {
    return this.data.getAvatarUrl(userId)
  }

}
