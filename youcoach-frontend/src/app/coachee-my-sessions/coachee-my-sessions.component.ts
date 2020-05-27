import { Component, OnInit } from '@angular/core';
import {SessionService} from '../request-session/session.service';
import {ISessionComplete} from '../coach-my-sessions/ISessionComplete';

@Component({
  selector: 'app-coachee-my-sessions',
  templateUrl: './coachee-my-sessions.component.html',
  styleUrls: ['./coachee-my-sessions.component.css']
})
export class CoacheeMySessionsComponent implements OnInit {
  sessions: ISessionComplete[] = null;

  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
    this.getSessions();
  }

  getSessions(): void {
    this.sessionService.getSessions().subscribe(sessions => this.sessions = sessions);
  }
}
