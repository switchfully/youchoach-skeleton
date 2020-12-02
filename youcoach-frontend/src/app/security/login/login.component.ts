import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {ActivatedRoute, Router} from '@angular/router';
import {CoacheeService} from '../../profile/services/coachee.service';
import {InitService} from "../../template/materialize/init.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent implements OnInit {
  error;
  success;
  sending: boolean;
  wrongUsernameOrPassword: boolean;
  userUnknown: boolean;
  loginForm;
  title = 'You-Coach | Sign in';
  jwt;
  private redirectUrl: any;

  constructor(private formBuilder: FormBuilder,
              private authenticationService: AuthenticationService,
              private router: Router,
              private route: ActivatedRoute,
              private coacheeService: CoacheeService,
              private initService: InitService
  ) {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit(): void {
    document.title = this.title;
    this.route.queryParams.subscribe(queryParams => this.redirectUrl = queryParams.redirectUrl)
  }

  onSubmit(loginData) {
    this.sending = true;
    this.error = false;
    this.success = false;
    this.authenticationService.login(loginData)
      .subscribe(
        (_ => {
          this.success = true;
          this.initService.initDropdowns();
          this.router.navigateByUrl(this.redirectUrl ? this.redirectUrl : this.getHomeUrl());
        }),
        (fault => {
          console.log('test');
          this.sending = false;
          if (fault.status === 401) {
            this.wrongUsernameOrPassword = true;
          } else {
            this.error = true;
          }
        })
      );
    this.loginForm.reset();
  }

  private getHomeUrl() {
    if (this.authenticationService.isAdmin()) {
      return `/admin/overview`;
    }
    if (this.authenticationService.isCoach()) {
      return `/coach/${this.authenticationService.getUserId()}/coach-profile`;
    }
    return `/coachee/${this.authenticationService.getUserId()}/profile`;
  }

  resetPassword() {
    this.coacheeService.requestPasswordResetToken({email: this.loginForm.get('username').value})
      .subscribe(
        _ => this.router.navigateByUrl('/password-reset-requested'),
        _ => this.userUnknown = true
      );
  }

  logout() {
    this.authenticationService.logout();
  }

  alert() {
    alert('contact admin');
  }
}
