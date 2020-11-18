import {Component, OnInit} from '@angular/core';
import {RequestService} from "../services/request.service";
import {Location} from "@angular/common";
import {ActivatedRoute} from "@angular/router";
import {ITopic} from "../interfaces/ICoach";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-change-topics',
  templateUrl: './change-topics.component.html',
  styleUrls: ['./change-topics.component.css']
})
export class ChangeTopicsComponent implements OnInit {
  private profileId: number;
  public topicsForm: FormGroup;
  public sending: boolean;


  constructor(private requestService: RequestService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private location: Location
  ) {
  }

  ngOnInit(): void {
    this.route.parent.params.subscribe(routeParams => this.profileId = routeParams.coachId);
    this.topicsForm = this.formBuilder.group({
      topics: ['']
    });
  }

  mail(form: any) {
    this.sending = true;
    this.requestService.changeTopics(this.profileId, form.topics).subscribe(() => this.location.back());
  }

}
