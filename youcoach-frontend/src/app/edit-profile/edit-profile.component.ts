import {Component, Input, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {FormBuilder, Validators} from '@angular/forms';
import {ICoachee} from '../register/icoachee';
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
  private isEmailChange = false;

  constructor(private fb: FormBuilder, private coacheeService: CoacheeService, private router: Router,
              private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.getCoachee();
  }

  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }

  updateProfile(): void {
    this.coacheeService.updateProfile(this.member).subscribe();
    if (this.isEmailChange === true) {
      alert('Sign in with your new email');
      this.authenticationService.logout();
      this.onLogin();
    } else {
      this.onBack();
    }


  }

  onBack(): void {
    this.router.navigate(['/profile']);
  }

  onLogin(): void {
    this.router.navigate(['/login']);
  }


  onSubmit() {
    console.log('isEmailValid: ' + this.editForm.get('email').value);
    console.log('isEmailValid: ' + this.member.email);
    if (this.editForm.get('email').value !== null) {
      this.isEmailChange = true;
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
