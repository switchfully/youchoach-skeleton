import {Component, OnInit} from '@angular/core';
import {IMember} from "../../profile/interfaces/IMember";
import {ProfileService} from "../services/profile.service";
import {ThemeService} from "../../template/theme.service";
import {ICoach} from "../../profile/interfaces/ICoach";
import {AuthenticationService} from "../../security/services/authentication/authentication.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.css']
})
export class OverviewComponent implements OnInit {
  profiles: IMember[];
  _filteredProfiles: IMember[];
  searchText: string;

  constructor(private profileService: ProfileService,
              private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.profileService.getAllProfiles().subscribe(profiles => {
      this.profiles = profiles;
      this.performFiltering();
    });
  }

  getProfileUrl(profile: IMember) {
    if (profile.youcoachRole.name === 'COACH' || profile.youcoachRole.name === 'ADMIN') {
      return `/coach/${profile.id}/coach-profile`
    }
    return `/coachee/${profile.id}/profile`;
  }

  performFiltering() {
    this.filteredProfiles = this.profiles;
    if (this.searchText !== undefined) {
      this.filteredProfiles = this._filteredProfiles
        .filter(profile =>
          profile.firstName.toLowerCase().indexOf(this.searchText.toLowerCase()) !== -1||
          profile.lastName.toLowerCase().indexOf(this.searchText.toLowerCase()) !== -1 ||
          profile.classYear.toLowerCase().indexOf(this.searchText.toLowerCase()) !== -1
        );
    }
  }

  get filteredProfiles(): IMember[] {
    this.performFiltering();
    return this._filteredProfiles;
  }

  set filteredProfiles(profiles: IMember[]) {
    this._filteredProfiles = profiles;
  }

  navigateToProfileUrl(profile: IMember) {
    this.authenticationService.setMimicUserId('' + profile.id);
    this.router.navigateByUrl(this.getProfileUrl(profile));
  }
}
