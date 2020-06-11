import {Pipe, PipeTransform} from "@angular/core";
import {ISessionComplete, Status} from "../interfaces/ISessionComplete";

@Pipe({
  name: 'FilterSessionsOnStatus',
  pure: false
})
export class FilterSessionsOnStatusPipe implements PipeTransform {
  transform(sessions: ISessionComplete[], type: String) {
    return sessions.filter(session => {
      switch (type) {
        case 'initial':
          return session.status === Status.REQUESTED || session.status === Status.ACCEPTED;
        case 'finished':
          return session.status === Status.FINISHED;
        case 'done':
          return session.status === Status.FEEDBACK_PROVIDED || session.status === Status.CANCELLED || session.status === Status.DECLINED;
        default:
          return false;
      }
    });
  }
}
