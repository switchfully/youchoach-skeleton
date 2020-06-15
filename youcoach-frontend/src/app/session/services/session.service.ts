import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ISession} from '../interfaces/ISession';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {ISessionComplete} from '../interfaces/ISessionComplete';
import {map} from "rxjs/operators";


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
    return this.http.get<ISessionComplete[]>(this.url, this.httpOptions).pipe(
      map(response => response.map(session => Object.assign(new ISessionComplete(), session)))
    );
  }

  getSessionsforCoach(): Observable<ISessionComplete[]> {
    return this.http.get<ISessionComplete[]>(this.url + '/coach', this.httpOptions).pipe(
      map(response => response.map(session => Object.assign(new ISessionComplete(), session)))
    );
  }

  cancel(sessionId: number): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/cancel`, this.httpOptions);
  }

  decline(sessionId: number): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/decline`, this.httpOptions);
  }

  accept(sessionId: number): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/accept`, this.httpOptions);
  }

  finish(sessionId: number): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/finish`, this.httpOptions);
  }

  sendFeedback(sessionId: number, feedback: string): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/feedback`, feedback, this.httpOptions).pipe(
      map(session => Object.assign(new ISessionComplete(), session))
    );
  }

  sendFeedbackAsCoach(sessionId: number, feedback: any): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/feedbackAsCoach`, feedback, this.httpOptions).pipe(
      map(session => Object.assign(new ISessionComplete(), session))
    );
  }

  getSession(sessionId: number): Observable<ISessionComplete> {
    return this.http.get<ISessionComplete>(this.url + `/${sessionId}`, this.httpOptions).pipe(
      map(session => Object.assign(new ISessionComplete(), session))
    );
  }

}
