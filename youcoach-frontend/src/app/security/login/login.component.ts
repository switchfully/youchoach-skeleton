import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {AuthenticationService} from '../services/authentication/authentication.service';
import {Router} from '@angular/router';
import {CoacheeService} from '../../profile/services/coachee.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: []
})
export class LoginComponent implements OnInit {
  error;
  success;
  loginForm;
  title = 'You-Coach | Sign in';
  jwt;

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService, private router: Router,
              private coacheeService: CoacheeService) {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit(): void {
    document.title = this.title;
  }

  onSubmit(loginData) {
    this.error = false;
    this.success = false;
    this.authenticationService.login(loginData)
      .subscribe(
        (_ => {
          this.success = true;
          this.router.navigateByUrl(this.getHomeUrl());
        }),
        (_ => this.error = true)
      );
    this.loginForm.reset();
  }

  private getHomeUrl() {
    if(this.authenticationService.isAdmin()) {
      return `/admin/overview`;
    }
    if (this.authenticationService.isCoach()) {
      return `/coach/${this.authenticationService.getUserId()}/coach-profile`;
    }
    return `/coachee/${this.authenticationService.getUserId()}/profile`;
  }

  resetPassword() {
    this.coacheeService.requestPasswordResetToken({ email: this.loginForm.get('username').value })
      .subscribe(_ => this.router.navigateByUrl('/password-reset-requested'));
  }

  logout() {
    this.authenticationService.logout();
  }

  alert() {
    alert('contact admin');
  }
}
