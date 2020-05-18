import {Component, Input, OnInit} from '@angular/core';
import {ICoachList} from '../coach-profile/ICoachList';
import {CoachService} from "../coach-profile/coach.service";
import {InitMaterializeComponent} from "../init-materialize.component";

@Component({
  selector: 'app-find-a-coach',
  templateUrl: './find-a-coach.component.html',
  styleUrls: ['./find-a-coach.component.css']
})
export class FindACoachComponent extends InitMaterializeComponent implements OnInit {
  searchText;
  coachList: ICoachList = {coaches: null};
  @Input() topicList;

  constructor(private coachService: CoachService) {
    super();
    this.topicList = [];
  }

  ngOnInit(): void {
    this.getCoaches();
    setTimeout(() => this.getTopics(), 1000);
  }

  getCoaches(): void {
    this.coachService.getAllCoaches().subscribe(coaches => {
      this.coachList = coaches;
    });
  }

  getTopics(): void {
    for (let coach of this.coachList.coaches) {
      for (let topic of coach.topics) {
        if (this.topicList.indexOf(topic.name) === -1) {
          this.topicList.push(topic.name);
        }
      }
    }
    this.topicList.sort();
  }
}
