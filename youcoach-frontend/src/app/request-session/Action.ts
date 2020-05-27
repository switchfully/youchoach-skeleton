export class Action {
  private sessionId: number;
  private status: Status;

  constructor(sessionId, status) {
    this.sessionId = sessionId;
    this.status = status;
  }
}

export enum Status {
  Accepted,
  Declined,
  Canceled
}
