import {Component, Input, OnInit} from '@angular/core';
import {ISessionComplete, Status} from "../interfaces/ISessionComplete";
import {SessionService} from "../services/session.service";

@Component({
  selector: 'app-session-table',
  templateUrl: './session-table.component.html',
  styleUrls: ['./session-table.component.css']
})
export class SessionTableComponent implements OnInit {
  @Input()title: String;
  @Input()statusType: String;
  @Input()sessions: ISessionComplete[];
  @Input()coach: boolean = false;

  constructor(private sessionService: SessionService) { }

  ngOnInit(): void {
  }

  cancelSession(session: ISessionComplete): void {
    this.sessionService.cancel(session.id).subscribe(
      _ => session.status = Status.CANCELLED,
      _ => alert('Updating status failed!'));
  }

  declineSession(session: ISessionComplete): void {
    this.sessionService.decline(session.id).subscribe(
      _ => session.status = Status.CANCELLED,
      _ => alert('Updating status failed!'));
  }

  acceptSession(session: ISessionComplete): void {
    this.sessionService.accept(session.id).subscribe(
      _ => session.status = Status.ACCEPTED,
      _ => alert('Updating status failed!'));
  }

  finishSession(session: ISessionComplete): void {
    this.sessionService.finish(session.id).subscribe(
      _ => session.status = Status.WAITING_FOR_FEEDBACK,
      _ => alert('Updating status failed!'));
  }
}
