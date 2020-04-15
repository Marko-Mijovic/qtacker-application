export interface IDevice {
  id?: number;
}

export class Device implements IDevice {
  constructor(public id?: number) {}
}
