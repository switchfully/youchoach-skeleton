import {Component, OnInit} from '@angular/core';
import {TranslateService} from '@ngx-translate/core';
import {RequestService} from "../services/request.service";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-become-coach',
  templateUrl: './become-coach.component.html',
  styleUrls: ['./become-coach.component.css']
})
export class BecomeCoachComponent implements OnInit {
  private profileId: number;
  sending: boolean;
  becomeACoachForm: FormGroup;
  error: boolean;

  constructor(
    private requestService: RequestService,
    public translateService: TranslateService,
    private route: ActivatedRoute,
    private location: Location,
    private formBuilder: FormBuilder
  ) {
  }

  ngOnInit() {
    this.route.parent.params.subscribe(routeParams => this.profileId = routeParams.id);
    this.becomeACoachForm = this.formBuilder.group({
      request: ['']
    });
  }

  mail(form: any) {
    this.sending = true;
    this.requestService.becomeACoach(this.profileId, form.request).subscribe(
      () => this.location.back(),
      ()=> {
        this.sending = false;
        this.error = true;
      }
    );
  }

}
