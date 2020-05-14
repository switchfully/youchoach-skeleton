import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication/authentication.service';
import {CoachService} from './coach.service';
import {ICoach} from './ICoach';

@Component({
  selector: 'app-coach-profile',
  templateUrl: './coach-profile.component.html',
  styleUrls: ['./coach-profile.component.css']
})
export class CoachProfileComponent implements OnInit {
  coach: ICoach = {
    availability: null,
    coachXP: null,
    coachingTopics: null,
    email: null,
    firstName: null,
    introduction: null,
    lastName: null,
    photoUrl: null,
    schoolYear: null,
    topicYear: null,
    youcoachRole: null
  };

  coachView = false;

  constructor(private authenticationService: AuthenticationService, private coachService: CoachService) { }

  ngOnInit(): void {
    this.getCoach();
    console.log(this.authenticationService.getToken());
    console.log(this.authenticationService.getUsername());
  }

  getCoach(): void {
    this.coachService.getCoach().subscribe(
      coach => this.coach = coach);
  }

  enableCoachView() {
    this.coachView = true;
  }
  disableCoachView() {
    this.coachView = false;
  }
}
