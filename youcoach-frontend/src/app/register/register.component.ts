import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {CoacheeService} from '../coacheeService/coachee.service';
import {ICoachee} from './icoachee';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: []
})
export class RegisterComponent implements OnInit {
  registerForm = this.fb.group({
    firstname: ['', [Validators.required]],
    lastname: ['', [Validators.required]],
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

  constructor(private fb: FormBuilder, private router: Router,
              private coacheeService: CoacheeService) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.coachee = {
      id: null,
      firstName: this.registerForm.get('firstname').value,
      lastName: this.registerForm.get('lastname').value,
      email: this.registerForm.get('email').value,
      password: this.registerForm.get('password').value
    };
    this.register(this.coachee);
    if (this.emailErrorMessage == null) {
      this.router.navigateByUrl('/profile');
    }
  }

  private register(coachee: ICoachee) {
    this.coacheeService.register(coachee).subscribe(addedCoachee => this.coachee = addedCoachee,
      err => {
      if (err.error.message === ('Email already exists!')) {
         this.emailExistsError = true;
      }
      this.emailErrorMessage = err.error.message;
    });
  }
}
