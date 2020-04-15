import { IDepartment } from 'app/shared/model/department.model';

export interface ICompany {
  id?: number;
  companyName?: string;
  departments?: IDepartment[];
}

export class Company implements ICompany {
  constructor(public id?: number, public companyName?: string, public departments?: IDepartment[]) {}
}
