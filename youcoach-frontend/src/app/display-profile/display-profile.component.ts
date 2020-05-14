import {Component, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {CoacheeService} from '../coacheeService/coachee.service';

@Component({
  selector: 'app-display-profile',
  templateUrl: './display-profile.component.html',
  styleUrls: ['./display-profile.component.css']
})
export class DisplayProfileComponent implements OnInit {

  member: IMember;

  constructor(private coacheeService: CoacheeService) {
  }

  ngOnInit(): void {
    document.getElementById('footer').setAttribute('class', 'page-footer yellow darken-2');
    this.getCoachee();
  }

  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }


}
