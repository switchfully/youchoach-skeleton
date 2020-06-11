import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ICoachee} from '../../security/register/icoachee';
import {IMember} from '../interfaces/IMember';
import {catchError} from 'rxjs/operators';
import {ICoacheeRegisterResult} from '../../security/interfaces/ICoacheeRegisterResult';
import {IRequestPasswordResetToken} from '../../security/interfaces/IRequestPasswordResetToken';
import {IPasswordChange} from '../../security/interfaces/IPasswordChange';
import {IPasswordChangeResult} from '../../security/interfaces/IPasswordChangeResult';
import {environment} from '../../../environments/environment';
import {IMemberProfileUpdated} from '../interfaces/IMemberProfileUpdated';
import {AuthenticationService} from "../../security/services/authentication/authentication.service";

@Injectable({
  providedIn: 'root'
})
export class CoacheeService {
  readonly url = `${environment.backendUrl}/users`;
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json',
      // 'Access-Control-Allow-Origin' : 'http://localhost:4200'
      'Access-Control-Allow-Origin' : 'https://youcoach-test.herokuapp.com/'
    }),

  };

  constructor(private http: HttpClient, private authenticationService: AuthenticationService) {
  }

  register(coachee: ICoachee): Observable<ICoacheeRegisterResult> {
    return this.http.post<ICoacheeRegisterResult>(this.url, coachee, this.httpOptions);
  }
  requestPasswordResetToken(tokenRequest: IRequestPasswordResetToken): Observable<any> {
    return this.http.post<any>(this.url + '/password/reset', tokenRequest, this.httpOptions);
  }
  performPasswordReset(passwordChange: IPasswordChange): Observable<IPasswordChangeResult> {
    return this.http.patch<any>(this.url + '/password/reset', passwordChange, this.httpOptions);
  }

  getCoachee(): Observable<IMember> {
    return this.http.get<IMember>(this.url + '/profile');
  }

    getCoacheeById(id: number): Observable<IMember> {
    return this.http.get<IMember>(this.url + '/profile' + `/${id}`).pipe(
      catchError(this.handleError<IMember>(`getCoachee with id: ${id}`))
    );
  }

  updateProfile(member: IMember): Observable<IMemberProfileUpdated> {
    if (this.authenticationService.isAdmin()) {
      return this.http.put<IMemberProfileUpdated>(this.url + '/profile/' + member.id, member, this.httpOptions);
    } else {
      return this.http.put<IMemberProfileUpdated>(this.url + '/profile', member, this.httpOptions);
    }
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }
}
