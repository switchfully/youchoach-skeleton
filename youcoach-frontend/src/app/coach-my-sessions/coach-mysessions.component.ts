import {Component, OnInit} from '@angular/core';
import {SessionService} from '../services/session.service';
import {Action, Status} from '../interfaces/Action';
import {TimeComparatorService} from '../services/time-comparator.service';
import {ISessionComplete} from '../interfaces/ISessionComplete';

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
            if (session.status === 'CANCELLED_BY_COACH') { session.cancelledByCoach = true; }
            if (session.status.includes('COACHEE')) { session.cancelledByCoachee = true; }
            session.status = 'CANCELLED';
          }
          const dateTopass = this.timeComparator.constructSessionDate(session.date, session.time);
          console.log(dateTopass);
          console.log(new Date());
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
      if (session.status.includes('CANCELLED')) { session.status = 'CANCELLED'; }
    });
  }
}
