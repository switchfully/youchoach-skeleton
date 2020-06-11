import {Component, OnInit} from '@angular/core';
import {ICoachList} from '../../profile/interfaces/ICoachList';
import {CoachService} from '../../profile/services/coach.service';
import * as M from 'materialize-css';
import {ICoach} from '../../profile/interfaces/ICoach';

@Component({
  selector: 'app-find-a-coach',
  templateUrl: './find-a-coach.component.html',
  styleUrls: ['./find-a-coach.component.css']
})
export class FindACoachComponent implements OnInit {
  searchText: string;
  coachList: ICoachList = {coaches: null};
  topicList = [];
  // tslint:disable-next-line:variable-name
  private _filteredCoaches: ICoach [];
  selectedTopics: string[];
  grades: number[];


  constructor(private coachService: CoachService) {
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
      setTimeout(() => this.enableSelect(), 50);
    });
  }


  get filteredCoaches(): ICoach[] {
    this.performFilter();
    return this._filteredCoaches;
  }

  set filteredCoaches(value: ICoach[]) {
    this._filteredCoaches = value;
  }

  private enableSelect() {
    const elem: any = document.getElementsByTagName('select');
    for (const el of elem) {
      const instance = M.FormSelect.init(el, {});
    }
  }

  performFilter() {
    this.filteredCoaches = this.coachList.coaches;
    if (this.selectedTopics && this.selectedTopics.length > 0) {
      this.filteredCoaches = this._filteredCoaches
        .filter(coach => {
          const c = coach.topics.some(topic => this.selectedTopics.some(sTopic => {
            const a = sTopic === topic.name;
            return a;
          }
          ));
          return c;
        });
    }
    if (this.grades && this.grades.length > 0) {
      this.filteredCoaches = this._filteredCoaches
        .filter(coach => {
          const c = coach.topics.some(topic => topic.grades.some(grade => this.grades.some(grd => {
            const a = (grd.toString() === grade.toString());
            return a;
          })));
          return c;
        });
    }
    if (this.searchText !== undefined && this.searchText.length > 2) {
      this.filteredCoaches = this._filteredCoaches
        .filter(coach => coach.firstName.toLowerCase().startsWith(this.searchText.toLowerCase()) ||
          coach.lastName.toLowerCase().startsWith(this.searchText.toLowerCase())
        );
    }
  }
}
