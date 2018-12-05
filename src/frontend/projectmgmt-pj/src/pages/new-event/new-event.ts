import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { ImagePicker } from "@ionic-native/image-picker";
import * as moment from 'moment';
import { Moment } from "moment";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import { DataProvider } from "../../providers/data/data";
import { Observable } from "rxjs";

/**
 * Generated class for the NewEventPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-new-event',
  templateUrl: 'new-event.html',
})
export class NewEventPage {

  eventTagList: Observable<{ id: number, name: string }[]>;
  eventTagsChosen: number[] = [];

  _startTime: Moment = moment().add(1, "d").hour(18).minute(0);
  _endTime: Moment = this._startTime.clone().add(2, "h");

  startTimeMinLimit: string;
  endTimeMinLimit: string;
  timeMaxLimit: string;

  address: string | null = null;

  lowerBoundEnabled: boolean = true;
  upperBoundEnabled: boolean = true;

  private _lowerBound: number = 5;
  private _upperBound: number = 7;

  fallBackImage = './assets/imgs/logo.png';
  eventImage: string | null = null;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private imagePicker: ImagePicker,
              private loading: LoadingCoverProvider,
              private data: DataProvider) {
    const now = moment();
    this.startTimeMinLimit = now.format();
    this.endTimeMinLimit = now.add(1, "minute").format();
    this.timeMaxLimit = now.add(5, "y").format("YYYY");

    [this.eventTagList] = this.loading.fetchData(this.data.getEventTagList());
    this.test()
  }

  get startTime() {
    return this._startTime.format()
  }

  set startTime(time) {
    this._startTime = moment.max(moment(time), moment());
    if (!this._startTime.isBefore(this._endTime))
      this._endTime = this._startTime.clone().add(2, "h")
  }

  get endTime() {
    return this._endTime.format()
  }

  set endTime(time) {
    const now = moment();
    this._endTime = moment.max(moment(time), now.clone().add(1, "minute"));
    if (!this._endTime.isAfter(this._startTime))
      this._startTime = moment.max(this._endTime.clone().subtract(2, "h"), now)
  }

  get lowerBound() {
    return this._lowerBound
  }

  set lowerBound(bound) {
    this._lowerBound = bound;
    this._upperBound = Math.max(this._upperBound, bound)
  }

  get upperBound() {
    return this._upperBound
  }

  set upperBound(bound) {
    this._upperBound = bound;
    this._lowerBound = Math.min(this._lowerBound, bound)
  }

  chooseImage() {
    this.imagePicker.getPictures({ maximumImagesCount: 1 }).then(
      ([result]) => {
        this.eventImage = result;
      }
    )
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad NewEventPage');
  }

  test() {
    this.data.testUpload(this.fallBackImage).then(x => console.log(x))
  }

}
