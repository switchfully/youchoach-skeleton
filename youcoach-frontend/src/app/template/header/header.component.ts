import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../security/services/authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';
import {ActivatedRoute} from '@angular/router';
import * as M from 'materialize-css';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class HeaderComponent implements OnInit {
  username;
  language = 'en';

  constructor(public authenticationService: AuthenticationService, private translate: TranslateService, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.translate.use(this.translate.getBrowserLang());
    this.username = this.authenticationService.getUsername();
    this.authenticationService.userLoggedIn$.subscribe(_ => {
      this.username = this.authenticationService.getUsername();
    });
  }

  switchLanguage(language: string) {
    this.translate.use(language);
    this.language = language;
    setTimeout(() => M.AutoInit(), 1);
  }

  currentLanguage() {
    return this.language;
  }

  logout() {
    this.authenticationService.logout();
  }

  isCoachMenu() {
    const url: string = this.route.snapshot['_routerState'].url;
    return url.indexOf('/coach/') !== -1;
  }
}
