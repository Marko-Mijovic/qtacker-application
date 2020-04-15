export interface IDepartment {
  id?: number;
  displayName?: string;
  location?: string;
  headOfDepartment?: string;
  description?: string;
  companyId?: number;
}

export class Department implements IDepartment {
  constructor(
    public id?: number,
    public displayName?: string,
    public location?: string,
    public headOfDepartment?: string,
    public description?: string,
    public companyId?: number
  ) {}
}
