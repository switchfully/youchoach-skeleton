import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {AuthenticationService} from './security/services/authentication/authentication.service';
import {Router} from '@angular/router';
import {ForbiddenService} from './security/services/forbidden.service';

@Injectable()
export class RedirectOnErrorInterceptor implements HttpInterceptor {

  constructor(private authenticationService: AuthenticationService, private router: Router,
              private forbiddenService: ForbiddenService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request).pipe(
      tap(_ => {
      }),
      catchError((err: any) => {
        const router = this.router;

        function isAuthorisationError() {
          return err.status === 403;
        }

        function isAuthenticationError() {
          return err.status === 401;
        }

        function ignoreUserUnknownOnLogin() {
          return router === undefined || router === null ||
            (router.url === '/login' && err.error.name === 'USER_UNKNOWN');
        }

        function weReceivedAndHandleCustomAuthenticationError() {
          return isAuthenticationError() && 'name' in err.error &&
            !ignoreUserUnknownOnLogin();
        }

        if (err instanceof HttpErrorResponse) {
          if (isAuthenticationError() || isAuthorisationError()) {
            if (weReceivedAndHandleCustomAuthenticationError()) {
              this.handleAuthenticationErrors(err);
            } else if (isAuthorisationError()) {
              this.handleErrorResponse('/forbidden');
            }
          }
        }
        return throwError(err);
      })
    );
  }


  private handleAuthenticationErrors(err: HttpErrorResponse) {
    console.log('handleAuthenticationErrors');
    switch (err.error.name) {
      case 'USER_UNKNOWN':
        if (this.router !== undefined && this.router !== null && this.router.url !== '/login') {
          this.handleErrorResponseAndLogUserOut('/login');
        }
        break;
      case 'USER_DISABLED':
        this.handleErrorResponseAndLogUserOut('/validate-account');
        break;
      default:
        console.log('did not know: ' + err.error.name);
        this.handleErrorResponseAndLogUserOut('/login');
    }
  }

  private handleErrorResponse(url: string, forceLogOut: boolean = false) {
    if (this.authenticationService.isLoggedIn() && forceLogOut !== undefined && forceLogOut === true) {
      this.authenticationService.logout();
    }
    this.router.navigateByUrl(url).then(_ => {
    });
    this.forbiddenService.url = this.router.url;
  }

  private handleErrorResponseAndLogUserOut(url: string) {
    this.handleErrorResponse(url, true);
  }
}
