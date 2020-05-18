import {Component, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {CoacheeService} from '../coacheeService/coachee.service';
import {ActivatedRoute, NavigationStart, Route, Router} from '@angular/router';
import {Observable} from 'rxjs';
import {map} from 'rxjs/operators';

@Component({
  selector: 'app-display-profile',
  templateUrl: './display-profile.component.html',
  styleUrls: ['./display-profile.component.css']
})
export class DisplayProfileComponent implements OnInit {

  member: IMember = {
    email: null,
    firstName: null,
    lastName: null,
    photoUrl: null,
    schoolYear: null,
    youcoachRole: null
  };

  constructor(private coacheeService: CoacheeService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const url: string = this.route.snapshot['_routerState'].url;
    if (url.endsWith('/profile')) {
      this.getCoachee();
    } else {
      this.getCoacheeByID(+this.route.snapshot.paramMap.get('id'));
    }
  }


  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }

  getCoacheeByID(id: number): void {
    this.coacheeService.getCoacheeById(id).subscribe(
      member => this.member = member);
  }
}
