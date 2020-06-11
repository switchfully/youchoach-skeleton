import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ISession} from '../interfaces/ISession';
import {Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {ISessionComplete} from '../interfaces/ISessionComplete';


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

  cancel(sessionId: number): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/cancel`, this.httpOptions);
  }

  decline(sessionId: number): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/decline`, this.httpOptions);
  }

  accept(sessionId: number): Observable<ISessionComplete> {
    return this.http.post<ISessionComplete>(this.url + `/${sessionId}/accept`, this.httpOptions);
  }

  getSession(sessionId: number): Observable<ISessionComplete> {
    return this.http.get<ISessionComplete>(this.url + `/${sessionId}`, this.httpOptions);
  }
}
