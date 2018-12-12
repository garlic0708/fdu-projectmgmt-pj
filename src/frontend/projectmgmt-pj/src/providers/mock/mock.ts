import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from '@angular/common/http';
import { Injectable} from '@angular/core';
import { Observable } from "rxjs";
import { escapeRegExp } from "@angular/compiler/src/util";
import { mockData } from "./mock-data";
import { of } from "rxjs/observable/of";
import { delay, mapTo } from "rxjs/operators";

/*
  Generated class for the MockProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class MockProvider implements HttpInterceptor {
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // req =  new HttpRequest(<any>req.method, `${url}${req.url}`);
    // return next.handle(req)
    let url = req.url;
    console.log(url);
    const idx = req.url.indexOf('?');
    if (idx !== -1)
      url = req.url.substring(0, idx);
    const key = `${req.method} ${url}`;
    const mockDatum = mockData[
      Object.keys(mockData).find(k => {
        k = escapeRegExp(k);
        k = k.replace(/:[A-Za-z]*(\\\/|$)/, "\\d+$1");
        // console.log(k, new RegExp(k), key);
        return new RegExp(k).test(key);
      })];
    const response = {
      status: 200,
    };
    if (mockDatum && mockDatum !== true)
      response['body'] = mockDatum;
    console.log(mockDatum);
    return mockDatum ?
      of(null).pipe(mapTo(new HttpResponse(response)), delay(500))
      : next.handle(req);
  }

}
