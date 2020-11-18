import {AfterViewInit, Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {ActivatedRoute} from "@angular/router";
import {CoachService} from "../services/coach.service";
import {ITopic} from "../interfaces/ICoach";
import {Location} from "@angular/common";
import {ProfileService} from "../../admin/services/profile.service";
import {flatMap, map, tap} from "rxjs/operators";

@Component({
  selector: 'app-edit-topic',
  templateUrl: './edit-topic.component.html',
  styleUrls: ['./edit-topic.component.css']
})
export class EditTopicComponent implements OnInit {
  private idToGet: number;
  topics: ITopic[];
  newTopicForm = this.formBuilder.group({name: ['']});

  constructor(private activatedRoute: ActivatedRoute,
              private coachService: CoachService,
              private location: Location,
              private formBuilder: FormBuilder,
              private profileService: ProfileService
  ) {
  }

  ngOnInit(): void {
    this.activatedRoute.parent.params
      .pipe(
        map(params => params.coachId),
        tap(coachId => this.idToGet=coachId),
        flatMap(coachId => this.coachService.getSpecificCoach(coachId)),
        tap(_ => this.getAllTopics()),
      )
      .subscribe(coach => this.topics = coach.topics);
  }

  private getAllTopics() {
    this.profileService.getAllTopics().subscribe(
      topics => {
        const data = {};
        for (let topic of topics) {
          data['' + topic] = null;
        }
        setTimeout(() => {
          $('input.autocomplete').autocomplete({data});
        }, 1000);
      });
  }

  toggle(topic: ITopic, number: number) {
    if (topic.grades.includes(number)) {
      topic.grades = topic.grades.filter(grade => grade !== number);
    } else {
      topic.grades.push(number);
    }
  }

  onSave() {
    this.coachService.updateTopics(this.idToGet, this.topics).subscribe(
      _ => this.onBack()
    )
  }

  onBack() {
    this.location.back();
  }

  addRow() {
    this.topics.push({name: this.newTopicForm.get('name').value, grades: []});
    this.newTopicForm.reset();
    this.newTopicForm.setValue( {'name':null});
  }

  removeRow(index: number) {
    this.topics.splice(index, 1);
  }

  updateValue(name: string) {
   this.newTopicForm.patchValue({name});
  }
}
