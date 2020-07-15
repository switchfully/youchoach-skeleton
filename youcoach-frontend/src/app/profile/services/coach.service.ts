import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ICoach, ITopic} from '../interfaces/ICoach';
import {ICoachList} from '../interfaces/ICoachList';
import {environment} from "../../../environments/environment";

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

  getSpecificCoach(id: number): Observable<ICoach> {
    return this.http.get<ICoach>(this.url + `/coach/profile/${id}`);
  }

  updateCoachInformation(coach: ICoach): Observable<ICoach> {
    return this.http.put<ICoach>(this.url + '/coach/profile/' + coach.id, coach, this.httpOptions);
  }

  getAllCoaches(): Observable<ICoachList> {
    return this.http.get<ICoachList>(this.url + '/find-coach');
  }

  updateTopics(idToGet: number, topics: ITopic[]): Observable<ICoachList> {
    return this.http.put<ICoachList>(this.url + '/coach/profile/' + idToGet + '/topics', topics, this.httpOptions);
  }
}
