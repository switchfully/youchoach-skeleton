import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }


  ngOnInit(): void {
  }

  calculateClass() {
    const url: string = this.route.snapshot['_routerState'].url;
    if (url.endsWith('/coach-profile') || url.endsWith('/change-topics')) {
      return 'page-footer teal lighten-3';
    }
    return 'page-footer yellow darken-2';
  }
}
