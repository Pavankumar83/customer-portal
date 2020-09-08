export interface IOrderProduct {
  id?: number;
  quantity?: number;
}

export class OrderProduct implements IOrderProduct {
  constructor(public id?: number, public quantity?: number) {}
}
