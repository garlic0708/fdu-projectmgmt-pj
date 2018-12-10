import { Component } from '@angular/core';
import { App, IonicPage, NavController, NavParams, ToastController } from 'ionic-angular';
import { ImagePicker } from "@ionic-native/image-picker";
import * as moment from 'moment';
import { Moment } from "moment";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import { DataProvider } from "../../providers/data/data";
import { Observable } from "rxjs";
import { Poi } from "../../components/amap/poi";
import { LocationSearchPage } from "../location-search/location-search";
import { SuperTabsController } from "ionic2-super-tabs";
import { EventDetailPage } from "../event-detail/event-detail";

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

  eventName: string;
  content: string;

  eventTagList: Observable<{ id: number, name: string }[]>;
  eventTagsChosen: number[] = [];

  _startTime: Moment;
  _endTime: Moment;

  startTimeMinLimit: string;
  endTimeMinLimit: string;
  timeMaxLimit: string;

  address: Poi;

  creditLimit: number;

  lowerBoundEnabled: boolean;
  upperBoundEnabled: boolean;

  private _lowerBound: number;
  private _upperBound: number;

  fallBackImage = './assets/imgs/logo.png';
  eventImage: string;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private imagePicker: ImagePicker,
              private loading: LoadingCoverProvider,
              private data: DataProvider,
              private toast: ToastController,
              private tabsCtrl: SuperTabsController,) {
    this.initializeFields();

    [this.eventTagList] = this.loading.fetchData(this.data.getEventTagList());
  }

  private initializeFields() {
    this.eventName = '';
    this.content = '';
    this.eventTagsChosen.length = 0;
    this._startTime = moment().add(1, "d").hour(18).minute(0).second(0);
    this._endTime = this._startTime.clone().add(2, "h");

    const anHourLater = moment().add(1, "h");
    this.startTimeMinLimit = anHourLater.format();
    this.endTimeMinLimit = anHourLater.add(1, "minute").format();
    this.timeMaxLimit = anHourLater.add(5, "y").format("YYYY");

    this.address = null;
    this.creditLimit = 0;

    this._lowerBound = 5;
    this._upperBound = 7;
    this.lowerBoundEnabled = true;
    this.upperBoundEnabled = true;

    this.eventImage = null;
  }

  selectAddress() {
    new Promise<Poi>(resolve => {
      this.appCtrl.getRootNavs()[0].push(LocationSearchPage, { resolveLocation: resolve })
    }).then(address => this.address = address)
  }

  get startTime() {
    return this._startTime.format()
  }

  set startTime(time) {
    console.log(time);
    this._startTime = moment.max(moment(time), moment().add(1, "h"));
    if (!this._startTime.isBefore(this._endTime))
      this._endTime = this._startTime.clone().add(2, "h")
  }

  get endTime() {
    return this._endTime.format()
  }

  set endTime(time) {
    const now = moment();
    this._endTime = moment.max(moment(time), now.clone().add(1, "minute").add(1, "h"));
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

  selectImage() {
    this.imagePicker.getPictures({ maximumImagesCount: 1 }).then(
      ([result]) => {
        this.eventImage = result;
      }
    )
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad NewEventPage');
  }

  submit() {
    const { eventName, content } = this;
    const [addressPx, addressPy] = Poi.getLnglat(this.address);
    this.data.addEvent(this.eventImage || this.fallBackImage, {
      eventName, content, startTime: this._startTime, endTime: this._endTime,
      lowerLimit: this.lowerBoundEnabled ? this.lowerBound : null,
      upperLimit: this.upperBoundEnabled ? this.upperBound : null,
      poiId: this.address.id,
      addressName: this.address.name,
      addressLocation: this.address.address,
      addressPx, addressPy,
      creditLimit: this.creditLimit,
      tags: this.eventTagsChosen,
    }).then(
      (x: { message: string }) => {
        this.initializeFields();
        this.tabsCtrl.slideTo('home');
        this.appCtrl.getRootNavs()[0].push(EventDetailPage, { eventId: +x.message })
      },
      (err: { status: string }) => {
        this.toast.create({ message: err.status, duration: 1500, }).present();
      })
  }

}
