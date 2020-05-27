import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ISession} from './ISession';
import {Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import {ISessionComplete} from '../coach-my-sessions/ISessionComplete';
import {Action, Status} from "./Action";



@Injectable({
  providedIn: 'root'
})
export class SessionService {
  readonly url = `${environment.backendUrl}/coaching-sessions`;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'http://localhost:4200'
    }),
  };

  constructor(private http: HttpClient) {
  }

  sendASession(session: ISession): Observable<ISession> {
    return this.http.post<ISession>(this.url, session, this.httpOptions);
  }

  getSessions(): Observable<ISessionComplete[]> {
    return this.http.get<ISessionComplete[]>(this.url, this.httpOptions);
  }

  getSessionsforCoach(): Observable<ISessionComplete[]> {
    return this.http.get<ISessionComplete[]>(this.url + '/coach', this.httpOptions);
  }

  sendStatus(action: Action): Observable<Action> {
    return this.http.patch<Action>(this.url, action, this.httpOptions);
  }
}
