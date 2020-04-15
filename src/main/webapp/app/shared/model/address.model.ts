export interface IAddress {
  id?: number;
  city?: string;
  street?: string;
  postalCode?: string;
  companyId?: number;
}

export class Address implements IAddress {
  constructor(public id?: number, public city?: string, public street?: string, public postalCode?: string, public companyId?: number) {}
}
