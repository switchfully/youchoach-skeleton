import {Component, OnInit} from '@angular/core';
import {SessionService} from '../services/session.service';
import {TimeComparatorService} from '../services/time-comparator.service';
import {ISessionComplete, Status} from '../interfaces/ISessionComplete';
import {flatMap} from "rxjs/operators";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-coach-mysessions',
  templateUrl: './coach-mysessions.component.html',
  styleUrls: ['./coach-mysessions.component.css']
})
export class CoachMysessionsComponent implements OnInit {
  sessions: ISessionComplete[];

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.parent.params
      .pipe(
        flatMap(params => this.sessionService.getSessionsforCoach(params.coachId))
      )
      .subscribe(sessions => this.sessions = sessions);
  }

  acceptSession(session: ISessionComplete): void {
    this.sessionService.accept(session.id).subscribe(
      _ => session.status = Status.ACCEPTED,
      _ => alert('Updating status failed!'));
  }

  declineSession(session: ISessionComplete): void {
    this.sessionService.decline(session.id).subscribe(
      _ => session.status = Status.DECLINED,
      _ => alert('Updating status failed!'));
  }

  cancelSession(session: ISessionComplete): void {
    this.sessionService.cancel(session.id).subscribe(
      _ => session.status = Status.CANCELLED,
      _ => alert('Updating status failed!'));
  }
}
