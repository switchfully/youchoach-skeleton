import { Component, OnInit } from '@angular/core';
import {SessionService} from '../request-session/session.service';
import {ISessionComplete} from '../coach-my-sessions/ISessionComplete';
import {Action, Status} from '../request-session/Action';
import {TimeComparatorService} from '../time-comparator.service';

@Component({
  selector: 'app-coachee-my-sessions',
  templateUrl: './coachee-my-sessions.component.html',
  styleUrls: ['./coachee-my-sessions.component.css']
})
export class CoacheeMySessionsComponent implements OnInit {
  sessions: ISessionComplete[] = null;
  sessionswithoutarchived: ISessionComplete[];
  sessionsarchived: ISessionComplete[];
  sessionsfeedback: ISessionComplete[];
  constructor(private sessionService: SessionService, private timeComparator: TimeComparatorService) { }

  ngOnInit(): void {
    this.getSessions();
    this.sessionsarchived = [];
    this.sessionswithoutarchived = [];
  }

  getSessions(): void {
    this.sessionService.getSessions().subscribe(
      sessions => {
        this.sessions = sessions;
        this.sessions.forEach(session => {
          if (session.status.includes('CANCELLED')) {
            if (session.status === 'CANCELLED_BY_COACH') { session.cancelledByCoach = true; }
            if (session.status.includes('COACHEE')) { session.cancelledByCoachee = true; }
            session.status = 'CANCELLED';
          }
          const dateTopass = this.timeComparator.constructSessionDate(session.date, session.time);
          if (new Date() > dateTopass) {
            this.sessionsarchived.push(session);
          } else {
            this.sessionswithoutarchived.push(session);
          }
        });
      }
    );
  }

  cancelSession(sessionId: number): void {
    const status = Status.CANCELLED_BY_COACHEE;
    const action = new Action(sessionId, status);
    this.sessionService.sendStatus(action).subscribe(
      _ => this.updateSessionStatus(sessionId, status),
      _ => alert('Updating status failed!'));
  }

  private updateSessionStatus(sessionId: number, status: Status) {
    this.sessions.forEach(session => {
      if (session.id === sessionId) {
        session.status = Status[status];
        if (session.status.includes('CANCELLED')) {
          // console.log('cancel');
          session.status = 'CANCELLED';
        }
      }
    });
  }
}
