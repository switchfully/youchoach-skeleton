import {ICoach} from '../coach-profile/ICoach';
import {ICoachee} from '../register/icoachee';

export interface ISessionComplete {
  id: number;
  coach: ICoach;
  coachee: ICoachee;
  subject: string;
  dateAndTime: string;
  location: string;
  remarks: string;
  status: string;
}
