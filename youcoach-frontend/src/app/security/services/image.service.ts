import {Injectable} from '@angular/core';
import {Observable, of} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {DomSanitizer} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ImageService {

  readonly url = `${environment.backendUrl}`;

  constructor(private http: HttpClient,
              private domSanitizer: DomSanitizer) {
  }

  downloadImageAsUrl(profileId: string): Observable<any> {
    return this.http.get(`${this.url}/users/profile/${profileId}/image`, {responseType: 'blob'}).pipe(
      map(image => this.domSanitizer.bypassSecurityTrustUrl(URL.createObjectURL(image))),
      catchError(_ => of('/assets/img/placeholder.png'))
    );
  }
}
