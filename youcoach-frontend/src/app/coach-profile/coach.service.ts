import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ICoach} from './ICoach';
import {ICoachList} from "./ICoachList";

@Injectable({
  providedIn: 'root'
})
export class CoachService {
  readonly url = 'http://localhost:8080/users';

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  constructor(private http: HttpClient) {
  }

  getCoach(): Observable<ICoach> {
    return this.http.get<ICoach>(this.url + '/coach/profile');
  }

  getAllCoaches(): Observable<ICoachList> {
    return this.http.get<ICoachList>(this.url + '/find-coach');
  }

}
