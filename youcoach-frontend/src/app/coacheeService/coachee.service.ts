import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ICoachee} from '../register/icoachee';
import {IMember} from '../IMember';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CoacheeService {
  readonly url = 'http://localhost:8080/users';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json',
      'Access-Control-Allow-Origin' : 'http://localhost:4200'
    }),

  };

  constructor(private http: HttpClient) {
  }

  register(coachee: ICoachee): Observable<ICoachee> {
    return this.http.post<ICoachee>(this.url, coachee, this.httpOptions);
  }

  getCoachee(): Observable<IMember> {
    return this.http.get<IMember>(this.url + '/profile');
  }

    getCoacheeById(id: number): Observable<IMember> {
    return this.http.get<IMember>(this.url + '/profile' + `/${id}`).pipe(
      catchError(this.handleError<IMember>(`getCoachee with id: ${id}`))
    );
  }

  updateProfile(member: IMember): Observable<IMember> {
    return this.http.put<IMember>(this.url + '/profile', member, this.httpOptions);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
