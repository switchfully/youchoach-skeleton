import {Injectable} from '@angular/core';
import {AuthenticationHttpService} from './authentication.http.service';
import {tap} from 'rxjs/operators';
import {Subject} from 'rxjs';
import * as JWT from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private tokenKey = 'jwt_token';
  private usernameKey = 'username';
  private userLoggedInSource = new Subject<boolean>();
  private tokenValue = null;
  private usernameValue = null;

  userLoggedIn$ = this.userLoggedInSource.asObservable();


  constructor(private loginService: AuthenticationHttpService) {
  }


  public setJwtToken(token: string, username: string): void {
    this.tokenValue = token;
    this.usernameValue = username;
    sessionStorage.setItem(this.tokenKey, this.tokenValue);
    sessionStorage.setItem(this.usernameKey, username);
  }

  login(loginData: any) {
    return this.loginService.login(loginData)
      .pipe(tap(response => {
        this.setJwtToken(response.headers.get('Authorization').replace('Bearer', '').trim(), loginData.username);
        this.userLoggedInSource.next(true);
      }));
  }

  getToken() {
    if (this.tokenValue === null) {
      this.tokenValue = sessionStorage.getItem(this.tokenKey);
    }
    return this.tokenValue;
  }

  getUsername() {
    if (this.usernameValue === null) {
      this.usernameValue = sessionStorage.getItem(this.usernameKey);
    }
    return this.usernameValue;
  }

  isLoggedIn() {
    return sessionStorage.getItem(this.tokenKey) !== null;
  }

  logout() {
    sessionStorage.removeItem(this.tokenKey);
    sessionStorage.removeItem(this.usernameKey);
    this.tokenValue = null;
    this.usernameValue = null;
    this.userLoggedInSource.next(false);
  }

  isCoach(): boolean {
    const tokenDecoded: any = JWT(this.getToken());
    return  tokenDecoded.rol.includes('ROLE_COACH');
  }
  isAdmin(): boolean {
    const tokenDecoded: any = JWT(this.getToken());
    return  tokenDecoded.rol.includes('ROLE_ADMIN');
  }
}
