import {Pipe, PipeTransform} from "@angular/core";
import {ISessionComplete, Status} from "../interfaces/ISessionComplete";

@Pipe({name: 'SessionStatusLabel'})
export class SessionStatusLabelPipe implements PipeTransform {

  transform(status: Status): any {
    switch (status) {
      case Status.REQUESTED:
        return 'Session requested';
      case Status.DECLINED:
        return 'cancelled (by coach)';
      case Status.CANCELLED:
        return 'cancelled (by coachee)';
      case Status.FEEDBACK_PROVIDED:
        return 'finished with feedback';
      case Status.ACCEPTED:
        return 'accepted session';
      case Status.FINISHED:
        return 'waiting for feedback';
      default:
        return 'blabla'
    }
  }
}
