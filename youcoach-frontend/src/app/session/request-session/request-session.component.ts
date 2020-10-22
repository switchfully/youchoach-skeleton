import {Component, OnInit} from '@angular/core';
import {CoachService} from '../../profile/services/coach.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ISession} from '../interfaces/ISession';
import {FormBuilder, ValidationErrors, Validators} from '@angular/forms';
import {SessionService} from '../services/session.service';
import * as M from 'materialize-css';
import {DatepickerOptions} from 'materialize-css';
import {TimeComparatorService} from '../services/time-comparator.service';
import {AuthenticationService} from "../../security/services/authentication/authentication.service";

@Component({
  selector: 'app-request-session',
  templateUrl: './request-session.component.html',
  styleUrls: ['./request-session.component.css']
})
export class RequestSessionComponent implements OnInit {
  private profileId: number;
  session: ISession;
  sessionForm;
  datePickerElem;
  timePickerElem;
  idToGet: number;

  constructor(private coachService: CoachService,
              private route: ActivatedRoute,
              private fb: FormBuilder,
              private sessionService: SessionService,
              private router: Router,
              private timeComparator: TimeComparatorService,
              private authenticationService: AuthenticationService
  ) {
  }

  ngOnInit(): void {
    this.sessionForm = this.fb.group({
      coachId: [''],
      subject: ['', [Validators.required]],
      date: ['', [Validators.required]],
      time: ['', [Validators.required]],
      location: ['', [Validators.required]],
      remarks: ['', [Validators.required]]
    }, {validators: [_ => this.validateDate()]});
    setTimeout(() => {
        this.initializeDatePicker();
        this.initializeTimePicker();
      }
      , 1000);
    this.idToGet = +this.route.snapshot.paramMap.get('coachId');
    this.profileId = +this.route.parent.snapshot.paramMap.get('id');
  }


  enableSend(): boolean {
    return this.sessionForm !== null &&
      this.validateDate() === null &&
      this.sessionForm.get('subject').value.length > 0 &&
      this.sessionForm.get('location').value.length > 0 &&
       this.sessionForm.get('remarks').value.length > 0;
  }

  private validateDate(): ValidationErrors | null {
    if (this.sessionForm === undefined) {
      return null;
    }
    const date = this.sessionForm.get('date').value;
    const time = this.sessionForm.get('time').value;

    if (date === '') {
      return {dateNotSet: true};
    }

    if (time === '') {
      return {timeNotSet: true};
    }

    const dateTopass = this.timeComparator.constructSessionDate(date, time);
    return null;
  }

  private initializeDatePicker(): void {
    const elems = document.querySelectorAll('.datepicker');
    const instances = M.Datepicker.init(elems, {
      format: 'dd/mm/yyyy',
      onClose: _ => {
        this.sessionForm.get('date').value = this.datePickerElem.toString();
        this.sessionForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
      }
    } as DatepickerOptions);

    for (const instance of instances) {
      if (instance !== undefined) {
        this.datePickerElem = instance;
      }
    }
  }

  private initializeTimePicker(): void {
    const elems = document.querySelectorAll('.timepicker');
    const instances = M.Timepicker.init(elems, {
      'twelveHour': false,
      'onCloseEnd': _ => {
        this.sessionForm.get('time').value = this.timePickerElem.time;
        this.sessionForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
      }
    });
    for (const instance of instances) {
      if (instance !== undefined) {
        this.timePickerElem = instance;
      }
    }
  }

  onSubmit() {
    this.session = {
      profileId: this.profileId,
      coachId: this.idToGet,
      subject: this.sessionForm.get('subject').value,
      date: this.sessionForm.get('date').value,
      time: this.sessionForm.get('time').value,
      location: this.sessionForm.get('location').value,
      remarks: this.sessionForm.get('remarks').value
    };
    this.sendRequest();
  }

  sendRequest(): void {
    this.sessionService.sendASession(this.session).subscribe(
      _ => this.goSessions()
    );
  }

  goSessions(): void {
    this.router.navigateByUrl(`/coachee/${this.authenticationService.getUserId()}/sessions`);
  }

}
