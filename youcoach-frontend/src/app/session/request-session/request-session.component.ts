import {Component, OnInit} from '@angular/core';
import {CoachService} from '../../profile/services/coach.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ISession} from '../interfaces/ISession';
import {FormBuilder, ValidationErrors, Validators} from '@angular/forms';
import {SessionService} from '../services/session.service';
import {InitMaterializeComponent} from '../../init-materialize.component';
import * as M from 'materialize-css';
import {TimeComparatorService} from '../services/time-comparator.service';

@Component({
  selector: 'app-request-session',
  templateUrl: './request-session.component.html',
  styleUrls: ['./request-session.component.css']
})
export class RequestSessionComponent extends InitMaterializeComponent implements OnInit {

  constructor(private coachService: CoachService, private route: ActivatedRoute,
              private fb: FormBuilder, private sessionService: SessionService, private router: Router, private timeComparator: TimeComparatorService) {
    super();
  }

  session: ISession;
  sessionForm;
  datePickerElem;
  timePickerElem;

  idToGet: number;

  enableSend(): boolean {
    return this.sessionForm !== null &&
      this.validateDate() === null &&
      this.sessionForm.get('subject').value.length > 0 &&
      this.sessionForm.get('location').value.length > 0;
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
    // return dateTopass === null || new Date() > dateTopass ? {mustBeFuture: true} : null;
    return null;
  }

  private initializeDatePicker(): void {
    const elems = document.querySelectorAll('.datepicker');
    const instances = M.Datepicker.init(elems, {
      'format': 'dd/mm/yyyy',
      'onClose': _ => {
        this.sessionForm.get('date').value = this.datePickerElem.toString();
        this.sessionForm.updateValueAndValidity({onlySelf: false, emitEvent: true});
      }
    });
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

  ngOnInit(): void {
    this.sessionForm = this.fb.group({
      idCoach: [''],
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
    this.idToGet = +this.route.snapshot.paramMap.get('id');
  }

  onSubmit() {
    this.session = {
      coachId: this.idToGet,
      subject: this.sessionForm.get('subject').value,
      date: this.sessionForm.get('date').value,
      time: this.sessionForm.get('time').value,
      location: this.sessionForm.get('location').value,
      remarks: this.sessionForm.get('remarks').value
    };
    this.sendRequest();
    this.goSessions();
  }

  sendRequest(): void {
    this.sessionService.sendASession(this.session).subscribe();
  }

  goProfile(): void {
    this.router.navigateByUrl('/profile');
  }

  goSessions(): void {
    this.router.navigateByUrl('/my-sessions');
  }

}
