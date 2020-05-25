import {Component, OnInit} from '@angular/core';
import {CoachService} from '../coach-profile/coach.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-request-session',
  templateUrl: './request-session.component.html',
  styleUrls: ['./request-session.component.css']
})
export class RequestSessionComponent implements OnInit {

  idToGet: number;
  constructor(private coachService: CoachService, private route: ActivatedRoute) {
    this.idToGet = +this.route.snapshot.paramMap.get('id');
  }


  ngOnInit(): void {
  }

  sendRequest() {

  }
}
