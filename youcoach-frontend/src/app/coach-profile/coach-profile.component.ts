import {Component, OnInit} from '@angular/core';
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
  title = 'You-Coach | Coach-Profile';

  coachView = false;

  constructor(private coachService: CoachService) { }

  ngOnInit(): void {
    document.getElementById('coachee').setAttribute('class', 'hidden');
    document.getElementById('footer').setAttribute('class', 'page-footer teal lighten-3');
    document.title = this.title;
    this.getCoach();
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
