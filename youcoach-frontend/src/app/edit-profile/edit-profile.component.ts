import {Component, Input, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {FormBuilder, Validators} from '@angular/forms';
import {ICoachee} from '../register/icoachee';
import {CoacheeService} from '../coacheeService/coachee.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  @Input() previousMember: IMember;
  member: IMember;
  editForm = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    email: ['', [Validators.required]],
    photoUrl: ['', [Validators.required]],
  });

  constructor(private fb: FormBuilder, private coacheeService: CoacheeService) {
  }

  ngOnInit(): void {
    this.getCoachee();
  }

  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }

  onSubmit() {
    this.member = {
      firstName: this.editForm.get('firstname').value,
      lastName: this.editForm.get('lastname').value,
      email: this.editForm.get('email').value,
      photoUrl: this.editForm.get('url').value,
      schoolYear: this.previousMember.schoolYear,
      youcoachRole: this.previousMember.youcoachRole
    };
    // this.register(this.coachee);
  }

  test(): void {
    alert('A href met event');
  }
}
