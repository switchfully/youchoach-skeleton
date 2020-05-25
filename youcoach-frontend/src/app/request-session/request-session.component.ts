import {Component, OnInit} from '@angular/core';
import {CoachService} from '../coach-profile/coach.service';
import {ActivatedRoute} from '@angular/router';
import {ISession} from './ISession';
import {FormBuilder} from '@angular/forms';

@Component({
  selector: 'app-request-session',
  templateUrl: './request-session.component.html',
  styleUrls: ['./request-session.component.css']
})
export class RequestSessionComponent implements OnInit {
  session: ISession;
  sessionForm = this.fb.group({
    idCoach : [''],
    subject : [''],
    date : [''],
    time : [''],
    location: [''],
    remarks : [''],
  });
  idToGet: number;
  constructor(private coachService: CoachService, private route: ActivatedRoute, private fb: FormBuilder) {
    this.idToGet = +this.route.snapshot.paramMap.get('id');
  }


  ngOnInit(): void {
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
    alert(this.session.idCoach);
  }
}
