import { Injectable } from '@angular/core';
import { AuthService, StorageType } from 'ng2-ui-auth';
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

/*
  Generated class for the CurrentUserProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class CurrentUserProvider {

  currentUser: { email: string, picture: string, nickname: string } | null = null;

  private refreshUrl = '/refresh';

  constructor(public auth: AuthService, private http: HttpClient) {
    console.log('Hello CurrentUserProvider Provider');
  }

  refresh(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      if (this.auth.isAuthenticated())
        this.http.get<{ token: string }>(this.refreshUrl).subscribe({
          error: err => reject(err.message),
          next: ({ token }) => {
            this.auth.setToken(token);
            resolve()
          }
        });
      else
        reject()
    })
  }

  login(email: string, password: string, rememberMe: boolean = true): Promise<any> {
    this.auth.setStorageType(rememberMe ? StorageType.LOCAL_STORAGE : StorageType.MEMORY);
    return new Promise<any>((resolve, reject) => {
      this.auth.login({ email, password }).subscribe({
        error: (err) => reject(err),
        next: () => {
          this.currentUser = this.auth.getPayload();
          resolve()
        },
      })
    })
  }

  register(email: string, password: string, nickname?: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      this.auth.signup({ email, password, nickname }).subscribe({
        error: err => reject(err.message),
        next: () => resolve(),
      })
    })
  }

  logout(): Observable<void> {
    return this.auth.logout()
  }

}
