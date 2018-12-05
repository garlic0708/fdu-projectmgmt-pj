import { Component } from '@angular/core';
import {IonicPage, NavController, NavParams, App} from 'ionic-angular';
import {Observable} from "rxjs";
import {DataProvider} from "../../providers/data/data";
import {LoadingCoverProvider} from "../../providers/loading-cover/loading-cover";

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

  personItems: Observable<any>;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
    console.log('-------------------', this.navParams.get('eventId'));
    [this.personItems] =
      this.loading.fetchData(this.data.getEventCheckin(this.navParams.get('eventId')));
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad RegisterPage');
  }

  change(personItem) {
    personItem.type = 1 - personItem.type;
  }

  todo() {

  }

}
