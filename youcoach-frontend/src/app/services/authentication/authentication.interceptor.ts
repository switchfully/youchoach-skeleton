import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {AuthenticationService} from './authentication.service';


@Injectable({
  providedIn: 'root'
})
export class AuthenticationInterceptor implements HttpInterceptor {
  constructor(private authenticationService: AuthenticationService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authenticationService.isLoggedIn()) {
      req = req.clone({
        setHeaders: {
          Authorization: `Bearer ${this.authenticationService.getToken()}`
        }
      });
    }
    return next.handle(req);
  }
}
