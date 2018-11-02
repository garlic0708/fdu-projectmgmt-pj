import { Component, ViewChild, ElementRef } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {lbs_msg_psg} from "../../lbs_msg_psg";
import {Observable} from "rxjs/Observable";
import {lbs_msg} from "../../lbs_msg";

/**
 * Generated class for the EventsNearbyPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
declare var AMap;
declare var LocationPlugin;
@Component({
  selector: 'page-events-nearby',
  templateUrl: 'events-nearby.html',
})
export class EventsNearbyPage {
  @ViewChild('map_container') map_container: ElementRef;
  map: any;//地图对象

  searchQuery: string = '';
  items: lbs_msg[];

  initializeItems() {
    this.items = [

    ];
  }

  constructor(public navCtrl: NavController, public navParams: NavParams, public http: HttpClient) {
    this.initializeItems();
  }


  getItems(ev: any): Observable<lbs_msg_psg> {
    // Reset items back to all of the items
    this.initializeItems();

    // set val to the value of the searchbar
    const val = ev.target.value;

    // if the value is an empty string don't filter the items
    if (val && val.trim() != '') {
      const url = 'https://restapi.amap.com/v3/assistant/inputtips?output=json&city=021&citylimit=true&keywords='+val+'&key=93c4bd0fee3dd51c49b2f93b64749208';
      this.http.get<lbs_msg_psg>(url).subscribe(data=>{
        console.log(data);
        let re = new lbs_msg_psg();
        re = data;
        if(re.tips.length !==0 ){
          for(var i=0;i<10;i++){
            this.items.push(re.tips[i]);
          }
        }
      });
    }
  }


  ionViewDidLoad() {
    this.map = new AMap.Map(this.map_container.nativeElement, {
      view: new AMap.View2D({//创建地图二维视口
        zoom: 11, //设置地图缩放级别
        rotateEnable: true,
        showBuildingBlock: true
      })
    });
    console.log('ionViewDidLoad EventsNearbyPage');

  }

  showLoaction(item: lbs_msg){
    console.log(item.location.indexOf(","));
    var x =item.location.substr(0,item.location.indexOf(","));
    var y =item.location.substr(item.location.indexOf(",")+1,item.location.length-item.location.indexOf(",")-1);
    console.log(x);
    console.log(y);
    var marker = new AMap.Marker({
      position: new AMap.LngLat(x*1, y*1),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
    });

// 将创建的点标记添加到已有的地图实例：
    this.map.add(marker);
    this.map.setZoomAndCenter(17, [x*1, y*1]);

  }
  showSelfLocation(){
    LocationPlugin.getLocation(data=>{
      alert(JSON.stringify(data));
    },msg=>{
      alert(JSON.stringify(msg));
    });
  }
  onComplete(data) {
    console.log(data.position);
  }
  onError(data) {

  }

}
