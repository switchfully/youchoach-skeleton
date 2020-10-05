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
  coacheeId: number;
  coacheeName: string;

  constructor(private route: ActivatedRoute,
              public authenticationService: AuthenticationService,
              private coacheeService: CoacheeService
  ) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(
        map(routeParams => routeParams.id),
        tap(coacheeId => this.coacheeId = coacheeId),
        flatMap(coacheeId => this.coacheeService.getCoacheeById(coacheeId))
      )
      .subscribe((coachee) => this.coacheeName = coachee.firstName + ' ' + coachee.lastName);
  }

  isActive(link: string) {
    const url: string = this.route.snapshot['_routerState'].url;
    return url.indexOf(link) !== -1;
  }
}
