import {Component, OnInit} from '@angular/core';
import {ICoachList} from '../coach-profile/ICoachList';
import {CoachService} from '../coach-profile/coach.service';
import * as M from 'materialize-css';
import {ICoach, ITopic} from '../coach-profile/ICoach';

@Component({
  selector: 'app-find-a-coach',
  templateUrl: './find-a-coach.component.html',
  styleUrls: ['./find-a-coach.component.css']
})
export class FindACoachComponent implements OnInit {
  searchText;
  coachList: ICoachList = {coaches: null};
  topicList: ITopic[] = [];
  filteredCoachList: ICoach[] = [];
  filteredtopicList = [];

  constructor(private coachService: CoachService) {
  }

  ngOnInit(): void {
    this.coachService.getAllCoaches().subscribe(coaches => {
      this.coachList = coaches;
      this.filteredCoachList = coaches.coaches;
      for (const coach of this.coachList.coaches) {
        for (const topic of coach.topics) {
          if (this.topicList.indexOf(topic) === -1) {
            this.topicList.push(topic);
          }
        }
      }
      this.topicList.sort();
      setTimeout(() => this.enableSelect(), 50);
    });
  }
  private enableSelect() {
    const elem: any = document.getElementsByTagName('select');
    for (const el of elem) {
      const instance = M.FormSelect.init(el, {});
    }
  }

  updateList(event: any) {
    this.filteredCoachList = [];
    for (const coach of this.coachList.coaches) {
      for (const topic1 of coach.topics) {
        if (topic1.name === event.target.value) {
          this.filteredCoachList.push(coach);
        }
      }
    }
  }
}
