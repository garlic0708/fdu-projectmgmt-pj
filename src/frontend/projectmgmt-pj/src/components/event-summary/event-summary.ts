import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Observable } from "rxjs";
import { EventPreview } from "../../pages/home/event-preview";
import { App, NavController, NavParams } from "ionic-angular";
import { DataProvider } from "../../providers/data/data";
import { LoadingCoverProvider } from "../../providers/loading-cover/loading-cover";
import { EventDetailPage } from "../../pages/event-detail/event-detail";

/**
 * Generated class for the EventSummaryComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'event-summary',
  templateUrl: 'event-summary.html'
})
export class EventSummaryComponent {

  private button = 1;

  @Input() eventItems: Observable<EventPreview[]>;
  @Output() eventClicked = new EventEmitter<number>();

  constructor() {
  }

  showNotStarted() {
    this.button = 1;
  }

  showStarted() {
    this.button = 2;
  }

  showCanceled() {
    this.button = 3;
  }

  showEnded() {
    this.button = 4;
  }

  clickEvent(id) {
    this.eventClicked.emit(id)
  }

}
