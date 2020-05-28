export class Action {
  private sessionId: number;
  private status: Status;

  constructor(sessionId, status) {
    this.sessionId = sessionId;
    this.status = status;
  }
}

export enum Status {
  ACCEPTED,
  DECLINED,
  REQUESTED,
  CANCELLED_BY_COACH,
  CANCELLED_BY_COACHEE,
  FEEDBACK_PROVIDED,
  AUTOMATICALLY_CLOSED,
  FINISHED,
  DONE
}
