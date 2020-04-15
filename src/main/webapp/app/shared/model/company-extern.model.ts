export interface ICompanyExtern {
  id?: number;
}

export class CompanyExtern implements ICompanyExtern {
  constructor(public id?: number) {}
}
