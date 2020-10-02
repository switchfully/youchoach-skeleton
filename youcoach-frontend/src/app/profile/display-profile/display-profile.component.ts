import {Component, OnInit} from '@angular/core';
import {IMember} from '../interfaces/IMember';
import {CoacheeService} from '../services/coachee.service';
import {ActivatedRoute} from '@angular/router';
import {flatMap, map, tap} from "rxjs/operators";

@Component({
  selector: 'app-display-profile',
  templateUrl: './display-profile.component.html',
  styleUrls: ['./display-profile.component.css']
})
export class DisplayProfileComponent implements OnInit {

  member: IMember = {
    id: null,
    email: null,
    firstName: null,
    lastName: null,
    classYear: null,
    youcoachRole: null
  };

  constructor(private coacheeService: CoacheeService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const url: string = this.route.snapshot['_routerState'].url;
    this.route.parent.params
      .pipe(
        map(routeParams => routeParams.id),
        flatMap(coacheeId => this.coacheeService.getCoacheeById(coacheeId))
      )
      .subscribe(member => this.member = member);
  }
}
