import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  title = 'You-Coach | Intra-School Coaching';

  constructor() { }

  ngOnInit(): void {
    document.title = this.title;
  }

}
