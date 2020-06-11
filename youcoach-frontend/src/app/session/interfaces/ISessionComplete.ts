export interface ISessionComplete {
  id: number;
  coach: IPerson;
  coachee: IPerson;
  subject: string;
  date: string;
  time: string;
  location: string;
  remarks: string;
  status: Status;
  cancelledByCoach: boolean;
  cancelledByCoachee: boolean;
}

export interface IPerson {
  name: string;
  id: number;
}

export enum Status {
  REQUESTED = 'REQUESTED',
  ACCEPTED = 'ACCEPTED',
  FINISHED = 'FINISHED',
  CANCELLED = 'CANCELLED',
  DECLINED = 'DECLINED',
  FEEDBACK_PROVIDED = 'FEEDBACK_PROVIDED',
}
