import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {ICoachee} from "../register/icoachee";
import {IValidationData} from "../IValidationData";
import {EmailValidationService} from "../email-validation.service";

@Component({
  selector: 'app-validate-account',
  templateUrl: './validate-account.component.html',
  styleUrls: ['./validate-account.component.css']
})
export class ValidateAccountComponent implements OnInit {
  email: string;
  token: string;
  private sub: any;

  constructor(private route: ActivatedRoute, private validationService: EmailValidationService) {
    this.token = 'initial';
    this.email = 'your email';
  }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params => {

        console.log(params);
        if(undefined !== params.token) {
          this.token = params.token;
        }
        console.log(this.token);
      });


  }

  validateAccount(): void {
    this.performValidation({email: this.email, verificationCode: this.token});
    console.log('validate account function ' + this.token + ' ' + this.email);
  }


  private performValidation(data: IValidationData) {
    this.validationService.validate(data).subscribe(result => console.log(result),
      err => {
        if (err.error.message === ('Email already exists!')) {
        }
      });
  }

}
