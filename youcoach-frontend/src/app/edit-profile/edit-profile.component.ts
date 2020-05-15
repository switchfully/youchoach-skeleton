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
  private isEmailChanged = false;
  oldEmail = '';
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
    this.coacheeService.updateProfile(this.member).subscribe();
    if (this.isEmailChanged === true) {
      alert('Sign in with your new email');
      this.authenticationService.logout();
      this.onLogin();
    } else {
      this.onBack();
    }
    this.isEmailChanged = false;
  }

  onBack(): void {
    this.router.navigate(['/profile']);
  }

  onLogin(): void {
    this.router.navigate(['/login']);
  }


  onSubmit() {
    console.log('isEmailValid: ' + this.member.email);
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
