import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ICoach} from './ICoach';
import {environment} from '../../environments/environment';

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

  updateCoachInformation(coach: ICoach): Observable<ICoach> {
    return this.http.put<ICoach>(this.url, coach, this.httpOptions);
  }
}
