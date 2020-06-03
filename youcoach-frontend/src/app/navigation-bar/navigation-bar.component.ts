import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../services/authentication/authentication.service';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-navigation-bar',
  templateUrl: './navigation-bar.component.html',
  styleUrls: ['./navigation-bar.component.css']
})
export class NavigationBarComponent implements OnInit {
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

  switchLanguage(language: string) {
    this.translate.use(language);
    this.language = language;
  }
  currentLanguage() {
    return this.language;
  }

}
