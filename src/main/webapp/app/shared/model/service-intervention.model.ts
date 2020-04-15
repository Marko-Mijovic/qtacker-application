import { Moment } from 'moment';

export interface IServiceIntervention {
  id?: number;
  dateTime?: Moment;
  price?: number;
  description?: string;
  deviceId?: number;
  companyExternId?: number;
}

export class ServiceIntervention implements IServiceIntervention {
  constructor(
    public id?: number,
    public dateTime?: Moment,
    public price?: number,
    public description?: string,
    public deviceId?: number,
    public companyExternId?: number
  ) {}
}
