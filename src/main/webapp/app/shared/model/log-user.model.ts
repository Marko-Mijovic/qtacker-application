import { Moment } from 'moment';

export interface ILogUser {
  id?: number;
  dateTimeLog?: Moment;
}

export class LogUser implements ILogUser {
  constructor(public id?: number, public dateTimeLog?: Moment) {}
}
