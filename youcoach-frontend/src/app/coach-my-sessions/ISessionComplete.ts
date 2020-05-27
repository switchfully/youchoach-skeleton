import {ICoach} from '../coach-profile/ICoach';
import {ICoachee} from '../register/icoachee';

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
}

export interface IPerson {
  name: string;
  id: number;
}
