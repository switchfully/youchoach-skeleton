import {Component, OnInit} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {CoachService} from '../services/coach.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {ICoach} from '../interfaces/ICoach';
import {flatMap} from "rxjs/operators";

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

  constructor(private fb: FormBuilder, private coachService: CoachService, private router: Router, private route: ActivatedRoute, private location: Location) {
  }

  ngOnInit(): void {
    this.getCoach();
  }

  getCoach(): void {
    this.route.parent.params
      .pipe(
        flatMap(params => this.coachService.getSpecificCoach(params.coachId))
      )
      .subscribe(
        coach => {
          this.coach = coach;
          this.editForm.patchValue(coach);
        }
      );
  }

  onSubmit() {
    this.coach = {
      id: this.coach.id,
      introduction: this.editForm.get('introduction').value,
      availability: this.editForm.get('availability').value,
      coachXP: this.coach.coachXP,
      topics: this.coach.topics,
      topicYear: this.coach.topicYear,
      firstName: this.coach.firstName,
      lastName: this.coach.lastName,
      email: this.coach.email,
      classYear: this.coach.classYear,
      youcoachRole: this.coach.youcoachRole
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
