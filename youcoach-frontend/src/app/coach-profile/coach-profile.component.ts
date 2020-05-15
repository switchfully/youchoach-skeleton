import {Component, OnInit} from '@angular/core';
import {CoachService} from './coach.service';
import {ICoach} from './ICoach';
import {ProfileViewService} from '../profile-view.service';

@Component({
  selector: 'app-coach-profile',
  templateUrl: './coach-profile.component.html',
  styleUrls: ['./coach-profile.component.css']
})
export class CoachProfileComponent implements OnInit {
  coach: ICoach = {
    availability: null,
    coachXP: null,
    topics: null,
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

  constructor(private coachService: CoachService, private profileView: ProfileViewService) { }

  ngOnInit(): void {
    document.getElementById('footer').setAttribute('class', 'page-footer teal lighten-3');
    document.title = this.title;
    this.getCoach();
    this.profileView.change.subscribe(value => this.coachView = value);
  }

  getCoach(): void {
    this.coachService.getCoach().subscribe(
      coach => this.coach = coach);
  }
}
