import {IMember} from './IMember';

export interface IMemberProfileUpdated extends IMember {
  token: string;
}
