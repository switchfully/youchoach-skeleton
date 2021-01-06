import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {ICoachee} from '../interfaces/ICoachee';
import {ICoacheeRegisterResult} from '../interfaces/ICoacheeRegisterResult';
import {AccountService} from "../services/account.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: []
})
export class RegisterComponent implements OnInit {
  registerForm = this.fb.group({
    firstname: ['', [Validators.required]],
    lastname: ['', [Validators.required]],
    classYear: ['', [Validators.required]],
    email: ['', [Validators.required]],
    password: ['', [Validators.required]],
    passwordVerification: ['', ]
  }, {validator: RegisterComponent.checkPasswords});
  coachee: ICoachee;
  emailExistsError = false;
  emailErrorMessage: string;

  private static checkPasswords(group: FormGroup) {
    const pass = group.get('password').value;
    const confirmPass = group.get('passwordVerification').value;
    return pass === confirmPass ? null : {notSame: true};
  }

  constructor(private fb: FormBuilder,
              private router: Router,
              private accountService: AccountService
  ) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.coachee = {
      id: null,
      firstName: this.registerForm.get('firstname').value,
      lastName: this.registerForm.get('lastname').value,
      classYear: this.registerForm.get('classYear').value,
      email: this.registerForm.get('email').value,
      password: this.registerForm.get('password').value
    };
    this.register(this.coachee);
  }

  private register(coachee: ICoachee) {
    this.accountService.register(coachee).subscribe(
      addedCoachee => {
        if (addedCoachee.accountEnabled) {
          this.router.navigateByUrl('/registration-success');
        } else {
          this.router.navigateByUrl('/validate-account');
        }
      },
      err => {
        if (err.error.message === ('Email already exists!')) {
          this.emailExistsError = true;
          this.emailErrorMessage = err.error.message;
        }
        if (!this.emailExistsError) {
          this.router.navigateByUrl('/coachee/profile');
        }
      });
  }
}
