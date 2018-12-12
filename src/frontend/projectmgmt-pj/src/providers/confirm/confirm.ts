import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AlertController } from "ionic-angular";

/*
  Generated class for the ConfirmProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class ConfirmProvider {

  constructor(private alert: AlertController) {
    console.log('Hello ConfirmProvider Provider');
  }

  confirm(message: string): Promise<void> {
    return new Promise<void>(resolve => {
      this.alert.create({
        title: message,
        buttons: [
          { text: '确定', handler: () => resolve() },
          { text: '取消', role: 'cancel' },
        ]
      }).present()
    })
  }

}
