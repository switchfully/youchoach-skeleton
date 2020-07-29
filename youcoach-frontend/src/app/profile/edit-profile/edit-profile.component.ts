import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {CoacheeService} from '../services/coachee.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../../security/services/authentication/authentication.service';
import {Location} from "@angular/common";
import * as M from 'materialize-css';
import {flatMap, map, tap} from "rxjs/operators";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  editForm = this.fb.group({
    id: '',
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    classYear: ['', [Validators.required]],
    email: ['', [Validators.required]],
    photoUrl: [''],
    youcoachRole: this.fb.group({
      name: [''],
      label: ['']
    }),
    profilePicture: ['']
  });
  isEmailChanged = false;
  oldEmail = '';
  emailExistsError = false;
  profileId = +this.route.snapshot.paramMap.get('id');

  constructor(private fb: FormBuilder, private coacheeService: CoacheeService, private router: Router,
              public authenticationService: AuthenticationService, private route: ActivatedRoute, private location: Location) {
  }

  ngOnInit(): void {
    const url: string = this.route.snapshot['_routerState'].url;
    this.getCoacheeByID(this.profileId);
  }

  getCoacheeByID(id: number): void {
    this.coacheeService.getCoacheeById(id).subscribe(
      member => {
        this.editForm.patchValue(member);
        M.AutoInit();
      }
    );
  }

  updateProfile(member): void {
    this.coacheeService.updateProfile(member)
      .subscribe(memberUpdated => {
          if (memberUpdated.token !== null) {
            this.authenticationService.setJwtToken(memberUpdated.token, memberUpdated.email);
          }
          this.onBack();
        },
        err => {
          if (err.error.message === ('Email already exists!')) {
            this.emailExistsError = true;
          }
        });
    this.emailExistsError = false;
  }

  onBack(): void {
    this.location.back();
  }

  onSubmit(member) {
    if (this.editForm.get('email').value !== this.oldEmail) {
      this.isEmailChanged = true;
    }
    this.updateProfile(member);
  }

}
