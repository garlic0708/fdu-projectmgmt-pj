import { Component, ViewChild } from '@angular/core';
import { App, InfiniteScroll, IonicPage, NavController, NavParams, Refresher, Slides } from 'ionic-angular';
import { EventDetailPage } from "../event-detail/event-detail";
import { Observable } from "rxjs";
import { DataProvider } from "../../providers/data/data";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import { EventPreview } from "./event-preview";
import { tap } from "rxjs/operators";

/**
 * Generated class for the HomePage page.
 *
 * See https://ionicframework.com/docs/components/#navigation for more info on
 * Ionic pages and navigation.
 */

@IonicPage()
@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
})
export class HomePage {

  slideItems: Observable<EventPreview[]>;
  flowItems: Observable<EventPreview[]>;

  searchItems: EventPreview[] = [];
  searchPage = 1;
  searchValue = '';
  searchHasMore = true;

  @ViewChild(Slides) slides: Slides;

  constructor(public navCtrl: NavController, public navParams: NavParams,
              private appCtrl: App,
              private data: DataProvider,
              private loading: LoadingCoverProvider) {
    this.reload();
  }

  private reload(refresher?: Refresher) {
    const [slideItems, flowItems] =
      this.loading.fetchData(this.data.getHomeSlides(), this.data.getHomeFlow());
    this.slideItems = slideItems.pipe(
      tap(() => {
        if (refresher) refresher.complete()
      })
    );
    this.flowItems = flowItems;
  }

  ionViewDidLoad() {
    console.log('ionViewDidLoad HomePage');
  }

  goToEventDetail(id) {
    this.appCtrl.getRootNavs()[0].push(EventDetailPage, { eventId: id })
  }

  getImageUrl(eventId) {
    return this.data.getEventImageUrl(eventId)
  }

  search(e) {
    this.searchPage = 1;
    this.searchValue = e.target.value;
    if (e.target.value)
      this.data.searchEvents(e.target.value, this.searchPage)
        .subscribe(data => this.searchItems = data);
    else this.searchItems = [];
  }

  loadMore(scroll: InfiniteScroll) {
    this.searchPage += 1;
    if (this.searchValue)
      this.data.searchEvents(this.searchValue, this.searchPage)
        .subscribe((data: EventPreview[]) => {
          if (!data.length) {
            this.searchHasMore = false;
          }
          this.searchItems = this.searchItems.concat(data);
          scroll.complete()
        })
  }

}
