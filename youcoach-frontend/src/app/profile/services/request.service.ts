import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {ICoach} from "../interfaces/ICoach";

@Injectable({
  providedIn: 'root'
})
export class RequestService {

  readonly url = `${environment.backendUrl}/requests`;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  requestProfileChange(profileId: number): Observable<Object>{
    console.log(profileId);
    return this.http.post<Object>(this.url + `/profile-change`, {profileId}, this.httpOptions);
  }
}
