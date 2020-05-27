import { Component, OnInit } from '@angular/core';
import {ISessionComplete} from './ISessionComplete';
import {SessionService} from '../request-session/session.service';

@Component({
  selector: 'app-coach-mysessions',
  templateUrl: './coach-mysessions.component.html',
  styleUrls: ['./coach-mysessions.component.css']
})
export class CoachMysessionsComponent implements OnInit {
  sessions: ISessionComplete[];
  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
    this.getSessions();
  }

  getSessions(): void {
    this.sessionService.getSessions().subscribe(sessions => this.sessions = sessions);
  }

}
