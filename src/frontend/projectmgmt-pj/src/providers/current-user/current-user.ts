import { Injectable } from '@angular/core';
import { AuthService, StorageType } from 'ng2-ui-auth';
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { ApiRedirectProvider } from "../api-redirect/api-redirect";
import { DataProvider } from "../data/data";

/*
  Generated class for the CurrentUserProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/

export type User = {
  email: string, id: number, nickname: string, credit: number,
};

@Injectable()
export class CurrentUserProvider {

  currentUser: User = null;

  private refreshUrl = '/refresh';
  private changePasswordUrl = '/auth/update';
  private changeNicknameUrl = '/api/user/updateName';
  private changeAvatarUrl = '/api/user/updateImg';

  constructor(public auth: AuthService, private http: ApiRedirectProvider,
              private data: DataProvider,) {
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

  changeNickname(nickname): Observable<any> {
    const formData = new FormData();
    formData.append('nickname', nickname);
    console.log(formData);
    return this.http.put(this.changeNicknameUrl, formData)
      .pipe(tap(() => this.currentUser.nickname = nickname))
  }

  changeAvatar(imageUri: string): Promise<any> {
    return this.data.getBlobFromUri(imageUri)
      .then(blob => {
        const formData = new FormData();
        formData.append('img', blob);
        return this.http.post(this.changeAvatarUrl, formData).toPromise()
      })
  }

}
