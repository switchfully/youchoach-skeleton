import { Component, OnInit } from '@angular/core';
import {InitMaterializeComponent} from '../init-materialize.component';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent extends InitMaterializeComponent implements OnInit {
  title = 'You-Coach | Intra-School Coaching';

  constructor() {
    super();
  }

  ngOnInit(): void {
    document.title = this.title;
  }
}
