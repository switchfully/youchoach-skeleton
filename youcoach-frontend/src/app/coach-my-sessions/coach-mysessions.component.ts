import { Component, OnInit } from '@angular/core';
import {ISessionComplete} from './ISessionComplete';
import {SessionService} from '../request-session/session.service';
import {Action, Status} from '../request-session/Action';

@Component({
  selector: 'app-coach-mysessions',
  templateUrl: './coach-mysessions.component.html',
  styleUrls: ['./coach-mysessions.component.css']
})
export class CoachMysessionsComponent implements OnInit {
  sessions: ISessionComplete[];
  sessionswithoutarchived: ISessionComplete[] = null;
  sessionsarchived: ISessionComplete[] = null;
  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
    this.getSessions();
  }

  getSessions(): void {
    this.sessionService.getSessionsforCoach().subscribe(
      sessions => {
        this.sessions = sessions;
        this.sessionswithoutarchived = sessions.filter(session => new Date(session.date.replace('/', '-').replace('/', '-').replace( /(\d{2})-(\d{2})-(\d{4})/, '$2/$1/$3')).getTime() > Date.now());
        this.sessionsarchived = this.sessions.filter(session => new Date(session.date.replace('/', '-').replace('/', '-').replace( /(\d{2})-(\d{2})-(\d{4})/, '$2/$1/$3')).getTime() < Date.now());
  });
  }

  acceptSession(sessionId: number): void {
    const action = new Action(sessionId, Status.Accepted);
    this.sessionService.sendStatus(action).subscribe(_ => null, _ => alert('Updating status failed!'));
  }

  declineSession(sessionId: number): void {
    const action = new Action(sessionId, Status.Declined);
    this.sessionService.sendStatus(action).subscribe(_ => null, _ => alert('Updating status failed!'));
  }
}
