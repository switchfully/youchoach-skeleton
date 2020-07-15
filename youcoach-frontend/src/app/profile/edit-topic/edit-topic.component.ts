import {Component, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {CoachService} from "../services/coach.service";
import {ICoach, ITopic} from "../interfaces/ICoach";
import {Location} from "@angular/common";
import {ProfileService} from "../../admin/services/profile.service";

@Component({
  selector: 'app-edit-topic',
  templateUrl: './edit-topic.component.html',
  styleUrls: ['./edit-topic.component.css']
})
export class EditTopicComponent implements OnInit {
  private idToGet: number;
  topics: ITopic[];
  newTopicForm = this.formBuilder.group({name: ['']}); //I would like to use a form here but the complexity with nested formArrays is just too big. So this is here to stop angular from complaining.
  newTopicName: string;

  constructor(private activatedRoute: ActivatedRoute,
              private coachService: CoachService,
              private location: Location,
              private formBuilder: FormBuilder,
  ) {}

  ngOnInit(): void {
    this.idToGet = +this.activatedRoute.snapshot.paramMap.get('id');
    this.coachService.getSpecificCoach(this.idToGet).subscribe(coach => this.topics = coach.topics);
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
    this.topics.push({name:this.newTopicForm.get('name').value, grades: []});
    this.newTopicForm.reset();
  }

  removeRow(index: number) {
    this.topics.splice(index, 1);
  }

}
