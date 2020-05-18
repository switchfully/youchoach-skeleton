import {Component, Input, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {FormBuilder, Validators} from '@angular/forms';
import {CoacheeService} from '../coacheeService/coachee.service';
import {Router} from '@angular/router';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  member: IMember;
  editForm = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    email: ['', [Validators.required]],
    photoUrl: [''],
  });
  isEmailChanged = false;
  oldEmail = '';
  emailExistsError = false;
  constructor(private fb: FormBuilder, private coacheeService: CoacheeService, private router: Router,
              private authenticationService: AuthenticationService) {
  }
  ngOnInit(): void {
    this.coacheeService.getCoachee().subscribe(coachee => this.oldEmail = coachee.email);
    this.getCoachee();
  }

  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }

  updateProfile(): void {
    this.coacheeService.updateProfile(this.member).subscribe(updatedMember => this.member = updatedMember,
      err => {
        if (err.error.message === ('Email already exists!')) {
          this.emailExistsError = true;
        }
      });
    setTimeout(() => {
      if (!this.emailExistsError) {
      if (this.isEmailChanged) {
        alert('Sign in with your new email');
        this.authenticationService.logout();
        this.onLogin();
      } else {
        this.onBack();
      }
    } }, 1500);
    // TEST IT IN HEROKU if timeout is enough
    this.emailExistsError = false;
    // this.isEmailChanged = false;
  }

  onBack(): void {
    this.router.navigateByUrl('/profile');
  }

  onLogin(): void {
    this.router.navigateByUrl('/login');
  }


  onSubmit() {
    if (this.editForm.get('email').value !== this.oldEmail) {
      this.isEmailChanged = true;
    }
    this.member = {
      firstName: this.editForm.get('firstName').value,
      lastName: this.editForm.get('lastName').value,
      email: this.editForm.get('email').value,
      photoUrl: this.editForm.get('photoUrl').value,
      schoolYear: this.member.schoolYear,
      youcoachRole: 'coachee',
    };
    this.updateProfile();
  }

}
