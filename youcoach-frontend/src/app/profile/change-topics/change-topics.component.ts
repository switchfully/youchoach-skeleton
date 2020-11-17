import {Component, OnInit} from '@angular/core';
import {RequestService} from "../services/request.service";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-change-topics',
  templateUrl: './change-topics.component.html',
  styleUrls: ['./change-topics.component.css']
})
export class ChangeTopicsComponent implements OnInit {
  private profileId: number;

  constructor(private requestService: RequestService,
              private route: ActivatedRoute,
              private location: Location
  ) { }

  ngOnInit(): void {
    this.route.parent.params.subscribe(routeParams => this.profileId = routeParams.coachId);
  }

  mail() {
    this.requestService.requestProfileChange(this.profileId).subscribe(() => this.location.back());
  }

}
