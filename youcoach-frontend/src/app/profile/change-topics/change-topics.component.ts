import {Component, OnInit} from '@angular/core';
import {RequestService} from "../services/request.service";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {ITopic} from "../interfaces/ICoach";

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
    // const changeTopics: ITopic[] = [{name: 'french', grades:[1,2,3]}, {name: 'dutch', grades:[2,3]}]
    const changeTopics: ITopic[] = [];
    this.requestService.changeTopics(this.profileId, changeTopics).subscribe(() => this.location.back());
  }

}
