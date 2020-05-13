import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {AuthenticationService} from '../authentication/authentication.service';
import {Router} from '@angular/router';

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

  constructor(private formBuilder: FormBuilder, private authenticationService: AuthenticationService, private router: Router) {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: ''
    });
  }

  ngOnInit(): void {
    document.title = this.title;
  }

  onSubmit(loginData) {
    this.success = false;
    this.error = false;
    this.authenticationService.login(loginData)
      .subscribe(
        (_ => {
          this.success = true;
          setTimeout(() => this.router.navigateByUrl('/profile'), 1000);
        }),
        (_ => this.error = true)
      );
    this.loginForm.reset();
  }

  logout() {
    this.authenticationService.logout();
  }

  alert() {
    alert('contact admin');
  }
}
