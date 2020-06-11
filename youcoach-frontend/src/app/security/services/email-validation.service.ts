import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {IValidationData} from '../interfaces/IValidationData';
import {IResendValidation} from '../interfaces/IResendValidation';
import {IValidationResult} from '../interfaces/IValidationResult';
import {environment} from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class EmailValidationService {

  readonly url = `${environment.backendUrl}/users`;
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  validate(data: IValidationData): Observable<IValidationResult> {
    return this.http.post<IValidationResult>(this.url + '/validate', data, this.httpOptions);
  }

  resend(data: IResendValidation): Observable<IResendValidation> {
    return this.http.patch<IResendValidation>(this.url + '/validate', data, this.httpOptions);
  }
}
