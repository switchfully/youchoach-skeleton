import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ICoach} from './ICoach';
import {environment} from '../../environments/environment';
import {ICoachList} from "./ICoachList";
import {map} from "rxjs/operators";

@Injectable({
  providedIn: 'root'
})
export class CoachService {
  readonly url = `${environment.backendUrl}/users`;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  constructor(private http: HttpClient) {
  }

  getCoach(): Observable<ICoach> {
    return this.http.get<ICoach>(this.url + '/coach/profile');
  }

  getCoachByEmail(id: string): Observable<ICoach> {
   // return  this.getAllCoaches().pipe( map((coaches: ICoachList) => coaches.coaches.find(i =>
   //   i.email
   //   === id)));
    return this.http.get<ICoach>(this.url + `/coach/profile/${id}`);
  }

  updateCoachInformation(coach: ICoach): Observable<ICoach> {
    return this.http.put<ICoach>(this.url + '/coach/profile', coach, this.httpOptions);
  }
  getAllCoaches(): Observable<ICoachList> {
    return this.http.get<ICoachList>(this.url + '/find-coach');
  }

}
