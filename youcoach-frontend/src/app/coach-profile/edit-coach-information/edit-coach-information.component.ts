import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {ICoach} from '../ICoach';
import {CoachService} from '../coach.service';

@Component({
  selector: 'app-edit-coach-information',
  templateUrl: './edit-coach-information.component.html',
  styleUrls: ['./edit-coach-information.component.css']
})
export class EditCoachInformationComponent implements OnInit {
  coach: ICoach;
  editForm = this.fb.group({
    introduction: ['', [Validators.required]],
    availability: ['', [Validators.required]],
  });

  constructor(private fb: FormBuilder, private coachService: CoachService) {
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
    this.coach.availability = this.editForm.get('availability').value;
    this.coach.introduction = this.editForm.get('introduction').value;
    this.updateProfile();
  }

  updateProfile(): void {
    this.coachService.updateCoachInformation(this.coach);
  }

}
