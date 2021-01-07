import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {ICoachee} from '../../security/interfaces/ICoachee';
import {IMember} from '../interfaces/IMember';
import {catchError} from 'rxjs/operators';
import {ICoacheeRegisterResult} from '../../security/interfaces/ICoacheeRegisterResult';
import {IRequestPasswordResetToken} from '../../security/interfaces/IRequestPasswordResetToken';
import {IPasswordChange} from '../../security/interfaces/IPasswordChange';
import {IPasswordChangeResult} from '../../security/interfaces/IPasswordChangeResult';
import {environment} from '../../../environments/environment';
import {IMemberProfileUpdated} from '../interfaces/IMemberProfileUpdated';

@Injectable({
  providedIn: 'root'
})
export class CoacheeService {
  readonly url = `${environment.backendUrl}/users`;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'https://youcoach-test.herokuapp.com/'
    }),
  };

  constructor(private http: HttpClient) {
  }

  getCoacheeById(id: number): Observable<IMember> {
    return this.http.get<IMember>(this.url + '/profile' + `/${id}`).pipe(
      catchError(this.handleError<IMember>(`getCoachee with id: ${id}`))
    );
  }

  updateProfile(member: IMember): Observable<any> {
    return this.http.put<any>(this.url + '/profile/' + member.id, member);
  }

  uploadImage(profileId: number, file: File) {
    const formData = new FormData();
    formData.append('profilePicture', file, file.name);
    return this.http.post(this.url + '/profile/' + profileId + '/image', formData);
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      return of(result as T);
    };
  }

}
