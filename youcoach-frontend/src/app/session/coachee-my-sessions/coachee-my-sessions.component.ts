import {Component, OnInit} from '@angular/core';
import {SessionService} from '../services/session.service';
import {ISessionComplete, Status} from '../interfaces/ISessionComplete';
import {ActivatedRoute} from "@angular/router";
import {flatMap} from "rxjs/operators";

@Component({
  selector: 'app-coachee-my-sessions',
  templateUrl: './coachee-my-sessions.component.html',
  styleUrls: ['./coachee-my-sessions.component.css']
})
export class CoacheeMySessionsComponent implements OnInit {
  sessions: ISessionComplete[] = null;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.route.parent.params
      .pipe(
        flatMap(params => this.sessionService.getSessions(params.id))
      )
      .subscribe(sessions => this.sessions = sessions);
  }

  cancelSession(session: ISessionComplete): void {
    this.sessionService.cancel(session.id).subscribe(
      _ => session.status = Status.CANCELLED,
      _ => alert('Updating status failed!'));
  }
}
