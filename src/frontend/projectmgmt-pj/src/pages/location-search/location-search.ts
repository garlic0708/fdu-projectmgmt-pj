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


  returnLoaction(item: lbs_msg){
    this.navCtrl.push(EventsNearbyPage,{
      content: item,
      returnType: 1
    });
  }
}
