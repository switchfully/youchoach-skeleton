import {Component, Input, OnInit} from '@angular/core';
import {ICoach} from "../coach-profile/ICoach";
import {CoachService} from "../coach-profile/coach.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-coach-profile-for-coachee',
  templateUrl: './coach-profile-for-coachee.component.html',
  styleUrls: ['./coach-profile-for-coachee.component.css']
})
export class CoachProfileForCoacheeComponent implements OnInit {

  constructor(private coachService: CoachService, private route: ActivatedRoute) { }

 @Input() coach: ICoach ={
   availability: '',
   coachXP: 0,
   email: '',
   firstName: "",
   introduction: "",
   lastName: "",
   photoUrl: "",
   schoolYear: "",
   topicYear: 0,
   topics: [],
   youcoachRole: ""
 };

  ngOnInit(): void {
    this.coachService.getCoachByEmail(this.route.snapshot.paramMap.get('id')).subscribe(coach => this.coach = coach);
  }

}
