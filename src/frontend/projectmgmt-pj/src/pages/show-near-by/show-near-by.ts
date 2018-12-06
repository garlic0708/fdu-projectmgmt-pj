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
 * Generated class for the ShowNearByPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */


declare var AMap;
var map;
@IonicPage()
@Component({
  selector: 'page-show-near-by',
  templateUrl: 'show-near-by.html',
})
export class ShowNearByPage {
  @ViewChild('map_container') map_container: ElementRef;
  focus_item:lbs_msg;

  constructor(public navCtrl: NavController, public navParams: NavParams,public http: HttpClient) {

  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad ShowNearByPage');
    map = new AMap.Map(this.map_container.nativeElement, {
      view: new AMap.View2D({//创建地图二维视口
        zoom: 11, //设置地图缩放级别
        rotateEnable: true,
        showBuildingBlock: true
      })
    });
  }
  initializeItems() {
    this.items = [

    ];
  }
  showLoaction(item: lbs_msg){
    this.focus_item=item;
    console.log(item.location.indexOf(","));
    var x =item.location.substr(0,item.location.indexOf(","));
    var y =item.location.substr(item.location.indexOf(",")+1,item.location.length-item.location.indexOf(",")-1);
    console.log(x);
    console.log(y);
    var marker = new AMap.Marker({
      position: new AMap.LngLat(1*x, y*1),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
    });

// 将创建的点标记添加到已有的地图实例：
    map.add(marker);
    map.setZoomAndCenter(18, [x*1, y*1]);
    this.initializeItems();
  }

  ensureLocation(){
    if(this.focus_item==null){
      console.log("null focus_item")
    }else {
      console.log(this.focus_item)
    }
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
