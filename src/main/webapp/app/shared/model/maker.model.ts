import { IModel } from 'app/shared/model/model.model';

export interface IMaker {
  id?: number;
  makerName?: string;
  models?: IModel[];
}

export class Maker implements IMaker {
  constructor(public id?: number, public makerName?: string, public models?: IModel[]) {}
}
