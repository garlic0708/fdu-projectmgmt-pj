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
var self_location=[];
var map;
function show_self(){
  var marker = new AMap.Marker({
    position: new AMap.LngLat(self_location[0]*1, self_location[1]*1),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
  });

// 将创建的点标记添加到已有的地图实例：
  map.add(marker);
  map.setZoomAndCenter(17, [self_location[0]*1, self_location[1]*1]);
}
@Component({
  selector: 'page-events-nearby',
  templateUrl: 'events-nearby.html',
})
export class EventsNearbyPage {
  @ViewChild('map_container') map_container: ElementRef;


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
    map = new AMap.Map(this.map_container.nativeElement, {
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
    map.add(marker);
    map.setZoomAndCenter(17, [x*1, y*1]);

  }
  showSelfLocation(){
    var geolocation = new AMap.Geolocation({
      enableHighAccuracy: true,//是否使用高精度定位，默认:true
      timeout: 10000,          //超过10秒后停止定位，默认：5s
      buttonPosition:'RB',    //定位按钮的停靠位置
      buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
      zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点

    });
    map.addControl(geolocation);
    geolocation.getCurrentPosition(function(status,result){
      if(status=='complete'){
        console.log(result);
        var a =JSON.stringify(result);
        a = JSON.parse(a);
        console.log(a);
        console.log(a.position.N);
        self_location=[];
        self_location.push(a.position.N,a.position.O);
        console.log(self_location);
        show_self();
      }
    });
  }

}
