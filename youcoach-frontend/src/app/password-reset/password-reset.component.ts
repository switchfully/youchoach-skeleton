import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CoacheeService} from '../services/coacheeService/coachee.service';

@Component({
  selector: 'app-password-reset',
  templateUrl: './password-reset.component.html',
  styleUrls: ['./password-reset.component.css']
})
export class PasswordResetComponent implements OnInit {
  emailMatch: RegExp;
  token: string;
  email: string;
  password: string;
  repeatPassword: string;

  constructor(private route: ActivatedRoute, private router: Router, private coacheeService: CoacheeService) {
    // tslint:disable-next-line:max-line-length
    this.emailMatch = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {

        // console.log(params);
        if (undefined !== params.token) {
          this.token = params.token;
        }
        // console.log(this.token);
      });
  }

  validEmailEntered(): boolean {
    // console.log(this.emailMatch.test(this.email), this.email);
    return this.emailMatch.test(this.email);
  }

  resetPassword(): void {
    this.coacheeService.performPasswordReset({email: this.email, token: this.token, password: this.password})
      .subscribe(result => {
                             if (result.passwordChanged) {
                               this.router.navigateByUrl('/password-change-success');
                             } else {
                               this.router.navigateByUrl('/password-change-failure');
                             }
      });
  }
}
