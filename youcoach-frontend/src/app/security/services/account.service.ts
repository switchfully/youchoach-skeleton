import { Injectable } from '@angular/core';
import {ICoachee} from "../interfaces/ICoachee";
import {Observable} from "rxjs";
import {ICoacheeRegisterResult} from "../interfaces/ICoacheeRegisterResult";
import {IRequestPasswordResetToken} from "../interfaces/IRequestPasswordResetToken";
import {IPasswordChange} from "../interfaces/IPasswordChange";
import {IPasswordChangeResult} from "../interfaces/IPasswordChangeResult";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AccountService {
  readonly url = `${environment.backendUrl}/security/account`;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'https://youcoach-test.herokuapp.com/'
    }),
  };

  constructor(private http: HttpClient) { }

  register(coachee: ICoachee): Observable<ICoacheeRegisterResult> {
    return this.http.post<ICoacheeRegisterResult>(this.url, coachee, this.httpOptions);
  }

  requestPasswordResetToken(tokenRequest: IRequestPasswordResetToken): Observable<any> {
    return this.http.post<any>(this.url + '/password/request-reset', tokenRequest, this.httpOptions);
  }

  performPasswordReset(passwordChange: IPasswordChange): Observable<IPasswordChangeResult> {
    return this.http.post<any>(this.url + '/password', passwordChange, this.httpOptions);
  }
}
