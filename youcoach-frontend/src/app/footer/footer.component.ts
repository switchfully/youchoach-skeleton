import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../authentication/authentication.service';
// import * as jwt_decode from 'jwt-decode';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: []
})
export class FooterComponent implements OnInit {

  constructor(private authenticationService: AuthenticationService) { }

  ngOnInit(): void {
  }
}
