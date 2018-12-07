import { Component, OnInit, ViewChild } from '@angular/core';
import { App, IonicPage, NavController, NavParams } from 'ionic-angular';
import { DataProvider } from "../../providers/data/data";
import { EventDetailPage } from "../event-detail/event-detail";
import { AMapComponent, CardContent } from "../../components/amap/amap";


/**
 * Generated class for the EventsNearbyPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */


declare var AMap;

@IonicPage()
@Component({
  selector: 'page-events-nearby',
  templateUrl: 'events-nearby.html',
})
export class EventsNearbyPage {

  @ViewChild(AMapComponent) amapContainer: AMapComponent;

  cardContent: CardContent = null;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              public data: DataProvider,
              private appCtrl: App,) {
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad EventsNearbyPage');
  }


  updateNearbyEvents() {
    const bounds = this.amapContainer.map.getBounds();
    console.log(bounds);
    console.log([bounds.northeast.lng, bounds.northeast.lat, bounds.southwest.lng, bounds.southwest.lat]);
    this.data.getEvent([bounds.northeast.lng, bounds.northeast.lat,
      bounds.southwest.lng, bounds.southwest.lat]).subscribe(data => {
      data.forEach(datum => {
        const marker1 = new AMap.Marker({
          position: new AMap.LngLat(datum.x, datum.y),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
        });
        const content: CardContent = {
          title: datum.name,
          showButton: true,
          buttonText: '活动详情',
          onButtonClick: () => this.goToDetail(datum.id)
        };
        marker1.on('click', (e) => {
          console.log(content);
          this.cardContent = content;
          this.amapContainer.map.setCenter([datum.x, datum.y])
        });
        this.amapContainer.map.add(marker1);
      });
      console.log("load event near by finish!");
    });
  }

  private goToDetail(id) {
    this.appCtrl.getRootNav().push(EventDetailPage, { eventId: id })
  }
}
