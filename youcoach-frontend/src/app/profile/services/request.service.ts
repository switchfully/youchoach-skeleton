import { Injectable } from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {ICoach, ITopic} from "../interfaces/ICoach";

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

  changeTopics(profileId: number, request: string): Observable<Object>{
    return this.http.post<Object>(this.url + `/change-topics`, {profileId, request}, this.httpOptions);
  }

  becomeACoach(profileId: number, request: string): Observable<Object> {
    return this.http.post<Object>(this.url + `/become-a-coach`, {profileId, request}, this.httpOptions);
  }
}
