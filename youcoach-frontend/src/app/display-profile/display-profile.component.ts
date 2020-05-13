import {Component, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {AuthenticationService} from '../authentication/authentication.service';
import {CoacheeService} from '../coacheeService/coachee.service';

@Component({
  selector: 'app-display-profile',
  templateUrl: './display-profile.component.html',
  styleUrls: ['./display-profile.component.css']
})
export class DisplayProfileComponent implements OnInit {

  member: IMember = null;

  constructor(private authenticationService: AuthenticationService, private coacheeService: CoacheeService) {
  }

  ngOnInit(): void {
    this.getCoachee();
    console.log(this.authenticationService.getToken());
    console.log(this.authenticationService.getUsername());
  }

  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }


}
