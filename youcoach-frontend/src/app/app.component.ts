import {AfterViewInit, Component} from '@angular/core';
import {AuthenticationService} from './security/services/authentication/authentication.service';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: []
})
export class AppComponent {

  constructor() {
  }

  ngOnInit(): void {

  }

}
