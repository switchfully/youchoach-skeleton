import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MessageService} from '../message.service';
import {ICoachee} from '../register/icoachee';
import {IMember} from '../IMember';

@Injectable({
  providedIn: 'root'
})
export class CoacheeService {
  readonly url = 'http://localhost:8080/users';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient, private messageService: MessageService) {
  }

  register(coachee: ICoachee): Observable<ICoachee> {
    return this.http.post<ICoachee>(this.url, coachee, this.httpOptions).pipe(
      // tap((newCoachee: ICoachee) => this.log(`registered coachee id=${newCoachee.id}`)),
      // catchError(this.handleError<any>('registerCoachee'))
    );
  }

  getCoachee(): Observable<IMember> {
    return this.http.get<IMember>(this.url + '/profile');
  }

  // private log(message: string) {
  //   this.messageService.add(`CoacheeService: ${message}`);
  // }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
