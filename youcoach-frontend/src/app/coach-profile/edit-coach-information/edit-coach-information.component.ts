import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {ICoach, ITopic} from '../ICoach';
import {CoachService} from '../coach.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-edit-coach-information',
  templateUrl: './edit-coach-information.component.html',
  styleUrls: ['./edit-coach-information.component.css']
})
export class EditCoachInformationComponent implements OnInit {
  coach: ICoach;
  editForm = this.fb.group({
    introduction: [''],
    availability: [''],
  });

  constructor(private fb: FormBuilder, private coachService: CoachService, private router: Router, private location: Location) {
  }

  ngOnInit(): void {
    this.getCoach();
  }

  getCoach(): void {
    this.coachService.getCoach().subscribe(
      coach => this.coach = coach
    );
  }

  onSubmit() {
    this.coach = {
      introduction: this.editForm.get('introduction').value,
      availability: this.editForm.get('availability').value,
      coachXP: this.coach.coachXP,
      topics: this.coach.topics,
      topicYear: this.coach.topicYear,
      firstName: this.coach.firstName,
      lastName: this.coach.lastName,
      email: this.coach.email,
      schoolYear: this.coach.schoolYear,
      youcoachRole: this.coach.schoolYear,
      photoUrl: this.coach.photoUrl
    };
    this.updateProfile();
  }

  updateProfile(): void {
    this.coachService.updateCoachInformation(this.coach).subscribe(
      updatedCoach => {
        this.coach = updatedCoach;
        this.goBack();
      }
    );
  }

  goBack(): void {
    this.location.back();
  }

}
