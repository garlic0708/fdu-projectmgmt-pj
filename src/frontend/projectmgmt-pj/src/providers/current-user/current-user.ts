import { Injectable } from '@angular/core';
import { AuthService, StorageType } from 'ng2-ui-auth';
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

/*
  Generated class for the CurrentUserProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/

export type User = {
  email: string, image: string, nickname: string, credit: number,
};

@Injectable()
export class CurrentUserProvider {

  currentUser: User = null;

  private refreshUrl = '/refresh';
  private changePasswordUrl = '/auth/update';

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
            this.setCurrentUser();
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
          this.setCurrentUser();
          console.log(this.currentUser);
          resolve()
        },
      })
    })
  }

  private setCurrentUser() {
    const payload = this.auth.getPayload();
    this.currentUser = {
      ...payload,
      email: payload.sub,
      image: payload.picture,
    };
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

  changePassword(f: { oldPassword: string, newPassword: string }): Observable<any> {
    return this.http.post(this.changePasswordUrl, {
      email: this.currentUser.email,
      ...f,
    })
  }

}
