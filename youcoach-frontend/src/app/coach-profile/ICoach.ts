import {IMember} from '../IMember';

export interface ICoach extends IMember {
  coachXP: number;
  introduction: string;
  availability: string;
  coachingTopics: string;
  topicYear: number;
}
