import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-coachee-header',
  templateUrl: './coachee.component.html',
  styleUrls: ['./coachee.component.css']
})
export class CoacheeComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }

  ngOnInit(): void {

  }

  isActive(link: string) {
    const url: string = this.route.snapshot['_routerState'].url;
    return url.endsWith(link);
  }
}
