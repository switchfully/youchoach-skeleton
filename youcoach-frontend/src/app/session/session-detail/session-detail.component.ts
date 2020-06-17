import {Component, OnInit} from '@angular/core';
import {SessionService} from "../services/session.service";
import {ISessionComplete, Status} from "../interfaces/ISessionComplete";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-session-detail',
  templateUrl: './session-detail.component.html',
  styleUrls: ['./session-detail.component.css']
})
export class SessionDetailComponent implements OnInit {

  session: ISessionComplete;
  coachFeedbackForm: FormGroup;
  coacheeFeedbackForm: FormGroup;
  coach: boolean;

  constructor(private formBuilder: FormBuilder, private sessionService: SessionService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.coach = this.route.snapshot['_routerState'].url.indexOf('/coach/') !== -1;
    let sessionId = +this.route.snapshot.paramMap.get('id');
    this.coachFeedbackForm = this.formBuilder.group({
      whatDidYouLike: "",
      onTime: "",
      wellPrepared: "",
      willingToLearn: "",
      howCanCoacheeGetBetter: ""
    });
    this.coacheeFeedbackForm = this.formBuilder.group({
      whatDidYouLike: "",
      howCanCoachGetBetter: "",
      onTime: "",
      usefulSession: "",
      clearExplanation: ""
    });

    this.sessionService.getSession(sessionId).subscribe(session => {
      this.session = session;
      if(session.coachFeedback) {
        this.coachFeedbackForm.patchValue(session.coachFeedback);
      }
      if(session.coacheeFeedback) {
        this.coacheeFeedbackForm.patchValue(session.coacheeFeedback);
      }
    });
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

  sendFeedbackAsCoachee(feedback) {
    this.sessionService.sendFeedback(this.session.id, feedback).subscribe(
      session => {
        this.session = session;
        this.coacheeFeedbackForm.patchValue(session.coacheeFeedback);
      },
      _ => alert('Updating status failed!'));
  }

  sendFeedbackAsCoach(feedback) {
    this.sessionService.sendFeedbackAsCoach(this.session.id, feedback).subscribe(
      session => {
        this.session = session;
        this.coachFeedbackForm.patchValue(session.coachFeedback);
      },
      _ => alert('Updating status failed!'));
  }
}
