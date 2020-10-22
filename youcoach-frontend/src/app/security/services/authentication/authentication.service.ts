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
  private userIdKey = 'user_id';
  private mimicUserId = 'mimic_user_id';
  private userLoggedInSource = new Subject<boolean>();
  private mimicUserSource = new Subject<number>();
  private tokenValue = null;
  private usernameValue = null;
  private userId = null;

  userLoggedIn$ = this.userLoggedInSource.asObservable();
  mimicUser$ = this.mimicUserSource.asObservable();

  constructor(private loginService: AuthenticationHttpService) {
  }

  public setJwtToken(token: string, username: string): void {
    this.tokenValue = token;
    this.usernameValue = username;
    this.userId = JWT(this.getToken()).id;
    sessionStorage.setItem(this.tokenKey, this.tokenValue);
    sessionStorage.setItem(this.usernameKey, this.usernameValue);
    sessionStorage.setItem(this.userIdKey, this.userId);
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

  getUserId() {
    if (this.userId === null) {
      this.userId = sessionStorage.getItem(this.userIdKey);
    }
    if (this.getMimicUserId() !== null) {
      return this.getMimicUserId();
    }
    return this.userId;
  }

  getMimicUserId() {
    return sessionStorage.getItem(this.mimicUserId);
  }

  setMimicUserId(mimicUserId: string) {
    sessionStorage.setItem(this.mimicUserId, mimicUserId);
    this.mimicUserSource.next(parseInt(mimicUserId));
  }

  isLoggedIn() {
    return sessionStorage.getItem(this.tokenKey) !== null;
  }

  logout() {
    sessionStorage.removeItem(this.tokenKey);
    sessionStorage.removeItem(this.usernameKey);
    sessionStorage.removeItem(this.userIdKey);
    sessionStorage.removeItem(this.mimicUserId);
    this.tokenValue = null;
    this.usernameValue = null;
    this.userId = null;
    this.mimicUserId = null;
    this.userLoggedInSource.next(false);
  }

  isCoach(): boolean {
    if(this.getToken() == null) {
      return false;
    }
    const tokenDecoded: any = JWT(this.getToken());
    return tokenDecoded.rol.includes('ROLE_COACH');
  }

  isAdmin(): boolean {
    if(this.getToken() == null) {
      return false;
    }
    const tokenDecoded: any = JWT(this.getToken());
    return tokenDecoded.rol.includes('ROLE_ADMIN');
  }
}
