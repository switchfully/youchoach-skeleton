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
        case 'requested':
          return session.isRequested();
        case 'accepted':
          return session.isAccepted();
        case 'waiting-for-feedback':
          return session.isWaitingForFeedback();
        case 'done':
          return session.isDone();
        default:
          return false;
      }
    });
  }
}
