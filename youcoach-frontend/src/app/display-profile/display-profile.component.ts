import {Component, OnInit} from '@angular/core';
import {IMember} from '../IMember';
import {AuthenticationService} from '../authentication/authentication.service';

@Component({
  selector: 'app-display-profile',
  templateUrl: './display-profile.component.html',
  styleUrls: ['./display-profile.component.css']
})
export class DisplayProfileComponent implements OnInit {

  member: IMember;

  constructor(private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.member.firstName = 'Omar';
    console.log(this.authenticationService.getToken());
    console.log(this.authenticationService.getUsername());
  }

}
