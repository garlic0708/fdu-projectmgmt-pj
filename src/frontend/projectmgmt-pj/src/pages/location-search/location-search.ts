import {Component, ViewChild} from '@angular/core';
import {IonicPage, NavController, NavParams, Searchbar} from 'ionic-angular';
import {lbs_msg} from "../../lbs_msg";
import {Observable} from "rxjs/Observable";
import {lbs_msg_psg} from "../../lbs_msg_psg";
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable} from "rxjs/Observable";
import { EventsNearbyPage } from "../events-nearby/events-nearby";

/**
 * Generated class for the LocationSearchPage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-location-search',
  templateUrl: 'location-search.html',
})
export class LocationSearchPage {

  searchbar_value: string="";
  @ViewChild('searchbar') searchbar:Searchbar;

  constructor(public navCtrl: NavController, public navParams: NavParams, public http: HttpClient) {
    this.initializeItems();
    this.searchbar_value=this.navParams.get('default_value');
  }

  items: lbs_msg[];

  initializeItems() {
    this.items = [

    ];
  }

  ionViewDidLoad() {

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

  returnLoaction(item: lbs_msg){
    this.navCtrl.push(EventsNearbyPage,{
      content: item,
      returnType: 1
    });
  }
}
