import { Component, OnInit } from '@angular/core';
import {ICoachList} from "../coach-profile/ICoachList";
import {CoachService} from "../coach-profile/coach.service";

@Component({
  selector: 'app-find-a-coach',
  templateUrl: './find-a-coach.component.html',
  styleUrls: ['./find-a-coach.component.css']
})
export class FindACoachComponent implements OnInit {

  coachList: ICoachList = {coaches: null};

  constructor(private coachService: CoachService) { }

  ngOnInit(): void {
    this.coachService.getAllCoaches().subscribe(coaches => this.coachList = coaches);
  }

}
