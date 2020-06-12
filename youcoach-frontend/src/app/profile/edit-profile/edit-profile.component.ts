import {Component, OnInit} from '@angular/core';
import {IMember} from '../interfaces/IMember';
import {FormBuilder, Validators} from '@angular/forms';
import {CoacheeService} from '../services/coachee.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthenticationService} from '../../security/services/authentication/authentication.service';

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit {
  member: IMember;
  editForm = this.fb.group({
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    email: ['', [Validators.required]],
    photoUrl: [''],
  });
  isEmailChanged = false;
  oldEmail = '';
  emailExistsError = false;
  constructor(private fb: FormBuilder, private coacheeService: CoacheeService, private router: Router,
              private authenticationService: AuthenticationService, private route: ActivatedRoute) {
  }
  ngOnInit(): void {
    const url: string = this.route.snapshot['_routerState'].url;
    if (url.endsWith('/edit-profile')) {
      this.getCoachee();
    } else {
      this.getCoacheeByID(+this.route.snapshot.paramMap.get('id'));
    }
  }

  getCoachee(): void {
    this.coacheeService.getCoachee().subscribe(
      member => this.member = member);
  }

  getCoacheeByID(id: number): void {
    this.coacheeService.getCoacheeById(id).subscribe(
      member => this.member = member);
  }

  updateProfile(): void {
    this.coacheeService.updateProfile(this.member)
      .subscribe(memberUpdated => {
        this.member = memberUpdated;
        if (memberUpdated.token !== null) {
            this.authenticationService.setJwtToken(memberUpdated.token, this.member.email);
          }
        },
      err => {
        if (err.error.message === ('Email already exists!')) {
          this.emailExistsError = true;
        }
      });
    setTimeout(() => {
      if (!this.emailExistsError) {
        this.onBack();
    } }, 1500);
    // TEST IT IN HEROKU if timeout is enough
    this.emailExistsError = false;
    // this.isEmailChanged = false;
  }

  onBack(): void {
    this.router.navigateByUrl('/profile');
  }

  onLogin(): void {
    this.router.navigateByUrl('/login');
  }


  onSubmit() {
    if (this.editForm.get('email').value !== this.oldEmail) {
      this.isEmailChanged = true;
    }
    this.member = {
      id: this.member.id,
      firstName: this.editForm.get('firstName').value,
      lastName: this.editForm.get('lastName').value,
      email: this.editForm.get('email').value,
      photoUrl: this.editForm.get('photoUrl').value,
      classYear: this.member.classYear,
      youcoachRole: 'coachee',
    };
    this.updateProfile();
  }

}