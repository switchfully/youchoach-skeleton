import {Component, OnInit} from '@angular/core';
import {IMember} from '../interfaces/IMember';
import {CoacheeService} from '../services/coachee.service';
import {ActivatedRoute} from '@angular/router';

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
    this.getCoacheeByID(+this.route.snapshot.paramMap.get('id'));
  }

  getCoacheeByID(id: number): void {
    this.coacheeService.getCoacheeById(id).subscribe(member => this.member = member);
  }
}
