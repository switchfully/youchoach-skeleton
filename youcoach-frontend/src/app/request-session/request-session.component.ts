import {Component, Input, OnInit} from '@angular/core';
import {CoachService} from '../coach-profile/coach.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ISession} from './ISession';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SessionService} from './session.service';
import {InitMaterializeComponent} from '../init-materialize.component';


@Component({
  selector: 'app-request-session',
  templateUrl: './request-session.component.html',
  styleUrls: ['./request-session.component.css']
})
export class RequestSessionComponent implements OnInit {

  constructor(private coachService: CoachService, private route: ActivatedRoute,
              private fb: FormBuilder, private sessionService: SessionService, private router: Router) {

    this.idToGet = +this.route.snapshot.paramMap.get('id');

  }

  session: ISession;
  sessionForm = this.fb.group({
    idCoach: [''],
    subject: ['', [Validators.required]],
    date: ['', [Validators.required]],
    time: ['', [Validators.required]],
    location: ['', [Validators.required]],
    remarks: [''],
  }, { validator: group => this.validateDate(group)});


  idToGet: number;
  @Input() invalidDate = true;
  @Input() invalidTime = true;
  @Input() mustBeFuture = true;

  private validateDate(group: FormGroup): boolean {
    if (this.sessionForm === undefined) {
      return false;
    }

    const date = group.get('date').value;
    const time = group.get('time').value;

    console.log(date);
    console.log(time);

    if (date === '') {
      this.invalidDate = true;
      return false;
    }
    this.invalidDate = false;
    if ( time === '') {
      this.invalidTime = true;
      return false;
    }
    this.invalidTime = false;
    // console.log(date , time);
    let dateTopass: number = Date.parse(date + ' ' + time);
    if (Date.now() > dateTopass){
      this.mustBeFuture = true;
    }
    this.mustBeFuture = false;
    return Date.now() < dateTopass;

  }


  ngOnInit(): void {
    this.getTime();
  }


  onSubmit() {
    this.session = {
      idCoach: this.idToGet,
      subject: this.sessionForm.get('subject').value,
      // dateAndTime:  this.sessionForm.get('date').value + this.sessionForm.get('time').value,
      date: this.sessionForm.get('date').value,
      time: this.sessionForm.get('time').value,
      location: this.sessionForm.get('location').value,
      remarks: this.sessionForm.get('remarks').value
    };
    // alert(this.session.dateAndTime);
    this.sendRequest();
    this.goProfile();
  }

  sendRequest(): void {
    alert('inside send request method');
    // this.sessionService.sendASession(this.session).subscribe();
  }

  goProfile(): void {
    this.router.navigateByUrl('/profile');
  }

  getTime(): string {
    const today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0');
    const yyyy = today.getFullYear();
    return today.toDateString();
  }


}
