import { Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild } from '@angular/core';
import { AMapApiProvider } from "../../providers/amap-api/amap-api";
import { Poi } from "./poi";

declare var AMap;
const geoLocationIcon = '//a.amap.com/jsapi_demos/static/demo-center/icons/dir-via-marker.png';

export type CardContent = {
  title: string;
  content?: string;
  showButton: boolean;
  buttonText: string;
  onButtonClick: () => any;
}

/**
 * Generated class for the AMapComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'amap',
  templateUrl: 'amap.html'
})
export class AMapComponent implements OnInit {

  @ViewChild('map_container') map_container: ElementRef;

  private searchBarValue: string = "";
  private markers = [];
  private inputTipList: Poi[];
  private inputTipPage: number = 1;
  private inputTipTriggerFlag = true;
  private itemTipLoading = false;
  private currentPOI: Poi = null;
  private address: { formattedAddress: string, pois: Poi[] } = null;
  private searchNum: number;
  private focus_marker = null;

  map: any;

  constructor(private aMapData: AMapApiProvider,) {
    console.log('Hello AMapComponent Component');
    this.searchNum = 0;
  }

  @Input() showSearchBar: boolean = true;
  @Input() showConfirmButton: boolean = false;
  @Input() tapToSelectPlace: boolean = false;
  @Input() enableCustomCardContent: boolean = false;
  @Input() customCardContent: CardContent = null;

  @Input() set showPOI(poi: Poi) {
    this.currentPOI = poi;
  }

  @Output() onMapMoved = new EventEmitter<void>();
  @Output() onSelectLocation = new EventEmitter<Poi>();
  @Output() onConfirmClicked = new EventEmitter<void>();

  ngOnInit(): void {
    this.map = new AMap.Map(this.map_container.nativeElement, {
      view: new AMap.View2D({//创建地图二维视口
        zoom: 11, //设置地图缩放级别
        rotateEnable: true,
        showBuildingBlock: true
      })
    });

    //this.map.on('moveend', mapMoveend);
    this.map.on('movestart', () => {
      console.log("start move");
    });
    this.map.on('mapmove', function () {
      console.log("move");
    });
    this.map.on('moveend', () => {
      console.log("end move");
      this.onMapMoved.emit();
    });
    this.map.on('touchstart', (e) => {
      console.log('touchstart event')
    });
    this.map.on('click', e => {
      if (this.tapToSelectPlace) {
        if (this.currentPOI === null && this.address === null)
          this.geoCodeAddress([e.lnglat.lng, e.lnglat.lat]);
        else
          this.clearDecorations();
      }
      if (this.enableCustomCardContent && this.customCardContent)
        this.clearDecorations();
      console.log('click event')
    });
    console.log('ionViewDidLoad EventsNearbyPage');
    if (this.currentPOI)
      this.showLocation(this.currentPOI);
    else
      this.showSelfLocation()
  }

  clearDecorations() {
    this.inputTipPage = 1;
    this.inputTipList = [];
    this.inputTipTriggerFlag = true;
    this.currentPOI = null;
    this.address = null;
    this.customCardContent = null;
    console.log('restore input tip')
  }

  geoCodeAddress(lnglat: number[]) {
    console.log(AMap, AMap.Geocoder);
    const geoCoder = new AMap.Geocoder({
      city: this.map.getAdcode(),
      extensions: 'all',
      batch: false,
    });
    geoCoder.getAddress(lnglat, (status, result) => {
      if (status === 'complete' && result.info === 'OK') {
        this.address = result.regeocode;
        this.showMarker(lnglat[0], lnglat[1]);
        console.log(this.address)
      }
    })
  }

  showLocation(item: Poi) {
    const [x, y] = Poi.getLnglat(item);
    console.log(x);
    console.log(y);
    this.showMarker(x, y);
    this.clearDecorations();
    this.currentPOI = item;
    this.onMapMoved.emit();
    this.onSelectLocation.emit(item)
  }

  private showMarker(x: any, y: any) {
    console.log(this.searchNum);
    if (this.searchNum !== 0) {
      console.log(this.markers[this.searchNum - 1]);
      this.markers[this.searchNum - 1].setMap(null);
    }
    const marker = new AMap.Marker({
      map: this.map,
      position: new AMap.LngLat(1 * x, y * 1),   // 经纬度对象，也可以是经纬度构成的一维数组[116.39, 39.9]
    });
    this.markers.push(marker);
    this.searchNum++;

    this.map.setZoomAndCenter(18, [x * 1, y * 1]);
  }

  showSelfLocation() {
    console.log('trying to position self');
    if (this.tapToSelectPlace || this.enableCustomCardContent)
      this.clearDecorations();
    const geolocation = new AMap.Geolocation({
      enableHighAccuracy: true,//是否使用高精度定位，默认:true
      timeout: 10000,          //超过10秒后停止定位，默认：5s
      showButton: false,
      buttonPosition: 'RB',    //定位按钮的停靠位置
      buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
      zoomToAccuracy: true,   //定位成功后是否自动调整地图视野到定位点
    });
    this.map.addControl(geolocation);

    // noinspection TypeScriptValidateJSTypes
    geolocation.getCurrentPosition((status, result) => {
      if (status == 'complete') {
        let a: any = JSON.stringify(result);
        a = JSON.parse(a);
        const selfLocation = [];
        selfLocation.push(a.position.P, a.position.O);
        console.log(selfLocation);
        this.map.setZoomAndCenter(17, [selfLocation[0] * 1, selfLocation[1] * 1]);
        console.log('set zoom and center when positioning self');
        this.onMapMoved.emit()
      }
    }, err => console.log(err));
  }

  getItems(ev: any) {
    // Reset items back to all of the items
    this.clearDecorations();

    // set val to the value of the searchbar
    this.searchBarValue = ev.target.value;

    // if the value is an empty string don't filter the items
    this.search();
  }

  private search() {
    console.log(this.map.getAdcode());
    if (this.searchBarValue && this.searchBarValue.trim() != '') {
      this.itemTipLoading = true;
      this.aMapData.getSearchList(this.searchBarValue, this.map.getAdcode(), this.inputTipPage).subscribe(tips => {
        if (tips.length === 0) this.inputTipTriggerFlag = false;
        console.log(tips);
        this.inputTipList = this.inputTipList.concat(tips);
        this.itemTipLoading = false;
        console.log('after concat', this.inputTipList)
      })
    }
  }

  loadMoreItems() {
    console.log('load more items', this.inputTipPage);

    if (this.inputTipTriggerFlag && !this.itemTipLoading) {
      this.inputTipPage += 1;
      this.search()
    }
  }

}
