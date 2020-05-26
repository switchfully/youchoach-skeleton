import {Component, OnInit} from '@angular/core';
import {CoachService} from '../coach-profile/coach.service';
import {ActivatedRoute, Router} from '@angular/router';
import {ISession} from './ISession';
import {FormBuilder, Validators} from '@angular/forms';
import {SessionService} from './session.service';
import DateTimeFormat = Intl.DateTimeFormat;

@Component({
  selector: 'app-request-session',
  templateUrl: './request-session.component.html',
  styleUrls: ['./request-session.component.css']
})
export class RequestSessionComponent implements OnInit {
  session: ISession;
  sessionForm = this.fb.group({
    idCoach: ['', [Validators.required]],
    subject: ['', [Validators.required]],
    date: ['', [Validators.required]],
    time: ['', [Validators.required]],
    location: ['', [Validators.required]],
    remarks: [''],
  });
  idToGet: number;

  constructor(private coachService: CoachService, private route: ActivatedRoute,
              private fb: FormBuilder, private sessionService: SessionService, private router: Router) {
    this.idToGet = +this.route.snapshot.paramMap.get('id');
  }


  ngOnInit(): void {
    alert(this.getTime());
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
    alert('inside send request method');
    // this.sessionService.sendASession(this.session).subscribe();
  }

  goProfile(): void {
    this.router.navigateByUrl('/profile');
  }

  getTime(): string {
    let today = new Date();
    const dd = String(today.getDate()).padStart(2, '0');
    const mm = String(today.getMonth() + 1).padStart(2, '0');
    const yyyy = today.getFullYear();
    // @ts-ignore
    today = mm + '/' + dd + '/' + yyyy;
    return today.toString();
  }
}
