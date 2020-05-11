import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {AuthenticationService} from '../authentication/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['../../css/materialize.css', '../../css/style.css']
})
export class LoginComponent implements OnInit {
  error;
  success;
  loginForm;

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit(): void {
  }

  onSubmit(loginData) {
    this.success = false;
    this.error = false;
    this.authenticationService.login(loginData)
      .subscribe(
        (_ => this.success = true),
        (_ => this.error = true)
      );
    this.loginForm.reset();
  }

  logout() {
    this.authenticationService.logout();
  }
}
