import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})

export class HomeComponent implements OnInit {
  title = 'You-Coach | Intra-School Coaching';


  constructor(public translateService: TranslateService) {

  }

  ngOnInit(): void {
    document.title = this.title;
  }

}
