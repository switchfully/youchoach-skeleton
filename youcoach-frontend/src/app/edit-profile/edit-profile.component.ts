import {Component, Input, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {FormBuilder, Validators} from '@angular/forms';
import {ICoachee} from '../register/icoachee';
import {CoacheeService} from '../coacheeService/coachee.service';
import {Router} from '@angular/router';

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

  constructor(private fb: FormBuilder, private coacheeService: CoacheeService, private router: Router) {
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
    this.onBack();
  }

  onBack(): void {
    this.router.navigate(['/profile']);
  }


  onSubmit() {
    console.log('test on submit');
    this.member = {
      firstName: this.editForm.get('firstName').value,
      lastName: this.editForm.get('lastName').value,
      email: this.editForm.get('email').value,
      photoUrl: this.editForm.get('photoUrl').value,
      schoolYear: this.member.schoolYear,
      youcoachRole: this.member.youcoachRole,
    };
    // this.member = this.editForm.value;
    this.updateProfile();
  }

}
