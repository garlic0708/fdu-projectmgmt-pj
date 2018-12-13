import { Component, Input, OnChanges } from '@angular/core';
import { BehaviorSubject, Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { ApiRedirectProvider } from "../../providers/api-redirect/api-redirect";

/**
 * Generated class for the ImgWithRequestComponent component.
 *
 * See https://angular.io/api/core/Component for more info on Angular
 * Components.
 */
@Component({
  selector: 'img-with-request',
  templateUrl: 'img-with-request.html'
})
export class ImgWithRequestComponent implements OnChanges {
  // This code block just creates an rxjs stream from the src
  // this makes sure that we can handle source changes
  // or even when the component gets destroyed
  // So basically turn src into src$
  @Input() private src: string;
  private src$ = new BehaviorSubject(this.src);

  ngOnChanges(): void {
    this.src$.next(this.src);
  }

  reload() {
    this.ngOnChanges()
  }

  // this stream will contain the actual url that our img tag will load
  // everytime the src changes, the previous call would be canceled and the
  // new resource would be loaded
  dataUrl$ = this.src$
    .filter(url => !!url)
    .switchMap(url => this.loadImage(url));

  // we need HttpClient to load the image
  constructor(private httpClient: ApiRedirectProvider) {
  }

  private loadImage(url: string): Observable<any> {
    return this.httpClient
    // load the image as a blob
      .get(url, { responseType: 'blob' })
      // create an object url of that blob that we can use in the src attribute
      .map(e => URL.createObjectURL(e))
  }

}
