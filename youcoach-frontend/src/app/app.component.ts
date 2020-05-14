import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from './authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent implements OnInit {
  username;
  language = 'en';

  constructor(private authenticationService: AuthenticationService, private translate: TranslateService) {
  }

  ngOnInit(): void {
    this.username = this.authenticationService.getUsername();
    this.authenticationService.userLoggedIn$.subscribe(_ => {
      this.username = this.authenticationService.getUsername();
    });
  }
}
