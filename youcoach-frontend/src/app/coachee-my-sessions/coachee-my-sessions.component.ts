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
  sessionswithoutarchived: ISessionComplete[] = null;
  sessionsarchived: ISessionComplete[] = null;
  sessionsfeedback: ISessionComplete[] = null;
  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
    this.getSessions();
  }

  getSessions(): void {
    this.sessionService.getSessions().subscribe(
      sessions => {
        this.sessions = sessions;
        this.sessionswithoutarchived = sessions.filter(session => new Date(session.date.replace('/', '-').replace('/', '-').replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3")).getTime() > Date.now());
        this.sessionsarchived = this.sessions.filter(session => new Date(session.date.replace('/', '-').replace('/', '-').replace( /(\d{2})-(\d{2})-(\d{4})/, "$2/$1/$3")).getTime() < Date.now());
      }
    );


  }
}
