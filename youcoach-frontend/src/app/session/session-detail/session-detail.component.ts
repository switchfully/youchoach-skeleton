import { Component, OnInit } from '@angular/core';
import {SessionService} from "../services/session.service";
import {ISessionComplete} from "../interfaces/ISessionComplete";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-session-detail',
  templateUrl: './session-detail.component.html',
  styleUrls: ['./session-detail.component.css']
})
export class SessionDetailComponent implements OnInit {

  session: ISessionComplete;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    let sessionId = +this.route.snapshot.paramMap.get('id');
    this.sessionService.getSession(sessionId).subscribe(session => this.session = session);
  }
}
