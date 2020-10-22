import {Injectable} from '@angular/core';
import {environment} from "../../../environments/environment";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {IMember} from "../../profile/interfaces/IMember";

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  readonly url = `${environment.backendUrl}/users`;
  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': 'https://youcoach-test.herokuapp.com/'
    }),

  };

  constructor(private http: HttpClient) {
  }

  getAllProfiles(): Observable<IMember[]> {
    return this.http.get<IMember[]>(this.url, this.httpOptions);
  }

  getAllTopics(): Observable<String[]> {
    return this.http.get<String[]>(this.url + '/topics', this.httpOptions);
  }

  deleteProfile(id: number): Observable<Object> {
    return this.http.delete(this.url + '/profile/' + id, this.httpOptions);
  }
}
