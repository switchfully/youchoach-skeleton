import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {ICoachee} from "../../security/register/icoachee";
import {Observable} from "rxjs";
import {ICoacheeRegisterResult} from "../../security/interfaces/ICoacheeRegisterResult";
import {IMember} from "../../profile/interfaces/IMember";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  readonly url = `${environment.backendUrl}/users`;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'https://youcoach-test.herokuapp.com/'
    }),

  };

  constructor(private http: HttpClient) {
  }

  getAllProfiles(): Observable<IMember[]> {
    return this.http.get<IMember[]>(this.url, this.httpOptions);
  }
}
