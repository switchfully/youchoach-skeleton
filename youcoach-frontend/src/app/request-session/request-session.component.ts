import { Component, OnInit} from '@angular/core';
import {CoachService} from '../coach-profile/coach.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ISession} from './ISession';
import { FormBuilder,ValidationErrors, Validators} from '@angular/forms';
import {SessionService} from './session.service';
import {InitMaterializeComponent} from '../init-materialize.component';
import * as M from 'materialize-css';

@Component({
  selector: 'app-request-session',
  templateUrl: './request-session.component.html',
  styleUrls: ['./request-session.component.css']
})
export class RequestSessionComponent extends InitMaterializeComponent implements OnInit {

  constructor(private coachService: CoachService, private route: ActivatedRoute,
              private fb: FormBuilder, private sessionService: SessionService, private router: Router) {
    super();
    this.idToGet = +this.route.snapshot.paramMap.get('id');
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

  private dateStringFormatMatches(input: string): boolean {
    return /[0-9]{2}\/[0-9]{2}\/[0-9]{4}/.test(input);
  }
  private timeStringFormatMatches(input: string): boolean {
    return /[0-9]{2}:[0-9]{2}/.test(input);
  }

  private parseDateStringToArray(input: string): number[] {
    return input.split('/').map(v => parseInt(v));
  }
  private parseDateStringForYear(input: string): number {
    return this.parseDateStringToArray(input)[2];
  }
  private parseDateStringForMonth(input: string): number {
    return this.parseDateStringToArray(input)[1];
  }
  private parseDateStringForDay(input: string): number {
    return this.parseDateStringToArray(input)[0];
  }
  private parseTimeStringToArray(input: string): number[]{
    return input.split(':').map(v => parseInt(v));
  }
  private parseTimeStringForHour(input: string): number {
    return this.parseTimeStringToArray(input)[0];
  }
  private parseTimeStringForMinutes(input: string): number {
    return this.parseTimeStringToArray(input)[1];
  }
  private constructSessionDate(date: string, time: string): Date | null {
    if(this.dateStringFormatMatches(date) && this.timeStringFormatMatches(time)){
      const year = this.parseDateStringForYear(date);
      const month = this.parseDateStringForMonth(date) - 1;
      const day = this.parseDateStringForDay(date);
      const hour = this.parseTimeStringForHour(time);
      const minutes = this.parseTimeStringForMinutes(time);
      return new Date(year, month, day, hour,minutes,0,0);
    } else {
      return null;
    }
  }
  private validateDate(): ValidationErrors | null {
    if(this.sessionForm === undefined) return null;
    const date = this.sessionForm.get('date').value;
    const time = this.sessionForm.get('time').value;

    if (date === '') {
      return {dateNotSet: true};
    }

    if ( time === '') {
      return {timeNotSet: true};
    }

    const dateTopass = this.constructSessionDate(date, time);
    return dateTopass === null || new Date() > dateTopass ? { mustBeFuture: true } : null;

  }

  private initializeDatePicker(): void{
    const elems = document.querySelectorAll('.datepicker');
    const instances = M.Datepicker.init(elems, {'format': 'dd/mm/yyyy',
      'onClose': _ => {
        this.sessionForm.get('date').value = this.datePickerElem.toString();
        this.sessionForm.updateValueAndValidity({ onlySelf: false, emitEvent: true });
      }
    });
    for(const instance of instances) {
      if(instance !== undefined){
        this.datePickerElem = instance;
      }
    }
  }
  private initializeTimePicker(): void{
    const elems = document.querySelectorAll('.timepicker');
    const instances = M.Timepicker.init(elems, {
      'twelveHour': false,
      'onCloseEnd': _ => {
        this.sessionForm.get('time').value = this.timePickerElem.time;
        this.sessionForm.updateValueAndValidity({ onlySelf: false, emitEvent: true });
      }
    });
    for(const instance of instances) {
      if(instance !== undefined){
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
      remarks: ['']
    }, {validators: [_ => this.validateDate()]});
    setTimeout(() => {
        this.initializeDatePicker();
        this.initializeTimePicker();
      }
      , 1000);

  }
  onSubmit() {
    this.session = {
      idCoach: this.idToGet,
      subject: this.sessionForm.get('subject').value,
      date: this.sessionForm.get('date').value,
      time: this.sessionForm.get('time').value,
      location: this.sessionForm.get('location').value,
      remarks: this.sessionForm.get('remarks').value
    };
    this.sendRequest();
    this.goProfile();
  }

  sendRequest(): void {
    this.sessionService.sendASession(this.session).subscribe();
  }

  goProfile(): void {
    this.router.navigateByUrl('/profile');
  }

}
