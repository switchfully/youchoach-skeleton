import {AfterViewInit, Component} from '@angular/core';
import {AuthenticationService} from './security/services/authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {
  username;

  constructor(private authenticationService: AuthenticationService, private translate: TranslateService) {
    translate.setDefaultLang('en');
  }

  ngOnInit(): void {
    this.username = this.authenticationService.getUsername();
    this.authenticationService.userLoggedIn$.subscribe(_ => {
      this.username = this.authenticationService.getUsername();
    });
  }

}