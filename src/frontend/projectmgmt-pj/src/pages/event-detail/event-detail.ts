import { Component } from '@angular/core';
import { IonicPage, LoadingController, NavController, NavParams } from 'ionic-angular';
import { DataProvider } from "../../providers/data/data";
import { Observable } from "rxjs";

/**
 * Generated class for the EventDetailPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-event-detail',
  templateUrl: 'event-detail.html',
})
export class EventDetailPage {

  detail: Observable<any>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private data: DataProvider,
              private loading: LoadingController) {
    let loadingView = this.loading.create({content: '加载中……'});
    loadingView.present();
    this.detail = this.data.getDetail(this.navParams.get('eventId')).pipe(
      detail => {
        loadingView.dismiss();
        return detail
      }
    );
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad EventDetailPage');
  }

}
