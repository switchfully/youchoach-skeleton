import { Injectable } from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class ThemeService {

  constructor(private route: ActivatedRoute) { }

  getTheme() {
    if(this.isCoachTheme()){
      return 'teal lighten-3';
    }
    if(this.isCoacheeTheme()) {
      return 'yellow darken-2';
    }
    if(this.isAdminTheme()) {
      return 'brown lighten-2'
    }
    return 'yellow darken-2'
  }

  private isCoachTheme() {
    const url: string = this.route.snapshot['_routerState'].url;
    return url.indexOf('/coach/') !== -1;
  }

  private isCoacheeTheme() {
    const url: string = this.route.snapshot['_routerState'].url;
    return url.indexOf('/coachee/') !== -1;
  }

  private isAdminTheme() {
    const url: string = this.route.snapshot['_routerState'].url;
    return url.indexOf('/admin/') !== -1;
  }
}
