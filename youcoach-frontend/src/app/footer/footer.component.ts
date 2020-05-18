import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication/authentication.service';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService, private route: ActivatedRoute) { }

  ngOnInit(): void {
  }

  calculateClass() {
    const url: string = this.route.snapshot['_routerState'].url;
    if (url.endsWith('/coach-profile')) {
      return 'page-footer teal lighten-3';
    }
    return 'page-footer yellow darken-2';
  }
}
