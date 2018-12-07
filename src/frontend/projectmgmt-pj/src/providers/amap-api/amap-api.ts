import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from "rxjs";
import { LBSMsgPsg } from "../../components/amap/lbs-msg-psg";
import { Poi } from "../../components/amap/poi";
import { map } from "rxjs/operators/map";

/*
  Generated class for the AMapApiProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class AMapApiProvider {

  searchListUrl = 'https://restapi.amap.com/v3/place/text';
  searchListParams = {
    output: 'json',
    citylimit: 'true',
    key: '93c4bd0fee3dd51c49b2f93b64749208',
    offset: '10',
  };

  constructor(public http: HttpClient) {
    console.log('Hello AMapApiProvider Provider');
  }

  getSearchList(keyword: string, adCode: string, page: number): Observable<Poi[]> {
    return this.http.get<LBSMsgPsg>(this.searchListUrl, {
      params: {
        ...this.searchListParams,
        city: adCode,
        keywords: keyword,
        page: page.toString(),
      }
    }).pipe(map(msg => msg.pois))
  }

}
