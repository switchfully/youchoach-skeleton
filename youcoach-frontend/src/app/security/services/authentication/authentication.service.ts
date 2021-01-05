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
  private mimicUserIdKey = 'mimic_user_id';
  private userLoggedInSource = new Subject<boolean>();
  private mimicUserSource = new Subject<number>();

  userLoggedIn$ = this.userLoggedInSource.asObservable();
  mimicUser$ = this.mimicUserSource.asObservable();

  constructor(private loginService: AuthenticationHttpService) {
  }

  public setJwtToken(token: string): void {
    sessionStorage.setItem(this.tokenKey, token);
  }

  login(loginData: any) {
    return this.loginService.login(loginData)
      .pipe(tap(response => {
        this.setJwtToken(response.headers.get('Authorization').replace('Bearer', '').trim());
        this.userLoggedInSource.next(true);
      }));
  }

  getToken() {
    return sessionStorage.getItem(this.tokenKey);
  }

  getUserId() {
    if (!this.isLoggedIn()) {
      return null;
    }
    if (this.isMimicing()) {
      return this.getMimicUserId();
    }
    return JWT(this.getToken()).id;
  }

  getMimicUserId() {
    return sessionStorage.getItem(this.mimicUserIdKey);
  }

  private isMimicing() {
    return this.getMimicUserId() !== null;
  }

  mimicUser(mimicUserId: string) {
    sessionStorage.setItem(this.mimicUserIdKey, mimicUserId);
    this.mimicUserSource.next(parseInt(mimicUserId));
  }

  isLoggedIn() {
    return sessionStorage.getItem(this.tokenKey) !== null;
  }

  logout() {
    sessionStorage.removeItem(this.tokenKey);
    sessionStorage.removeItem(this.mimicUserIdKey);
    this.userLoggedInSource.next(false);
  }

  isCoach(): boolean {
    if (!this.isLoggedIn()) {
      return false;
    }
    const tokenDecoded: any = JWT(this.getToken());
    return tokenDecoded.rol.includes('COACH');
  }

  isAdmin(): boolean {
    if (!this.isLoggedIn()) {
      return false;
    }
    const tokenDecoded: any = JWT(this.getToken());
    return tokenDecoded.rol.includes('ADMIN');
  }
}
