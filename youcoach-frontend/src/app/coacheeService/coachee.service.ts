import { Injectable } from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {catchError, tap} from 'rxjs/operators';
import {MessageService} from '../message.service';
import {ICoachee} from '../register/ICoachee';

@Injectable({
  providedIn: 'root'
})
export class CoacheeService {
  url = 'http://localhost:8080/users';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  constructor(private http: HttpClient, private messageService: MessageService) { }

  register(coachee: ICoachee): Observable<ICoachee> {
    return this.http.post<ICoachee>(this.url, coachee, this.httpOptions).pipe(
      tap((newItem: ICoachee) => this.log(`added Item id=${newItem.id}`)),
      catchError(this.handleError<ICoachee>('addItem'))
    );
  }

  private log(message: string) {
    this.messageService.add(`CoacheeService: ${message}`);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      // console.error(error); // log to console instead
      this.log(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
