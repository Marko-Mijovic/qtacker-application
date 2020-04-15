export interface IModel {
  id?: number;
  modelName?: string;
  makerId?: number;
}

export class Model implements IModel {
  constructor(public id?: number, public modelName?: string, public makerId?: number) {}
}
