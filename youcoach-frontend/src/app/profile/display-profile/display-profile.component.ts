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
    photoUrl: null,
    classYear: null,
    youcoachRole: null
  };

  adminEdit = false;


  constructor(private coacheeService: CoacheeService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    const url: string = this.route.snapshot['_routerState'].url;
    if (url.endsWith('/profile')) {
      this.getCoachee();
    } else {
      this.adminEdit = true;
      this.getCoacheeByID(+this.route.snapshot.paramMap.get('id'));
    }
  }


  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }

  getCoacheeByID(id: number): void {
    this.coacheeService.getCoacheeById(id).subscribe(
      member => this.member = member);
  }
}
