import {Component, OnInit} from '@angular/core';
import {CoachService} from '../services/coach.service';
import {ICoach} from '../interfaces/ICoach';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-coach-profile',
  templateUrl: './coach-profile.component.html',
  styleUrls: ['./coach-profile.component.css']
})
export class CoachProfileComponent implements OnInit {
  coach: ICoach = {
    id: null,
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
  idToGet: number;

  coachView = false;

  constructor(private coachService: CoachService, private route: ActivatedRoute) {
    this.idToGet = +this.route.snapshot.paramMap.get('id');
  }

  ngOnInit(): void {

    document.title = this.title;
    if (this.idToGet !== 0) {
      this.getSpecificCoach();
    } else {
      this.getCoach();
    }
  }

  getSpecificCoach(): void {
    this.coachService.getSpecificCoach(this.idToGet).subscribe(coach => this.coach = coach);
  }
  getCoach(): void {
    this.coachService.getCoach().subscribe(
      coach => this.coach = coach);
  }
}
