import {Component, OnInit} from '@angular/core';
import {ISessionComplete} from './ISessionComplete';
import {SessionService} from '../request-session/session.service';
import {Action, Status} from '../request-session/Action';
import {TimeComparatorService} from '../time-comparator.service';

@Component({
  selector: 'app-coach-mysessions',
  templateUrl: './coach-mysessions.component.html',
  styleUrls: ['./coach-mysessions.component.css']
})
export class CoachMysessionsComponent implements OnInit {
  sessions: ISessionComplete[];
  sessionswithoutarchived: ISessionComplete[];
  sessionsarchived: ISessionComplete[];
  sessionsfeedback: ISessionComplete[];

  constructor(private sessionService: SessionService, private timeComparator: TimeComparatorService) {
  }

  ngOnInit(): void {
    this.getSessions();
    this.sessionsarchived = [];
    this.sessionswithoutarchived = [];
  }

  getSessions(): void {
    this.sessionService.getSessionsforCoach().subscribe(
      sessions => {
        this.sessions = sessions;
        for (const session of sessions) {
          if (session.status.includes('CANCELLED')) {
            if (session.status === 'CANCELLED_BY_COACH') session.cancelledByCoach = true;
            if (session.status.includes('COACHEE')) session.cancelledByCoachee = true;
            session.status = 'CANCELLED';
          }
          const dateTopass = this.timeComparator.constructSessionDate(session.date, session.time);
          if (new Date() > dateTopass) {
            this.sessionsarchived.push(session);
          } else {
            this.sessionswithoutarchived.push(session);
          }
        }
      });
  }

  acceptSession(sessionId: number): void {
    const status = Status.ACCEPTED;
    const action = new Action(sessionId, status);
    this.sessionService.sendStatus(action).subscribe(
      _ => this.updateSessionStatus(sessionId, status),
      _ => alert('Updating status failed!'));
  }

  declineSession(sessionId: number): void {
    const status = Status.DECLINED;
    const action = new Action(sessionId, status);
    this.sessionService.sendStatus(action).subscribe(
      _ => this.updateSessionStatus(sessionId, status),
      _ => alert('Updating status failed!'));
  }

  cancelSession(sessionId: number): void {
    const status = Status.CANCELLED_BY_COACH;
    const action = new Action(sessionId, status);
    this.sessionService.sendStatus(action).subscribe(
      _ => this.updateSessionStatus(sessionId, status),
      _ => alert('Updating status failed!'));
  }

  private updateSessionStatus(sessionId: number, status: Status) {
    this.sessions.forEach(session => {
      if (session.id === sessionId) {
        session.status = Status[status];
      }
      if (session.status.includes('CANCELLED')) session.status = 'CANCELLED';
    });
  }
}
