import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthenticationService} from "../../security/services/authentication/authentication.service";
import {CoacheeService} from "../../profile/services/coachee.service";
import {flatMap, map, tap} from "rxjs/operators";

@Component({
  selector: 'app-coachee-header',
  templateUrl: './coachee.component.html',
  styleUrls: ['./coachee.component.css']
})
export class CoacheeComponent implements OnInit {

  constructor() {}

  ngOnInit(): void {}

}
