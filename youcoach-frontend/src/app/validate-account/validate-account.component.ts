import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {IValidationData} from '../interfaces/IValidationData';
import {EmailValidationService} from '../services/email-validation.service';

@Component({
  selector: 'app-validate-account',
  templateUrl: './validate-account.component.html',
  styleUrls: ['./validate-account.component.css']
})
export class ValidateAccountComponent implements OnInit {
  email: string;
  token: string;
  emailMatch: RegExp;
  validationFailed = false;

  constructor(private route: ActivatedRoute, private validationService: EmailValidationService, private router: Router) {
    // tslint:disable-next-line:max-line-length
    this.emailMatch = new RegExp(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/);
    this.token = '';
    this.email = '';
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {

        console.log(params);
        if (undefined !== params.token) {
          this.token = params.token;
        }
        console.log(this.token);
      });


  }

  validateAccount(): void {
    this.performValidation({email: this.email, verificationCode: this.token});
    console.log('validate account function ' + this.token + ' ' + this.email);
  }

  resendValidationtoken(): void {
    this.validationService.resend({ email: this.email, validationBeingResend: false } ).subscribe(
      result => console.log('result', result),
      err => console.log('err', err)
    );
  }

  validEmailEntered(): boolean {
    console.log(this.emailMatch.test(this.email), this.email);
    return this.emailMatch.test(this.email);
  }


  private performValidation(data: IValidationData) {
    this.validationService.validate(data).subscribe(
      result => {
        if (result.emailAddressValidated) {
          this.router.navigateByUrl('/email-validation-success');
        } else {
          this.validationFailed = true;
        }
      },
      _ => this.validationFailed = true);
  }

}
