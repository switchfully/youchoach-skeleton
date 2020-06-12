export class ISessionComplete {
  id: number;
  coach: IPerson;
  coachee: IPerson;
  subject: string;
  date: string;
  time: string;
  location: string;
  remarks: string;
  status: Status;

  isDone(): boolean {
    return this.status === Status.CANCELLED || this.status === Status.DECLINED || this.status === Status.FEEDBACK_PROVIDED;
  }

  isWaitingForFeedback() {
    return this.status === Status.WAITING_FOR_FEEDBACK;
  }

  isRequested() {
    return this.status === Status.REQUESTED;
  }

  isAccepted() {
    return this.status === Status.ACCEPTED;
  }
}

export interface IPerson {
  name: string;
  id: number;
}

export enum Status {
  REQUESTED = 'REQUESTED',
  ACCEPTED = 'ACCEPTED',
  WAITING_FOR_FEEDBACK = 'WAITING_FOR_FEEDBACK',
  CANCELLED = 'CANCELLED',
  DECLINED = 'DECLINED',
  FEEDBACK_PROVIDED = 'FEEDBACK_PROVIDED',
}
