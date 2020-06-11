export interface ISessionComplete {
  id: number;
  coach: IPerson;
  coachee: IPerson;
  subject: string;
  date: string;
  time: string;
  location: string;
  remarks: string;
  status: string;
  cancelledByCoach: boolean;
  cancelledByCoachee: boolean;
}

export interface IPerson {
  name: string;
  id: number;
}
