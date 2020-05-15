import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {MessageService} from './message.service';
import {Observable} from 'rxjs';
import {IValidationData} from './IValidationData';

@Injectable({
  providedIn: 'root'
})
export class EmailValidationService {

  readonly url = 'http://localhost:8080/users';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient, private messageService: MessageService) {
  }

  validate(data: IValidationData): Observable<IValidationData> {
    return this.http.patch<IValidationData>(this.url + '/validate', data, this.httpOptions).pipe(
    );
  }
}
