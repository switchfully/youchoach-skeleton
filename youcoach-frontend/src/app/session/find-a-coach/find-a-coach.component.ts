import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ICoachList} from '../../profile/interfaces/ICoachList';
import {CoachService} from '../../profile/services/coach.service';
import * as M from 'materialize-css';
import {ICoach} from '../../profile/interfaces/ICoach';
import {InitService} from "../../template/materialize/init.service";

@Component({
  selector: 'app-find-a-coach',
  templateUrl: './find-a-coach.component.html',
  styleUrls: ['./find-a-coach.component.css']
})
export class FindACoachComponent implements OnInit {
  private coachList: ICoachList = {coaches: null};
  topicList = [];
  // tslint:disable-next-line:variable-name
  private _filteredCoaches: ICoach [];
  selectedTopics: string[] = [];
  selectedGrades: number[] = [];
  selectedText: string = '';


  constructor(private coachService: CoachService, private initService: InitService) {
  }

  ngOnInit(): void {
    this.getCoachesAndTopics();
  }

  getCoachesAndTopics() {
    this.coachService.getAllCoaches().subscribe(coaches => {
      this.coachList = coaches;
      this._filteredCoaches = coaches.coaches;
      for (const coach of this.coachList.coaches) {
        for (const topic of coach.topics) {
          if (this.topicList.indexOf(topic.name) === -1) {
            this.topicList.push(topic.name);
          }
        }
      }
      this.topicList.sort();
      this.initService.initFormSelect();
    });
  }


  get filteredCoaches(): ICoach[] {
    this.performFilter();
    return this._filteredCoaches;
  }

  set filteredCoaches(value: ICoach[]) {
    this._filteredCoaches = value;
  }

  performFilter() {
    if (!this.coachList.coaches) {
      this.filteredCoaches = [];
      return;
    }
    const filters = [];
    if (this.selectedTopics.length > 0 && this.selectedGrades.length > 0) {
      filters.push(coach => this.hasTopicThatIsInSelectedTopicsAndSelectedGrade(coach, this.selectedTopics, this.selectedGrades));
    }
    if (this.selectedTopics.length > 0) {
      filters.push(coach => this.hasOneOfSelectedTopics(coach, this.selectedTopics));
    }
    if (this.selectedGrades.length > 0) {
      filters.push(coach => this.hasOneOfSelectedGrades(coach, this.selectedGrades));
    }
    if (this.selectedText.length > 2) {
      filters.push(coach => this.hasFirstOrLastNameStartingWith(coach, this.selectedText));
    }

    this.filteredCoaches = this.coachList.coaches.filter(coach => filters.every(filter => filter(coach)))
  }

  private hasFirstOrLastNameStartingWith(coach, searchText) {
    return coach.firstName.toLowerCase().startsWith(searchText.toLowerCase()) || coach.lastName.toLowerCase().startsWith(searchText.toLowerCase());
  }

  private hasOneOfSelectedGrades(coach, selectedGrades) {
    return coach.topics.some(topic => this.isInSelectedGrades(topic, selectedGrades));
  }

  private hasOneOfSelectedTopics(coach, selectedTopics) {
    return coach.topics.some(topic => this.isASelectedTopic(topic, selectedTopics));
  }

  private hasTopicThatIsInSelectedTopicsAndSelectedGrade(coach: any, selectedTopics: string[], selectedGrades: number[]) {
    return coach.topics.some(topic => this.isASelectedTopic(topic, selectedTopics) && this.isInSelectedGrades(topic, selectedGrades));
  }

  private isASelectedTopic(topic, selectedTopics) {
    return selectedTopics.some(sTopic => sTopic === topic.name);
  }

  private isInSelectedGrades(topic, selectedGrades) {
    return topic.grades.some(grade => selectedGrades.some(sGrade => sGrade.toString() === grade.toString()));
  }
}
