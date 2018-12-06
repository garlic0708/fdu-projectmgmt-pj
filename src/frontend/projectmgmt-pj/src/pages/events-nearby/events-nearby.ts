import { Component, ViewChild, ElementRef } from '@angular/core';
import {IonicPage, NavController, NavParams, Searchbar} from 'ionic-angular';
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {lbs_msg_psg} from "../../lbs_msg_psg";
import {Observable} from "rxjs/Observable";
import {lbs_msg} from "../../lbs_msg";
import {location_array } from "../../location_array";
import { LocationSearchPage } from "../location-search/location-search";
import { ShowNearByPage } from "../show-near-by/show-near-by";
import {DataProvider} from "../../providers/data/data";
import {EventPoint} from "../../event_point";


/**
 * Generated class for the EventsNearbyPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */


declare var AMap;
var self_location=[];
var map;
var focus_marker=null;

function show_self(){
  if(focus_marker!=null){
    map.remove(focus_marker);
  }
  focus_marker = new AMap.Marker({
    position: new AMap.LngLat(self_location[0]*1, self_location[1]*1),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
  });

// 将创建的点标记添加到已有的地图实例：
  map.add(focus_marker);
  map.setZoomAndCenter(17, [self_location[0]*1, self_location[1]*1]);
  console.log(self_location);
}
@IonicPage()
@Component({
  selector: 'page-events-nearby',
  templateUrl: 'events-nearby.html',
})
export class EventsNearbyPage {
  @ViewChild('map_container') map_container: ElementRef;

  searchbar_value: string="";
  markers=[];
  searchQuery: string = '';
  items: lbs_msg[];
  searchNum; number;
  returnType: number;
  search_content: lbs_msg;
  nearby_array: location_array;
  event_plist: EventPoint[];


  initializeItems() {
    this.items = [

    ];
  }

  constructor(public navCtrl: NavController, public navParams: NavParams, public http: HttpClient,
  public data: DataProvider) {
    this.searchNum=0;
  }

  goLocation_search(){
    this.navCtrl.push(LocationSearchPage,{
      default_value: this.searchbar_value
    });
    // this.nearby_array= new location_array();
    // this.nearby_array.size_num=3;
    // this.nearby_array.x_list=[110.11111,110.1112,110.1113];
    // this.nearby_array.y_list=[30.1111,30.1111,30.1111];
    //
    // this.navCtrl.push(ShowNearByPage,{
    //   location_array: this.nearby_array
    // });
  }


  ionViewDidLoad() {
    map = new AMap.Map(this.map_container.nativeElement, {
      view: new AMap.View2D({//创建地图二维视口
        zoom: 11, //设置地图缩放级别
        rotateEnable: true,
        showBuildingBlock: true
      })
    });

    //map.on('moveend', mapMoveend);
    map.on('movestart',function () {
      console.log("start move");
    });
    map.on('mapmove', function () {
      console.log("move");
    });
    map.on('moveend', function () {
      console.log("end move");
      var bounds = map.getBounds();
      console.log(bounds);
      var zoom = map.getZoom(); //获取当前地图级别
      var center = map.getCenter(); //获取当前地图级别
      console.log(zoom);
      console.log(center);
      data.getEvent(self_location).subscribe(data=>{
        for(var i=0;i<data.length;i++){
          var marker1 = new AMap.Marker({
            position: new AMap.LngLat(data[i].x, data[i].y),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          });
          marker1.content=data[i].id;
          marker1.on('click',function (e) {
            console.log(e.target.content);
            console.log(e.target.getPosition());
          });
          map.add(marker1);
        }
        console.log("load event near by finish!");
      });

    });
    console.log('ionViewDidLoad EventsNearbyPage');
    this.showSelfLocation();
    const data = this.data;
    setTimeout(function () {
      data.getEvent(self_location).subscribe(data=>{
        for(var i=0;i<data.length;i++){
          var marker1 = new AMap.Marker({
            position: new AMap.LngLat(data[i].x, data[i].y),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
          });
          marker1.content=data[i].id;
          marker1.on('click',function (e) {
            console.log(e.target.content);
            console.log(e.target.getPosition());
          });
          map.add(marker1);
        }
        console.log("load event near by finish!");
      });
    }, '10');
  }


  showLoaction(item: lbs_msg){
    console.log(this.searchNum);
    if(this.searchNum!==0){
      console.log(this.markers[this.searchNum-1]);
      this.markers[this.searchNum-1].setMap(null);
    }
    console.log(item.location.indexOf(","));
    var x =item.location.substr(0,item.location.indexOf(","));
    var y =item.location.substr(item.location.indexOf(",")+1,item.location.length-item.location.indexOf(",")-1);
    console.log(x);
    console.log(y);
    var marker = new AMap.Marker({
      map:map,
      position: new AMap.LngLat(1*x, y*1),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
    });
    this.markers.push(marker);
    this.searchNum++;

// 将创建的点标记添加到已有的地图实例：
//     map.add(marker);
    map.setZoomAndCenter(18, [x*1, y*1]);
    this.initializeItems();
  }
  showSelfLocation(){
    var geolocation = new AMap.Geolocation({
      enableHighAccuracy: true,//是否使用高精度定位，默认:true
      timeout: 10000,          //超过10秒后停止定位，默认：5s
      showButton: false,
      buttonPosition:'RB',    //定位按钮的停靠位置
      buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
      zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点
    });
    map.addControl(geolocation);
    geolocation.getCurrentPosition(function(status,result){
      if(status=='complete'){
        var a =JSON.stringify(result);
        a = JSON.parse(a);
        self_location=[];
        self_location.push(a.position.P,a.position.O);
        console.log(self_location);
        //show_self();
        if(focus_marker!=null){
          map.remove(focus_marker);
        }
        focus_marker = new AMap.Marker({
          map: map,
          icon: '//a.amap.com/jsapi_demos/static/demo-center/icons/dir-via-marker.png',
          position: new AMap.LngLat(self_location[0]*1, self_location[1]*1),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
        });
        focus_marker.content=-1;
        focus_marker.on('click',function (e) {
          console.log(e.target.content);
          console.log(e.target.getPosition());
        });
// 将创建的点标记添加到已有的地图实例：
//         map.add(focus_marker);
        map.setZoomAndCenter(17, [self_location[0]*1, self_location[1]*1]);
      }
    });
  }

  jumpto(){
    this.navCtrl.push(ShowNearByPage,{});
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
}
