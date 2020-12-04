import {AfterViewInit, Component, OnInit} from '@angular/core';
import {SessionService} from "../services/session.service";
import {ISessionComplete, Status} from "../interfaces/ISessionComplete";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";
import {flatMap, map, tap, timeout} from "rxjs/operators";
import {ViewportScroller} from "@angular/common";

@Component({
  selector: 'app-session-detail',
  templateUrl: './session-detail.component.html',
  styleUrls: ['./session-detail.component.css']
})
export class SessionDetailComponent implements OnInit, AfterViewInit {

  session: ISessionComplete;
  coachFeedbackForm: FormGroup;
  coacheeFeedbackForm: FormGroup;
  coach: boolean;
  private fragment: string;

  constructor(private formBuilder: FormBuilder, private sessionService: SessionService, private route: ActivatedRoute, private viewportScroller: ViewportScroller) {
  }

  ngOnInit(): void {
    this.coach = this.route.snapshot['_routerState'].url.indexOf('/coach/') !== -1;
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

    this.route.params
      .pipe(
        flatMap(routParams => this.sessionService.getSession(routParams.sessionId))
      )
      .subscribe(session => {
        this.session = session;
        if (session.coachFeedback) {
          this.coachFeedbackForm.patchValue(session.coachFeedback);
        }
        if (session.coacheeFeedback) {
          this.coacheeFeedbackForm.patchValue(session.coacheeFeedback);
        }
      });

    this.route.fragment.subscribe(fragment => this.fragment = fragment);
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

  ngAfterViewInit(): void {
    setTimeout(() => {
      if (this.fragment) {
        this.viewportScroller.scrollToAnchor(this.fragment);
      }
    }, 75);
  }
}
